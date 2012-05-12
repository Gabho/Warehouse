/*
 * Impl. of Shelf, the basic storage unit.
 * This is the part of the task coordinator pattern. 
 * Taking the place of task coorinator.
 */

package topology.resource.management;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import persistence.Database;

/**
 * Implemetation of Shelf.
 * @author Martin Lofaj
 */
public class Shelf implements IShelf {

    private List<IItem> items;
    private int id;
    private int capacity;
    private int usedSpace;
    private @EJB Database db;
        
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Costructs a Shelf of given id. If content of the shelf already
     * exist in database it will load it and set up all related member
     * variables. Uses default capacity.
     * @param id shelf's id
     */
    public Shelf(int id) {
        this.id = id;
        this.capacity = DEFAULT_CAPACITY;
        //try {
        //    db = (Database) new InitialContext().lookup("java:global/Warehouse/Warehouse-ejb/Database");
            List<IItem> loadedItems = db.getShelf(id);
            if(loadedItems.size() > 0) {
                items = new ArrayList<IItem>(loadedItems);
            } else {
                items = new ArrayList<IItem>(capacity);
            }
        //} catch (NamingException ex) {
        //    Logger.getLogger(Shelf.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        updateUsedSpace();
    }
    /**
     * Costructs a Shelf of given id. If content of the shelf already
     * exist in database it will load it and set up all related member
     * variables. Uses specified capacity.
     * @param id shelf's id
     * @param capacity shelf's capacity
     */
    public Shelf(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        //try {
        //    db = (Database) new InitialContext().lookup("java:global/Warehouse/Warehouse-ejb/Database");
            List<IItem> loadedItems = db.getShelf(id);
            if(loadedItems.size() > 0) {
                items = new ArrayList<IItem>(loadedItems);
            } else {
                items = new ArrayList<IItem>(capacity);
            }
        //} catch (NamingException ex) {
        //    Logger.getLogger(Shelf.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        updateUsedSpace();
    }
            
    @Override
    public List<IItem> getItems() {
        return items;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getItemCount(String type) {
        int counter = 0;
        for(IItem it : items) {
            if(it.getType().equals(type)) {
                counter += it.getAmount();
            }
        }
        return counter;
    }

    @Override
    public int getFreeSpace() {
        return capacity - usedSpace;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void insertItem(IItem item) {
        if(item.getAmount() <= getFreeSpace()) {
            items.add(item);
            db.updateShelf(items, id);
        }
        updateUsedSpace();
    }

       
    @Override
    public IItem removeItem(IItem item) {
        if(!items.isEmpty()) {
            if(!items.remove(item)) {
                item = null;
            }
            db.updateShelf(items, id);
        }
        updateUsedSpace();
        
        return item;
    }
    
    @Override
    public List<IItem> remove() {
        List<IItem> retItems = items;
        db.removeShelf(id);
        items = null;
        id = -1;
        capacity = -1;
        
        return retItems;
    }
    
    /**
     * Computes used space by adding amounts of all items stored
     * in Shelf and writes the result to usedSpace member variable.
     */
    private void updateUsedSpace() {
        for(IItem item : items) {
            usedSpace += item.getAmount();
        }
    }
}
