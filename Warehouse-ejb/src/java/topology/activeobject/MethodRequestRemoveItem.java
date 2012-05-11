/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.MasterDataEntity;
import topology.storage.IObjectManager;

/**
 *
 * @author Gabo
 */
public class MethodRequestRemoveItem implements IMethodRequest{
    
    private IObjectManager manager;
    private int quantity;
    private MasterDataEntity masterData;
    
    public MethodRequestRemoveItem(int quantity, MasterDataEntity masterData, IObjectManager manager){
        this.quantity = quantity;
        this.masterData = masterData;
        this.manager = manager;
    }

    @Override
    public void call() {
        //manager.removeItem(quantity, masterData);
        Logger LOGGER = Logger.getLogger(MethodRequestRemoveItem.class.getName());
        LOGGER.log(Level.INFO, "..............................MethodRequest Remove Item: {0} {1}",new Object[]{masterData.getId(), quantity});
    }
    
}
