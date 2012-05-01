/*
 * Implementation of the Resource Cache pattern.
 */
package topology;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Lofaj
 */
public class ResourceCache {
    
    private List<IShelf> cache;
    private int capacity;

    public ResourceCache(int capacity) {
        this.capacity = capacity;
        cache = new ArrayList<IShelf>(capacity);
    }

    //nie som si isty ci tam namiesto shlefu nedat priamo item
    public void insert(IShelf shelf) {
        if(capacity < cache.size()) {
            cache.add(shelf);
        } else {
            //TODO: eviction of unsed resources
        }
    }
    
    //nie som si isty ci tam namiesto shlefu nedat priamo item
    public void remove(IShelf shelf) {
        cache.remove(shelf);
    }
}
