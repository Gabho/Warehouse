/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

import javax.ejb.Local;
import topology.configuration.AbstractComponent;

/**
 *
 * @author Mao
 */
@Local
public interface StorageLocal {
    public void insert(Object object);
    public void remove(Object object);
    public Object find(String hashKey);
    
    public int addItem();
    public int removeItem();
    public int findItem();
    public int getFreeSpace();
}
