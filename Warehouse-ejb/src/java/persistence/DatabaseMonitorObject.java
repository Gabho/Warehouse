package persistence;

import java.util.List;
import topology.resource.management.IItem;

/**
 * Abstraktná trieda, ktorá definuje funkcionalitu databázy
 * a zároveň reprezentuje Monitor Object. Zabezpečuje synchronizovaný
 * prístup a vykonávanie operácií nad databázou.
 * @author Gabriel Cervenak
 */
public abstract class DatabaseMonitorObject {
    
    private MonitorLock lock = new MonitorLock();
    private MonitorCondition condition = new MonitorCondition(lock);
    
    /**
     * Vráti poličku s daným id
     * @param shelfId id poličky, ktorú chcem získať
     * @return zoznam itemov z danej poličky
     */
    public List<IItem> getShelf(int shelfId) {
        lock.lock();
        List<IItem> items = synchronizedGetShelf(shelfId);
        lock.unlock();
        return items;
    }

    /**
     * Aktualizuje obsah poličky s daným id
     * @param items zoznam itemov, ktoré sa na poličke majú nachádzať
     * @param shelfId id poličky, ktorú chcem aktualizovať
     */
    public void updateShelf(List<IItem> items, int shelfId) {
        lock.lock();
        synchronizedUpdateShelf(items, shelfId);
        lock.unlock();
    }
    
    /**
     * Odstráni poličku s daným id
     * @param shelfId id poličky, ktorú chcem odstrániť
     */
    public void removeShelf(int shelfId) {
        lock.lock();
        synchronizedRemoveShelf(shelfId);
        lock.unlock();
    }
    
    /**
     * Vkladanie nových master dát
     * @param masterData master data, ktoré majú byť vložené
     */
    public void addMasterData(MasterDataEntity masterData) {
        lock.lock();
        synchronizedAddMasterData(masterData);
        lock.unlock();
    }
    
    /**
     * Odstránenie master dát spolu so všetkými prislúchajúcimi položkami
     * @param id reťazec reprezentujúci id master dát
     */
    public void removeMasterData(String id) {
        lock.lock();
        synchronizedRemoveMasterData(id);
        lock.unlock();
    }
    
    /**
     * Vracia zoznam všetkých master dát nachádzajúcich sa v databáze
     * @return zoznam master dát
     */
    public List<MasterDataEntity> getMasterData() {
        lock.lock();
        List<MasterDataEntity> masterData = synchronizedGetMasterData();
        lock.unlock();
        return masterData;
    }

    /**
     * Hľadanie položiek na základe vyhľadávacieho reťazca
     * @param string vyhľadávací reťazec
     * @return zoznam master dát
     */
    public List<MasterDataEntity> search(String string) {
        lock.lock();
        List<MasterDataEntity> masterData = synchronizedSearch(string);
        lock.unlock();
        return masterData;
    }
    
    /**
     * Pozastaví vykonávanie vlákna až pokiaľ iné vlákno nezavolá metódu goNotify()
     */
    public void gowait(){
        condition.goWait();
    }
    
    /**
     * Upovedomí pozastavené vlákno, že môže pokračovať vo vykonávaní
     */
    public void goNotify(){
        condition.goNotify();
    }
    
    abstract List<IItem> synchronizedGetShelf(int shelfId);
    abstract void synchronizedUpdateShelf(List<IItem> items, int shelfId);
    abstract void synchronizedRemoveShelf(int shelfId);
    abstract List<MasterDataEntity> synchronizedSearch(String string);
    abstract void synchronizedAddMasterData(MasterDataEntity masterData);
    abstract void synchronizedRemoveMasterData(String id);
    abstract List<MasterDataEntity> synchronizedGetMasterData();
    
}
