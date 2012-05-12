/*
 * Implementation of the Resource Cache pattern.
 */
package topology.resource.management;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateful;


/**
 * A generic resource cache. A unique id is needed to store a resource in it.
 * @author Martin Lofaj
 */
@Stateful
public class ResourceCache<T> {

    private Map<Integer ,CachedResource<T>> cache;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Costructs resource cache. Sets its capacity to default and creates
     * its hashmap representation.
     */
    public ResourceCache() {
        this.capacity = DEFAULT_CAPACITY;
        cache = new HashMap<Integer ,CachedResource<T>>(capacity);
    }

    /**
     * Inserts a given resource into cache. If the cache is full it
     * will remove the least used item first.
     * @param key unique key for hash calculation.
     * @param resource item to be stored.
     */
    public void insert(int key, T resource) {
        if (capacity < cache.size()) {
            cache.put(key ,new CachedResource<T>(resource, new Date(), key));
        } else {
            cache.remove(findLeastUsed());
            cache.put(key, new CachedResource<T>(resource, new Date(), key));
        }
    }
    
    /**
     * Takes specified key a tests if the key is present in the cache.
     * @param key key to look for.
     * @return true if item with specified key is cached false if it isn't.
     */
    public boolean isCached(int key) {
        return cache.containsKey(key);
    }

    //pri pouziti odstranovania najmenej pouzivaneho prvku je 
    //zlozitost operacie vkladania dost velka.
    private int findLeastUsed() {
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

    /**
     * Removes item with specified key from the cache.
     * @param key of the item to be removed.
     * @return item bound with key
     */
    public T remove(int key) {
        return cache.remove(key).getResource();
    }
    
    /**
     * Constainer for items stored in cache. Adds some additional 
     * information to the item (time of insertion, its key)
     * @param <T> type of the stored item
     */
    private class CachedResource<T> {

        private T resource;
        private Date timestamp;
        private int key;

        /**
         * Creates resource object.
         * @param resource the actual item.
         * @param timeStamp time object was inserted into cache.
         * @param key key of the inserted object.
         */
        public CachedResource(T resource, Date timeStamp, int key) {
            this.resource = resource;
            this.timestamp = timeStamp;
            this.key = key;
        }
        
        /**
         * Returns key key of the stored item.
         * @return object's key.
         */
        public int getKey() {
            return key;
        }

        /**
         * Returns time of object's insertion.
         * @return object's time stamp
         */
        public Date getTimeStamp() {
            return this.timestamp;
        }

        /**
         * Manipulation with insertion time.
         * @param stamp new insertion time.
         */
        public void setTimeStamp(Date stamp) {
            this.timestamp = stamp;
        }

        /**
         * Provides accsess to object stored inside this container.
         * @return cached resource.
         */
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
