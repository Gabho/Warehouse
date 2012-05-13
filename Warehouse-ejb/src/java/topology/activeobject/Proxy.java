/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import persistence.Database;
import persistence.MasterDataEntity;
import topology.resource.management.Item;
import topology.storage.IObjectManager;

/**
 *
 * @author Gabo
 */
@Stateless
public class Proxy implements IFunctionality {

    private static final Logger LOGGER = Logger.getLogger(Proxy.class.getName());
    private Scheduler scheduler = new Scheduler();
    @EJB
    private Database database;
    @EJB
    private IObjectManager manager;

    @Override
    public IFuture<SearchResult> search(String search) {
        Future<SearchResult> result = new Future<SearchResult>();
        scheduler.enqueue(new MethodRequestSearch(search, result, database));
        return result;
    }

//    @Override
//    public IFuture<Integer> search(String search) {
//        if(scheduler != null){
//            LOGGER.log(Level.INFO, "..............................Proxy: scheduler nie je null....................");
//        }
//        else
//            LOGGER.log(Level.INFO, "..............................Proxy: scheduler je null !!!!!!....................");
//        Future<Integer> result = new Future<Integer>();
//        scheduler.enqueue(new MethodRequestSearch(search, result, database));
//        return result;
//    }
    @Override
    public void insertMasterData(MasterDataEntity masterData) {
        scheduler.enqueue(new MethodRequestInsertMD(masterData, database));
    }

    @Override
    public void removeMasterData(String id) {
        scheduler.enqueue(new MethodRequestRemoveMD(id, database));
    }

    @Override
    public void insertNewItem(Item item) {
        scheduler.enqueue(new MethodRequestInsertItem(item, manager));
    }

    @Override
    public void removeItem(int quantity, MasterDataEntity masterData) {
        scheduler.enqueue(new MethodRequestRemoveItem(quantity, masterData, manager));
    }

    @Override
    public IFuture<List<Item>> makeOrder(List<Item> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
