/*
 * Object Manager pattern - Warehouse topology
 * 
 */
package topology.storage;

import java.util.ArrayList;
import javax.ejb.Stateless;

/**
 * 
 * @author Martin Pakandl
 */
@Stateless
public class Storage implements IObjectManager,IStorage {
    
    private ArrayList storage;
    
    public Storage() {
        storage = new ArrayList();
    }
        
    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void find() {
        throw new UnsupportedOperationException("Not supported yet.");
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
