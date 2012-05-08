/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.List;
import persistence.MasterDataEntity;
import topology.resource.management.Item;

/**
 *
 * @author Gabo
 */
public class Proxy implements IFunctionality {
    
    private Scheduler scheduler = new Scheduler();

    @Override
    public synchronized Future<Integer> search(String search) {
        Future<Integer> result = new Future<Integer>();
        scheduler.enqueue(new MethodRequestSearch(search, result));
        return result;
    }

    @Override
    public synchronized void insertMasterData(MasterDataEntity masterData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public synchronized void removeMasterData(MasterDataEntity masterData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public synchronized void insertNewItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public synchronized void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public synchronized IFuture<List<Item>> makeOrder(List<Item> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
