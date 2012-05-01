/*
 * Implementation of the Resource Cache pattern.
 */
package topology.resource.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Martin Lofaj
 */
public class ResourceCache<T> {

    private List<CachedResource<T>> cache;
    private int capacity;

    public ResourceCache(int capacity) {
        this.capacity = capacity;
        cache = new ArrayList<CachedResource<T>>(capacity);
    }

    //nie som si isty ci tam namiesto shlefu nedat priamo item
    public void insert(T resource) {
        if (capacity < cache.size()) {
            cache.add(new CachedResource<T>(resource, new Date()));
        } else {
            cache.remove(findOldest());
            cache.add(new CachedResource<T>(resource, new Date()));
        }
    }

    private CachedResource<T> findOldest() {
        Date oldestTime = new Date();
        CachedResource<T> oldest = null;
        for (CachedResource res : cache) {
            if (res.getTimeStamp().compareTo(oldestTime) <= 0) {
                oldest = res;
                oldestTime = oldest.getTimeStamp();
            }
        }
        return oldest;
    }

    //nie som si isty ci tam namiesto shlefu nedat priamo item
    public void remove(T resource) {
        cache.remove(new CachedResource<T>(resource));
    }
    
    //teoreticky by sa este dalo tak ze urobim notofy metodu
    // a ta spravi to ze updatne cas ked sa zavola funkcionalita objektu
    // tato metoda by bola volana v proxy itemu ..alebo cohokolvek
    public void updateTimestamp(T resource) {
        CachedResource<T> res = new CachedResource<T>(resource);
        for(CachedResource cr : cache) {
            if(cr.equals(res)) {
                cr.setTimeStamp(new Date());
                return;
            }
        }
    }

    /**
     * Item stored in resource cache
     *
     * @param <T>
     */
    private class CachedResource<T> {

        private T resource;
        private Date timestamp;

        public CachedResource(T resource, Date timeStamp) {
            this.resource = resource;
        }

        private CachedResource(T resource) {
            this.resource = resource;
            this.timestamp = null;
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
            if (this.resource != other.resource && (this.resource == null || !this.resource.equals(other.resource))) {
                return false;
            }
            return true;
        }
    }
}
