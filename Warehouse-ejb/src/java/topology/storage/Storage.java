/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

import java.util.HashMap;
import javax.ejb.Stateful;
import topology.configuration.AbstractComponent;


/**
 *
 * @author Mao
 */
@Stateful
public class Storage implements IObjectManager {

   private HashMap manager;

    public Storage() {
        manager = new HashMap();
    }
   
    @Override
    public void insert(Object object) {
        String hashKey;
        AbstractComponent object1 = (AbstractComponent) object;
        hashKey = object1.getCode();

       manager.put(hashKey, object);
    }

    @Override
    public void remove(Object object) {
        manager.remove(object.hashCode());
    } 

    @Override
    public Object find(String hashKey) {
        return manager.get(hashKey);
    }
    
    @Override
    public int addItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int removeItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int findItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getFreeSpace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
