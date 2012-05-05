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
public class Storage implements IStorage {
    
    ObjectManager om;
 
    public Storage() {
        om = new ObjectManager();

    }

    public ObjectManager getObjectManager() {
        return om;
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

    private class ObjectManager implements IObjectManager {
        private HashMap manager;
        public ObjectManager() {
            manager = new HashMap();
        }

        @Override
        public void insert() {
            manager.put(this, this);//to this je len zatial
        }

        @Override
        public void remove() {
            manager.remove(this);
        }

        @Override
        public void find() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
}
