/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.List;
import java.util.logging.Logger;
import persistence.Database;
import persistence.MasterDataEntity;

/**
 *
 * @author Gabo
 */
public class MethodRequestSearch implements IMethodRequest {

    private static final Logger LOGGER = Logger.getLogger(MethodRequestSearch.class.getName());
    private String search;
    private Future<SearchResult> result;
    private Database database;

    public MethodRequestSearch(String search, Future<SearchResult> result, Database database) {
        this.search = search;
        this.result = result;
        this.database = database;
    }

    @Override
    public void call() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                List<MasterDataEntity> masterData = database.search(search);
                result.addResult(new SearchResult(masterData));
            }
        };
        thread.start();
    }
}
