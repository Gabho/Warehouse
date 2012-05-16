package topology.activeobject;

import java.util.List;
import java.util.logging.Logger;
import persistence.Database;
import persistence.MasterDataEntity;

/**
 * Trieda reprezentujúca požiadavku na vyhľadávanie.
 * @author Gabriel Cervenak
 */
public class MethodRequestSearch implements IMethodRequest {

    private static final Logger LOGGER = Logger.getLogger(MethodRequestSearch.class.getName());
    private String search;
    private Future<SearchResult> result;
    private Database database;

    /**
     * Vytvorenie novej požiadavky na hľadanie.
     * @param search vyhľadávací reťazec.
     * @param result Future, do ktorého bude uložené oznámenie o úspešnosti prebehnutia operácie.
     * @param database EJB poskytujúci prístup k databáze.
     */
    public MethodRequestSearch(String search, Future<SearchResult> result, Database database) {
        this.search = search;
        this.result = result;
        this.database = database;
    }

    /**
     * Vytvorenie nového vlákna, v ktorom prebehne operácia vyhľadávania.
     */
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
