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
    public Future<Integer> search(String search) {
        Future<Integer> result = new Future<Integer>();
        return result;
    }

    @Override
    public void insertMasterData(MasterDataEntity masterData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeMasterData(MasterDataEntity masterData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertNewItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IFuture<List<Item>> makeOrder(List<Item> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
