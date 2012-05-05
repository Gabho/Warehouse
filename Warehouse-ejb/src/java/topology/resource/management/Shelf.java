/*
 * Impl. of Shelf, the basic storage unit.
 * This is the part of the task coordinator pattern. 
 * Taking the place of task coorinator.
 */

package topology.resource.management;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin Lofaj
 */
public class Shelf implements IShelf {

    private List<IItem> items;
    private int id;
    private int capacity;
    //TODO: add database connection or something
    
    private static final int DEFAULT_CAPACITY = 10;

    public Shelf(int id) {
        this.id = id;
        this.capacity = DEFAULT_CAPACITY;
        items = new ArrayList<IItem>(capacity);
        
        //TODO: check for existing items.
        //getShelf from database
    }
    
    public Shelf(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        items = new ArrayList<IItem>(capacity);
        
        //TODO: check for existing items.
        //getShelf from database
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
    public void insertItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO: inserting item
    }

       
    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO: removing item
    }
    
}
