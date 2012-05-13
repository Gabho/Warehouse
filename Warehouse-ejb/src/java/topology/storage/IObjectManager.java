/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

import javax.ejb.Local;
import persistence.MasterDataEntity;
import topology.resource.management.Item;

/**
 *
 * @author Mao
 */  
@Local
public interface IObjectManager {
    public void insert(Object object);
    public String remove(String hashKey);
    public Object find(String hashKey);
    
    public int addItem(Item item);
    public int removeItem(int quantity, MasterDataEntity masterData);
    public int findItem();
    public int getFreeSpace();
    public String printStorage();
}
