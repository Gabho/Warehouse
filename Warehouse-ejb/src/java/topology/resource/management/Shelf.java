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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import persistence.Database;

/**
 *
 * @author Martin Lofaj
 */
public class Shelf implements IShelf {

    private List<IItem> items;
    private int id;
    private int capacity;
    private Database db;
        
    private static final int DEFAULT_CAPACITY = 10;

    public Shelf(int id) {
        this.id = id;
        this.capacity = DEFAULT_CAPACITY;
        items = new ArrayList<IItem>(capacity);
        try {
            db = (Database) new InitialContext().lookup("java:global/Warehouse/Warehouse-ejb/Database");
            //TODO item loading
        } catch (NamingException ex) {
            Logger.getLogger(Shelf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Shelf(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        items = new ArrayList<IItem>(capacity);
        try {
            db = (Database) new InitialContext().lookup("java:global/Warehouse/Warehouse-ejb/Database");
            //TODO item loading
        } catch (NamingException ex) {
            Logger.getLogger(Shelf.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        int totalAmount = 0;
        for(IItem item : items) {
            totalAmount += item.getAmount();
        }
        return capacity - totalAmount;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void insertItem(IItem item) {
        throw new UnsupportedOperationException("Not supported yet.");
        //db.addShelf(id);
    }

       
    @Override
    public Item removeItem(IItem item) {
        throw new UnsupportedOperationException("Not supported yet.");
        //return db.updateShelf(null);
    }
    
    @Override
    public List<IItem> remove() {
        throw new UnsupportedOperationException("Not supported yet.");
        //db.removeShelf(id);
        //return this.items;
    }
}
