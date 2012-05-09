/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.logging.Logger;
import persistence.Database;

/**
 *
 * @author Gabo
 */
public class MethodRequestSearch implements IMethodRequest {

    private static final Logger LOGGER = Logger.getLogger(MethodRequestSearch.class.getName());
    private String search;
    private Future<Integer> result;
    private Database database;

    public MethodRequestSearch(String search, Future<Integer> result, Database database) {
        this.search = search;
        this.result = result;
        this.database = database;
    }

    @Override
    public void call() {
        int quantity = database.search(search);
        result.addResult(quantity);
    }
    
}
