/*
 * Implementation of a virtual proxy pattern.
 */
package topology.resource.management;

import java.util.List;

/**
 * 
 * @author Martin Lofaj
 */
public class ProxyShelf implements IShelf{
    
    private IShelf shelf;
    
    //Id of the actual shelf.
    private int id;

    public ProxyShelf(int id) {
        this.id = id;
    }

    @Override
    public List<IItem> getItems() {
        if(shelf == null) {
            shelf = new Shelf(id);
        }
        
        return shelf.getItems();
    }

    @Override
    public int getCapacity() {
        if(shelf == null) {
            shelf = new Shelf(id);
        }
        return shelf.getCapacity();
    }

    @Override
    public int getItemCount(String type) {
        if(shelf == null) {
            shelf = new Shelf(id);
        }
        return shelf.getItemCount(type);
    }

    @Override
    public int getFreeSpace() {
        if(shelf == null) {
            shelf = new Shelf(id);
        }
        return shelf.getFreeSpace();
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void insertItem(Item item) {
        if(shelf == null) {
            shelf = new Shelf(id);
        }
        shelf.insertItem(item);
    }

    @Override
    public void removeItem(Item item) {
        if(shelf == null) {
            shelf = new Shelf(id);
        }
        shelf.insertItem(item);
    }
    
}
