package topology.activeobject;

import communication.CommunicationLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import persistence.Database;
import persistence.MasterDataEntity;

/**
 * Trieda reprezentujúca požiadavku na vyhľadávanie.
 *
 * @author Gabriel Cervenak
 */
public class MethodRequestSearch implements IMethodRequest {

    private static final Logger LOGGER = Logger.getLogger(MethodRequestSearch.class.getName());
    private String search;
    private Future<SearchResult> result;
    private Database database;
    private String warehouseID;
    private List<String> resultList = new ArrayList<String>();
    private CommunicationLocal communication;
    
    /**
     * Vytvorenie novej požiadavky na vyhľadávanie položiek.
     * @param search vyhľadávací reťazec.
     * @param result Future, do ktorého bude vložený výsledok vyhľadávania.
     * @param database EJB poskytujúci prístup k databáze.
     * @param warehouseID identifikátor skladu.
     * @param communication EJB zabezpečujúci komunikáciu.
     */
    public MethodRequestSearch(String search, Future<SearchResult> result, Database database, String warehouseID, CommunicationLocal communication) {
        this.search = search;
        this.result = result;
        this.database = database;
        this.warehouseID = warehouseID;
        //this.resultList = resultList;
        this.communication = communication;
    }

    /**
     * Vytvorenie nového vlákna, v ktorom prebehne operácia vyhľadávania.
     */
    @Override
    public void call() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                    resultList = communication.searchRemote(search);
                    List<MasterDataEntity> masterData = database.search(search);
                    for (MasterDataEntity master : masterData) {
                        resultList.add(("WAREHOUSE:" + warehouseID + ": " + master.getId() + " ,quantity=" + Integer.toString(master.getQuantity())));
                    }
                    result.addResult(new SearchResult(resultList));
            }
        };
        thread.start();
    }
}
