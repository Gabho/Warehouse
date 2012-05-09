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
    
    //vracia zoznam poloziek nachadzajucich sa na danej policke
    public List<Item> getShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //prida policku na danu poziciu
    public void addShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //aktualizuje obsah policky na danej pozicii
    public void updateShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //odstrani celu policku z danej pozicie
    public void removeShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //vrati pocet najdenych itemov (hladanie podla master entity)
    public int search(String string) {
        int quantity;
        lock.lock();
        quantity = synchronizedSearch(string);
        lock.unlock();
        return quantity;
    }
    
    abstract int synchronizedSearch(String string);
    
}
