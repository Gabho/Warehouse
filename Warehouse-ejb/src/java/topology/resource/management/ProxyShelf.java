/*
 * Implementation of a virtual proxy pattern.
 */
package topology.resource.management;

import java.util.List;

/**
 * ProxyShelf vytvára inštanciu poličky až prípade, že je vyžadovaná jej
 * funkcionalita. Pri volaní metódy sa vyhľadá inštancia poličky v resource
 * cache. Ak sa tam nenachádza vytvorí sa nová. A pred návratom z metódy sa
 * inštancia uloží do resource cache.
 * @author Martin Lofaj
 */
public class ProxyShelf implements IShelf {
    
    private IShelf shelf;
    private ResourceCache<IShelf> cache;
    
    //Id of the actual shelf.
    private int id;

    /**
     * Vytvorenie nového proxy. Pridelenie jedinečného identifikátora
     * a referencie na resource cache.
     * @param id
     * @param cache 
     */
    public ProxyShelf(int id,ResourceCache<IShelf> cache) {
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
    
    /**
     * Získanie inštancie poličky. Najprv sa vyhľadáva v resource cache. V príade,
     * že sa tam inštancia nenachádza vytvára sa nová (volá sa konštruktor).
     */
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
     * Uloží inštanciu poličky do resource cache.
     * Zmaže referenciu na objekt poličky.
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
