/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;
import topology.resource.management.Item;
import topology.resource.management.TaskFailureException;
import topology.storage.IObjectManager;

/**
 *
 * @author Gabo
 */
public class MethodRequestInsertItem implements IMethodRequest {

    private Item item;
    private Future<Boolean> result;
    private IObjectManager manager;

    public MethodRequestInsertItem(Item item, Future<Boolean> result, IObjectManager manager) {
        this.item = item;
        this.result = result;
        this.manager = manager;
    }

    @Override
    public void call() {
        result.addResult(manager.addItem(item));
        Logger LOGGER = Logger.getLogger(MethodRequestInsertItem.class.getName());
        LOGGER.log(Level.INFO, "..............................MethodRequest Insert Item: {0} {1}", new Object[]{item.getType(), item.getAmount()});
    }
}
