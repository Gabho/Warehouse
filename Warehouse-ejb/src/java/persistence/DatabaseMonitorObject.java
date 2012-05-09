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
 * @author Gabo
 */
public abstract class DatabaseMonitorObject {
    
    private MonitorLock lock = new MonitorLock();
    private MonitorCondition condition = new MonitorCondition(lock);
    
    //vracia zoznam poloziek nachadzajucich sa na danej policke
    public List<Item> getShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //prida policku na danu poziciu
    public void addShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //aktualizuje obsah policky na danej pozicii
    public void updateShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //odstrani celu policku z danej pozicie
    public void removeShelf(Position position) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //prida MasterData entitu do tabulky
    public void addMasterData(MasterDataEntity masterData) {
        lock.lock();
        synchronizedAddMasterData(masterData);
        lock.unlock();
    }
    
    public void removeMasterData(MasterDataEntity masterData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<MasterDataEntity> getMasterData() {
        lock.lock();
        List<MasterDataEntity> masterData = synchronizedGetMasterData();
        lock.unlock();
        return masterData;
    }

    //vrati pocet najdenych itemov (hladanie podla master entity)
    public int search(String string) {
        int quantity;
        lock.lock();
        quantity = synchronizedSearch(string);
        lock.unlock();
        return quantity;
    }
    
    abstract int synchronizedSearch(String string);
    abstract void synchronizedAddMasterData(MasterDataEntity masterData);
    abstract void synchronizedRemoveMasterData(MasterDataEntity masterData);
    abstract List<MasterDataEntity> synchronizedGetMasterData();
    
}
