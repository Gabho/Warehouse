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
public interface IFunctionality {
    
    //vráti počet nájdených itemov vyhovujúcich hľadanému reťazcu
    IFuture<Integer> search(String search);
    
    void insertMasterData(MasterDataEntity masterData);
    
    void removeMasterData(MasterDataEntity masterData);
    
    void insertNewItem(Item item);
    
    void removeItem(Item item);
    
    IFuture<List<Item>> makeOrder(List<Item> items);
    
}
