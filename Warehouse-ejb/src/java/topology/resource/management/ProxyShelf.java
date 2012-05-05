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
    private ResourceCache<IShelf> cache;
    
    //Id of the actual shelf.
    private int id;

    public ProxyShelf(int id, ResourceCache<IShelf> cache) {
        this.id = id;
        this.cache = cache;
    }

    @Override
    public List<IItem> getItems() {
        List<IItem> items;
        
        getShelf();
        items = shelf.getItems();
        cacheShelf();
        
        return items;
    }

    @Override
    public int getCapacity() {
        int capacity;
        
        getShelf();
        capacity =shelf.getCapacity();
        cacheShelf();
        
        return capacity;
    }

    @Override
    public int getItemCount(String type) {
        int count;
        
        getShelf();
        count = shelf.getItemCount(type);
        cacheShelf();
        
        return count;
    }

    @Override
    public int getFreeSpace() {
        int freeSpace;
        
        getShelf();
        freeSpace = shelf.getFreeSpace();
        cacheShelf();
        
        return freeSpace;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void insertItem(Item item) {
        getShelf();
        shelf.insertItem(item);
        cacheShelf();
    }

    @Override
    public void removeItem(Item item) {
        getShelf();
        shelf.insertItem(item);
        cacheShelf();
    }
    
    private void getShelf() {
        if(shelf == null) {
            if(cache.isCached(id)) {
                shelf = cache.remove(id);
            } else {
                shelf = new Shelf(id);
            }
        }
    }
    
    private void cacheShelf() {
        cache.insert(id, shelf);
        shelf = null;
    }
    
}
