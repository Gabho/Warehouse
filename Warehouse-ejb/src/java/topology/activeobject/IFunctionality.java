/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.List;
import javax.ejb.Local;
import persistence.MasterDataEntity;
import topology.resource.management.Item;

/**
 *
 * @author Gabo
 */
@Local
public interface IFunctionality {
    
    IFuture<Integer> search(String search);
    
    void insertMasterData(MasterDataEntity masterData);
    
    void removeMasterData(String id);
    
    void insertNewItem(Item item);
    
    void removeItem(int quantity, MasterDataEntity masterData);
    
    IFuture<List<Item>> makeOrder(List<Item> items);
    
}
