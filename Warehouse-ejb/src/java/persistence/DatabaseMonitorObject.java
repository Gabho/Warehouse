/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.List;
import topology.resource.management.Item;
import topology.resource.management.Position;

/**
 *
 * @author Gabo
 */
public abstract class DatabaseMonitorObject {
    
    private MonitorLock lock = new MonitorLock();
    private MonitorCondition condition = new MonitorCondition(lock);
    

    public List<Item> getShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int search(String string) {
        int quantity;
        lock.lock();
        quantity = synchronizedSearch(string);
        lock.unlock();
        return quantity;
    }
    
    abstract int synchronizedSearch(String string);
    
}
