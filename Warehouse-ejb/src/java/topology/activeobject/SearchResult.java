package topology.activeobject;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.MasterDataEntity;

/**
 * Trieda reprezentujúca výsledok vyhľadávania.
 *
 * @author Gabriel Cervenak
 */
public class SearchResult {

    private List<MasterDataEntity> masterData;
    //konstatnta identifikujúca sklad
    private static String warehouseID = "Warehouse";

    /**
     * Vytvorenie nového výsledku vyhľadávania.
     * Zároveň získa ID zo súboru, pokiaľ sa súbor nepodarí načítať, id skladu bude "Warehouse"
     * @param masterData zoznam master dát, ktoré sú výsledkom vyhľadávania.
     */
    public SearchResult(List<MasterDataEntity> masterData) {
        FileInputStream fstream = null;
        try {
            this.masterData = masterData;
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
            SearchResult.warehouseID = br.readLine();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(SearchResult.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Vracia zoznam master dát, ktoré sú výsledkom vyhľadávania.
     *
     * @return zoznam master dát.
     */
    public List<MasterDataEntity> getMasterData() {
        return masterData;
    }

    /**
     * Vracia identifikátor, ktorý je pre každý warehouse jedinečný.
     *
     * @return identifikátor.
     */
    public String getWarehouseID() {
        return warehouseID;
    }
}
