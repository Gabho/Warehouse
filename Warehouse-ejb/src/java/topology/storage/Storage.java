/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import persistence.ItemEntity;
import persistence.MasterDataEntity;
import topology.configuration.AbstractComponent;
import topology.resource.management.*;

/**
 *
 * @author Mao
 */
@Stateful
public class Storage implements IObjectManager {

    private static HashMap manager = new HashMap();

    @Override
    public void insert(Object object) {
        String hashKey;
        AbstractComponent object1 = (AbstractComponent) object;
        hashKey = object1.getCode();

        manager.put(hashKey, object);
    }

    @Override
    public String remove(String hashKey) {
        if (manager.remove(hashKey) == null) {
            return "Error: No match to object!";
        } else {
            return "Remove: OK";
        }
    }

    @Override
    public Object find(String hashKey) {
        return manager.get(hashKey);
    }

    @Override
    public boolean addItem(Item item){ 
        //TODO: koordinovane ulozenie rozlozeneho itemu.
        int shelfCap = Shelf.DEFAULT_CAPACITY;
        int itemAmount = item.getAmount();
        List<IShelf> shelfs = new ArrayList();
        //rozlozenie itemu.
        int numofShelfs = itemAmount / shelfCap + 1;
        try {
            IItem it;
            for (int i = 0; i < numofShelfs; i++) {
                if (itemAmount > shelfCap) {
                    it = new Item(item.getId() + i, shelfCap, item.getType(),
                            item.getDescription(), item.getExpiration(), null);
                    shelfs.add(setPosition(it)); //ulozenie daneho itemu do shelfu (PREPARE !!)
                    itemAmount -= shelfCap;               
                } else if (itemAmount > 0) {
                    it = new Item(item.getId() + i, itemAmount, item.getType(),
                            item.getDescription(), item.getExpiration(), null);
                    shelfs.add(setPosition(it)); //ulozenie itemu do shelfu (PREPARE !!)              
                }
            }
        } catch (TaskFailureException e) {
            //abort v pripade neuspechu
            for (IShelf shelf : shelfs) {
                shelf.abort();
            }
            return false;
        }
        //comit coordinovanej operacie
        for (IShelf shelf : shelfs) {
            shelf.commit();
        }
        shelfs.clear();
        return true;
    }

    private IShelf setPosition(IItem item) throws TaskFailureException {
        for (int i = 1; i < 4; i++) {
            try {
                Aisle aisle = (Aisle) manager.get("A" + i);
                List<Rack> racks = aisle.getRacks();
                for (int j = 0; j < racks.size(); j++) {
                    Rack rack = racks.get(j);
                    List<IShelf> lst = rack.getShelfs();
                    for (int k = 0; k < lst.size(); k++) {
                        IShelf proxyS = lst.get(k);
                        if (proxyS.getFreeSpace() >= item.getAmount()) {
                            //int rackID = Integer.valueOf(rack.getCode().charAt(1));\
                            int rackID = 1;
                            //TODO: opravit
                            System.out.println("RACK : - " + rack.getCode() + " = " + rackID );
                            int aisleID = 1;//Integer.valueOf(aisle.getCode().charAt(1));
                            item.setPosition(new Position(proxyS.getID(), rackID, aisleID));
                            proxyS.prepare(item); //Prepare !!
                            System.out.println("Proxy ID - setPos :" + proxyS.getID());
                            return proxyS;
                        }
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        throw new TaskFailureException();
    }

    @Override
    public int removeItem(int quantity, MasterDataEntity masterData) {
        try {
            ItemEntity itemEntity = masterData.getItemEntitys().get(quantity);
            int aisleID = itemEntity.getAisle();
            Aisle aisle = (Aisle) manager.get("A" + aisleID);
            List<Rack> racks = aisle.getRacks();
            Rack rack = null;
            for (int i = 0; i < racks.size(); i++) {
                rack = racks.get(i);
                if (rack.getCode().equals("R" + itemEntity.getRack())) {
                    break;
                }
            }
            List<IShelf> shelfs = rack.getShelfs();
            IShelf shelf = null;
            for (int i = 0; i < shelfs.size(); i++) {
                shelf = shelfs.get(i);
                if (shelf.getID() == itemEntity.getShelf()) {
                    break;
                }
            }
            shelf.removeItem(null);
        } catch (Exception ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int findItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*
     * Return free space.
     */

    @Override
    public int getFreeSpace() {
        int freeSpace = 0;
        for (int i = 1; i < 4; i++) {
            try {
                Aisle aisle = (Aisle) manager.get("A" + i);
                List<Rack> racks = aisle.getRacks();
                for (int j = 0; j < racks.size(); j++) {
                    Rack rack = racks.get(j);
                    List<IShelf> lst = rack.getShelfs();
                    for (int k = 0; k < lst.size(); k++) {
                        IShelf proxyS = lst.get(k);
                        freeSpace += proxyS.getFreeSpace();
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return freeSpace;
    }

    @Override
    public String printStorage() {
        String print = new String();
        for (int i = 1; i < 4; i++) {
            Aisle aisle = (Aisle) manager.get("A" + i);
            print += "<p>" + aisle.getCode() + " :<br>";
            List<Rack> racks = null;
            try {
                racks = aisle.getRacks();
            } catch (Exception ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int j = 0; j < racks.size(); j++) {
                Rack rack = racks.get(j);
                List<IShelf> lst = null;
                print += rack.getCode() + ": ";
                try {
                    lst = rack.getShelfs();
                } catch (Exception ex) {
                    Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (int k = 0; k < lst.size(); k++) {
                    IShelf proxyS = lst.get(k);
                    print += proxyS.getID() + " ";
                }
                print += "</br>";
            }
            print += "</p>";
        }
        return print;
    }
}
