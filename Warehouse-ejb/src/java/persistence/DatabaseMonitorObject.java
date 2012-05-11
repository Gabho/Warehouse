/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.List;
import topology.resource.management.Item;
import topology.resource.management.Position;

/**
 *
 * @author Gabriel Cervenak
 */
//Trieda predstavujúca databázu ako monitor objekt
public abstract class DatabaseMonitorObject {
    
    private MonitorLock lock = new MonitorLock();
    private MonitorCondition condition = new MonitorCondition(lock);
    
    //Vracia zoznam položiek nachádzajúcich sa na danej poličke
    public List<Item> getShelf(Position position) {
        lock.lock();
        List<Item> items = synchronizedGetShelf(position);
        lock.unlock();
        return items;
    }

    //Pridá poličku na danú pozíciu
    public void addShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //Aktualizuje obsah poličky na danej pozícii
    public void updateShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //Odstráni celú poličku z danej pozície
    public void removeShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public int search(String string) {
        int quantity;
        lock.lock();
        quantity = synchronizedSearch(string);
        lock.unlock();
        return quantity;
    }
    
    abstract List<Item> synchronizedGetShelf(Position position);
    abstract int synchronizedSearch(String string);
    abstract void synchronizedAddMasterData(MasterDataEntity masterData);
    abstract void synchronizedRemoveMasterData(String id);
    abstract List<MasterDataEntity> synchronizedGetMasterData();
    
}
