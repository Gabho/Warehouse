/*
 * Implementation of the Resource Cache pattern.
 */
package topology.resource.management;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Martin Lofaj
 */
public class ResourceCache<T> {

    private Map<Integer ,CachedResource<T>> cache;
    private int capacity;

    public ResourceCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<Integer ,CachedResource<T>>(capacity);
    }

    //nie som si isty ci tam namiesto shlefu nedat priamo item
    public void insert(int key, T resource) {
        if (capacity < cache.size()) {
            cache.put(key ,new CachedResource<T>(resource, new Date(), key));
        } else {
            cache.remove(findOldest());
            cache.put(key, new CachedResource<T>(resource, new Date(), key));
        }
    }
    
    public T getResource(int key) {
        return cache.get(key).getResource();
    }
    
    public boolean isCached(int key) {
        return cache.containsKey(key);
    }

    //pri pouziti odstranovania najmenej pouzivaneho prvku je 
    //zlozitost operacie vkladania dost velka.
    private int findOldest() {
        Date oldestTime = new Date();
        int keyOldest = 0;
        for (CachedResource res : cache.values()) {
            if (res.getTimeStamp().compareTo(oldestTime) <= 0) {
                keyOldest = res.getKey();
                oldestTime = res.getTimeStamp();
            }
        }
        return keyOldest;
    }

    public void remove(int key) {
        cache.remove(key);
    }
    
    public void updateTimestamp(int key) {
        CachedResource<T> resource = cache.get(key);
        if(resource != null) {
            resource.setTimeStamp(new Date());
        }
    }

    /**
     * Item stored in resource cache
     * @param <T>
     */
    private class CachedResource<T> {

        private T resource;
        private Date timestamp;
        private int key;

        public CachedResource(T resource, Date timeStamp, int key) {
            this.resource = resource;
            this.timestamp = timeStamp;
            this.key = key;
        }

        private CachedResource(T resource) {
            this.resource = resource;
            this.timestamp = null;
        }
        
        public int getKey() {
            return key;
        }

        public Date getTimeStamp() {
            return this.timestamp;
        }

        public void setTimeStamp(Date stamp) {
            this.timestamp = stamp;
        }

        public T getResource() {
            return resource;
        }

        @Override
        public int hashCode() {
            return resource.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final CachedResource<T> other = (CachedResource<T>) obj;
            if (this.resource != other.resource && (this.resource == null ||
                    !this.resource.equals(other.resource))) {
                return false;
            }
            return true;
        }
    }
}
