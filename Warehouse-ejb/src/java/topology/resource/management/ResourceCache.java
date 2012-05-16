/*
 * Implementation of the Resource Cache pattern.
 */
package topology.resource.management;

import javax.ejb.Stateful;
import java.util.*;

/**
 * Generický resource cache. Slúži ako úložisko pre často používané objekty aby
 * sa predišlo ich opätovnému vytváraniu. K ukladaní prvkov do cache je potrebný
 * jedinečný kľúč. Prvky sú ukladané v údajovej štruktúre HashMap. Cache je
 * automaticky vyprázdňovaný po uplinutí definovaného časového intervalu.
 *
 * @author Martin Lofaj
 */
@Stateful
public class ResourceCache<T> {

    private Map<Integer, CachedResource<T>> cache;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;
    private Timer timer;
    private boolean lock;

    /**
     * Vytvorenie resource cache. Kapacita a interval čistenia cache sú
     * nastavené na predvolenú hodnotu.
     */
    public ResourceCache() {
        this.capacity = DEFAULT_CAPACITY;
        cache = new HashMap<Integer, CachedResource<T>>(capacity);
    }

    /**
     * Vloženie prvku do cahce. Ak je cache plný odstráni sa najstarší prvok.
     * Novo vloženému prvku sa riradí čas vloženia.
     *
     * @param key jedinečný kľúč.
     * @param resource vkladaný prvok.
     */
    public void insert(int key, T resource) {
        if (capacity > cache.size()) {
            cache.put(key, new CachedResource<T>(resource, new Date(), key));
        } else {
            cache.remove(findLeastUsed());
            cache.put(key, new CachedResource<T>(resource, new Date(), key));
        }
    }

    /**
     * Vezme jedinečný kľúč prvku a vyhľadá podľa neho prvok.
     *
     * @param key kľúč hľadaného prvku.
     * @return true ak sa prvok v cache nachádza, false v prípade, že sa
     * nenachádza.
     */
    public boolean isCached(int key) {
        return cache.containsKey(key);
    }

    /**
     * Nájdenie najstaršieho prvku v cache. Metóda porovnáva časi všetkých
     * prvkov v cache a najstarší sa vráti.
     *
     * @return kľúč nájdeného prvku.
     */
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
     * Odobratie prvku z cache podľa zadaného kľúča.
     *
     * @param key kľúč prvku, ktorý má byť odobraný.
     * @return odobraný prvok.
     */
    public T remove(int key) {
        return cache.remove(key).getResource();
    }

    /**
     * Odstránenie všetkých uložených prvkov. Vymazanie celého resource cache.
     */
    public void clearCache() {
        if (!cache.isEmpty()) {
            System.out.println("[Resource Cache] clearing cache ...");
            cache.clear();
            System.out.println("[Resource Cache] clearing cache done");
        }
    }

    /**
     * Kontainer pre prvok uložený v cache. Prvku dopĺňa potrebné informácie pre
     * cache ako je kľúč prvku a čas jeho pridania.
     * @param <T> typ prvkov ukládaných do cache.
     */
    private class CachedResource<T> {

        private T resource;
        private Date timestamp;
        private int key;

        /**
         * Vytvorí kontainer pre prvok.
         *
         * @param resource samotný prvok.
         * @param timeStamp čas vloženia/použitia do cache.
         * @param key kľúč prvku.
         */
        public CachedResource(T resource, Date timeStamp, int key) {
            this.resource = resource;
            this.timestamp = timeStamp;
            this.key = key;
        }

        /**
         * Vráti jedinečný kľúč prvku.
         *
         * @return kľúč prvku.
         */
        public int getKey() {
            return key;
        }

        /**
         * Vráti čas uloženia prvku.
         *
         * @return object's time stamp
         */
        public Date getTimeStamp() {
            return this.timestamp;
        }

        /**
         * Pozmení šas vloženia prvku.
         *
         * @param stamp new insertion time.
         */
        public void setTimeStamp(Date stamp) {
            this.timestamp = stamp;
        }

        /**
         * Poskytoje prístup k prvkom uložených v cache.
         *
         * @return uložený prvok.
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
            if (this.resource != other.resource && (this.resource == null
                    || !this.resource.equals(other.resource))) {
                return false;
            }
            return true;
        }
    }
}
