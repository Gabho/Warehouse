package topology.activeobject;

import communication.CommunicationLocal;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import persistence.Database;
import persistence.MasterDataEntity;
import topology.resource.management.Item;
import topology.storage.IObjectManager;

/**
 * EJB reprezentujúci proxy, ktorý vytvorí požiadavky na metódy a uloží ich do
 * radu požiadaviek.
 *
 * @author Gabriel Cervenak
 */
@Stateless
public class Proxy implements IFunctionality, IProxyLocal {

    private static final Logger LOGGER = Logger.getLogger(Proxy.class.getName());
    private static Scheduler scheduler = new Scheduler();
    @EJB
    private Database database;
    @EJB
    private IObjectManager manager;
    @EJB
    private CommunicationLocal communication;
    private String warehouseID = getID();

    @Override
    public IFuture<SearchResult> search(String search) {
        //List<String> resultList = new ArrayList<String>();
        //communication.searchRemote(search);
        Future<SearchResult> result = new Future<SearchResult>();
        scheduler.enqueue(new MethodRequestSearch(search, result, database, warehouseID, communication));
        return result;
    }

    @Override
    public void insertMasterData(MasterDataEntity masterData) {
        scheduler.enqueue(new MethodRequestInsertMD(masterData, database));
    }

    @Override
    public void removeMasterData(String id) {
        scheduler.enqueue(new MethodRequestRemoveMD(id, database));
    }

    @Override
    public IFuture<Boolean> insertNewItem(Item item) {
        Future<Boolean> result = new Future<Boolean>();
        scheduler.enqueue(new MethodRequestInsertItem(item, result, manager));
        return result;
    }

    @Override
    public IFuture<Boolean> removeItem(int quantity, MasterDataEntity masterData) {
        Future<Boolean> result = new Future<Boolean>();
        scheduler.enqueue(new MethodRequestRemoveItem(quantity, masterData, result, manager));
        return result;
    }

    @Override
    public IFuture<List<Item>> makeOrder(List<Item> items) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Načítanie ID skladu zo súboru. Ak sa súbor nenájde, automaticky sa prielí id "Warehouse".
     * @return reťazec predstavujúci id skladu.
     */
    private String getID() {
        String returnID;
        FileInputStream fstream = null;
        try {
            String filePath = getClass().getProtectionDomain().getCodeSource().getLocation().toString();

            //file path calculation when deployed in NetBeans
            int endindex = filePath.indexOf("dist/gfdeploy");
            filePath = filePath.substring(6, endindex);
            filePath = filePath + ("Warehouse-ejb/src/java/topology/activeobject/warehouseID.txt");

            //file path calculation when deployed in cmd
//            filePath = filePath.substring(6);
//            filePath = filePath + ("topology/activeobject/warehouseID.txt");

            fstream = new FileInputStream(filePath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            returnID = br.readLine();
            System.out.println("..............................PROXY WAREHOUSE ID: "+returnID);
            return returnID;
        } catch (FileNotFoundException ex) {
            return "Warehouse";
        } catch (IOException ex) {
            return "Warehouse";
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(SearchResult.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getWarehouseID() {
        return warehouseID;
    }

}
