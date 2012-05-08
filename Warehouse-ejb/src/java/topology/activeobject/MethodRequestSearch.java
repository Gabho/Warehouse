/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import javax.ejb.EJB;
import persistence.Database;

/**
 *
 * @author Gabo
 */
public class MethodRequestSearch implements IMethodRequest {

    String search;
    Future<Integer> result;
    @EJB
    Database database;

    public MethodRequestSearch(String search, Future<Integer> result) {
        this.search = search;
        this.result = result;
    }

    @Override
    public void call() {
        int quantity = database.search(search);
        result.addResult(quantity);
    }
}
