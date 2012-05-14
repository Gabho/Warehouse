/*
 * Implementation of a virtual proxy pattern.
 */
package topology.resource.management;

import java.util.List;

/**
 * Proxy shelf stores a instance of shelf in resource cache.
 * When shelf's method is invoked it finds the instance in cache.
 * If its not there it reates a new one a stores puts it to cache before
 * the method returns.
 * @author Martin Lofaj
 */
public class ProxyShelf implements IShelf {
    
    private IShelf shelf;
    private ResourceCache<IShelf> cache;
    
    //Id of the actual shelf.
    private int id;

    public ProxyShelf(int id,ResourceCache<IShelf> cache) {
        this.id = id;
        this.cache = cache;
//        try {
//            cache = (ResourceCache<IShelf>) new InitialContext().lookup("/home/mao/NetBeansProjects/Warehouse/Warehouse-ejb/src/java/topology/resource/management/ResourceCache");
//        } catch (NamingException ex) {
//            Logger.getLogger(ProxyShelf.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
    public void insertItem(IItem item) {
        getShelf();
        shelf.insertItem(item);
        cacheShelf();
    }

    @Override
    public IItem removeItem(IItem item) {
        getShelf();
        IItem removedItem = shelf.removeItem(item);
        cacheShelf();
        return removedItem;
    }
    
    @Override
    public List<IItem> remove() {
        getShelf();
        List<IItem> items =  shelf.remove();
        shelf = null;
        return items;
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
    
    /**
     * Puts a working instance of shelf into resource cache.
     */
    private void cacheShelf() {
        cache.insert(id, shelf);
        shelf = null;
    }

    @Override
    public void prepare(IItem item) throws TaskFailureException {
        getShelf();
        shelf.prepare(item);
        cacheShelf();
    }

    @Override
    public void commit() {
        getShelf();
        shelf.commit();
        cacheShelf();
    }

    @Override
    public void abort() {
        getShelf();
        shelf.abort();
        cacheShelf();
    }
    
}
