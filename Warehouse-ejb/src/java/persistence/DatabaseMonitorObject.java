/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.List;
import topology.resource.management.IItem;

/**
 *
 * @author Gabriel Cervenak
 */
//Trieda predstavujúca databázu ako monitor objekt
public abstract class DatabaseMonitorObject {
    
    private MonitorLock lock = new MonitorLock();
    private MonitorCondition condition = new MonitorCondition(lock);
    
    //Vracia zoznam položiek nachádzajúcich sa na danej poličke
    public List<IItem> getShelf(int shelfId) {
        lock.lock();
        List<IItem> items = synchronizedGetShelf(shelfId);
        lock.unlock();
        return items;
    }

    //Aktualizuje obsah poličky na danej pozícii
    public void updateShelf(List<IItem> items, int shelfId) {
        lock.lock();
        synchronizedUpdateShelf(items, shelfId);
        lock.unlock();
    }
    
    //Odstráni celú poličku z danej pozície
    public void removeShelf(int shelfId) {
        lock.lock();
        synchronizedRemoveShelf(shelfId);
        lock.unlock();
    }
    
    //Prida MasterData entitu do tabuľky
    public void addMasterData(MasterDataEntity masterData) {
        lock.lock();
        synchronizedAddMasterData(masterData);
        lock.unlock();
    }
    
    //Vymaže z tabuľky MasterData entitu spolu so všetkými prislúchajúcimi itemami
    public void removeMasterData(String id) {
        lock.lock();
        synchronizedRemoveMasterData(id);
        lock.unlock();
    }
    
    //Vracia zoznam všetkých master dát v tabuľke
    public List<MasterDataEntity> getMasterData() {
        lock.lock();
        List<MasterDataEntity> masterData = synchronizedGetMasterData();
        lock.unlock();
        return masterData;
    }

    //Vráti počet nájdených itemov (hľadanie podľa master data entity)
    public List<MasterDataEntity> search(String string) {
        lock.lock();
        List<MasterDataEntity> masterData = synchronizedSearch(string);
        lock.unlock();
        return masterData;
    }
    
    abstract List<IItem> synchronizedGetShelf(int shelfId);
    abstract void synchronizedUpdateShelf(List<IItem> items, int shelfId);
    abstract void synchronizedRemoveShelf(int shelfId);
    abstract List<MasterDataEntity> synchronizedSearch(String string);
    abstract void synchronizedAddMasterData(MasterDataEntity masterData);
    abstract void synchronizedRemoveMasterData(String id);
    abstract List<MasterDataEntity> synchronizedGetMasterData();
    
}
