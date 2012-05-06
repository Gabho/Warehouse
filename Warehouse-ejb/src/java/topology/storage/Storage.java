/*
 * Object Manager pattern - Warehouse topology
 * 
 */
package topology.storage;

import java.util.HashMap;
import javax.ejb.Stateless;

/**
 *
 * @author Martin Pakandl
 */
@Stateless
public class Storage implements IObjectManager, IStorage {

    private HashMap manager;

    public Storage() {
        manager = new HashMap();
    }
   
    @Override
    public void insert(Object object) {
        manager.put(object.hashCode(), object);
    }

    @Override
    public void remove(Object object) {
        manager.remove(object.hashCode());
    }

    @Override
    public Object find(Object object) {
        return manager.get(object.hashCode());
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

    @Override
    public int getFreeSpace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
