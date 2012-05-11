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
import topology.resource.management.ProxyShelf;


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
    public int addItem() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        for (int i = 1; i < 16; i++) {
            Rack rack = (Rack) manager.get("R"+i);
            try {
                List<ProxyShelf>  lst = rack.getShelfs();
                for (int j = 0; j < lst.size(); j++) {
                    ProxyShelf proxyS = lst.get(j);
                    freeSpace += proxyS.getFreeSpace();
                }
            } catch (Exception ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return freeSpace;
    }

    
}
