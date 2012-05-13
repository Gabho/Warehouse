/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import topology.configuration.AbstractComponent;
import topology.resource.management.IShelf;
import topology.resource.management.Item;
import topology.resource.management.Position;


/**
 *
 * @author Mao
 */
@Stateful
public class Storage implements IObjectManager {

   private HashMap manager;

    public Storage() {
        manager = new HashMap();  
    }
   
    @Override
    public void insert(Object object) {
        String hashKey;
        AbstractComponent object1 = (AbstractComponent) object;
        hashKey = object1.getCode();

       manager.put(hashKey, object);
    }

    @Override
    public String remove(String hashKey) {
        if(manager.remove(hashKey) == null) 
            return "Error: No match to object!";
        else return "Remove: OK";
    } 

    @Override
    public Object find(String hashKey) {
        return manager.get(hashKey);
    }
    
    @Override
    public int addItem(Item item) {
        for (int i = 1; i < 4; i++) {
            try {
                Aisle aisle = (Aisle) manager.get("A"+i);
                List<Rack> racks = aisle.getRacks();
                for (int j = 0; j < racks.size(); j++) {
                    Rack rack = racks.get(j);
                    List<IShelf>  lst = rack.getShelfs();
                    for (int k = 0; k < lst.size(); k++) {
                        IShelf proxyS = lst.get(k);
                        if(proxyS.getFreeSpace() == 0) {
                            int rackID = Integer.valueOf(rack.getCode().charAt(1));
                            int aisleID = Integer.valueOf(aisle.getCode().charAt(1));
                            item.setPosition(new Position(proxyS.getID(),rackID,aisleID));
                            proxyS.insertItem(item);
                            return 1;
                        }
                    }
                }
                
            } catch (Exception ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return 0;
    }

    @Override
    public int removeItem() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        int countOfRacks = 15;
        int freeSpace = 0;
        for (int i = 1; i < 4; i++) {
            try {
                Aisle aisle = (Aisle) manager.get("A"+i);
                List<Rack> racks = aisle.getRacks();
                for (int j = 0; j < racks.size(); j++) {
                    Rack rack = racks.get(j);
                    List<IShelf>  lst = rack.getShelfs();
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
