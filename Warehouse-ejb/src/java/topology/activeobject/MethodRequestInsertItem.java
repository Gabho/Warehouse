/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;
import topology.resource.management.Item;
import topology.storage.IObjectManager;



/**
 *
 * @author Gabo
 */
public class MethodRequestInsertItem implements IMethodRequest {
    
    private Item item;
    private IObjectManager manager;
    
    public MethodRequestInsertItem(Item item, IObjectManager manager){
        this.item = item;
        this.manager = manager;
    }

    @Override
    public void call() {
        manager.addItem(item);
        Logger LOGGER = Logger.getLogger(MethodRequestInsertItem.class.getName());
        LOGGER.log(Level.INFO, "..............................MethodRequest Insert Item: {0} {1}",new Object[]{item.getType(),item.getAmount()});
    }
    
}
