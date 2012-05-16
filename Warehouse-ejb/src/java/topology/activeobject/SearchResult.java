package topology.activeobject;

import java.util.List;
import persistence.MasterDataEntity;

/**
 * Trieda reprezentujúca výsledok vyhľadávania.
 * @author Gabriel Cervenak
 */
public class SearchResult {
    
    private List<MasterDataEntity> masterData;
    //konstatnta identifikujúca sklad
    private static final String warehouseID = "Gabo-Warehouse";
    
    /**
     * Vytvorenie nového výsledku vyhľadávania.
     * @param masterData zoznam master dát, ktoré sú výsledkom vyhľadávania.
     */
    public SearchResult(List<MasterDataEntity> masterData){
        this.masterData = masterData;
    }

    /**
     * Vracia zoznam master dát, ktoré sú výsledkom vyhľadávania.
     * @return zoznam master dát.
     */
    public List<MasterDataEntity> getMasterData() {
        return masterData;
    }

    /**
     * Vracia identifikátor, ktorý je pre každý warehouse jedinečný.
     * @return identifikátor.
     */
    public String getWarehouseID() {
        return warehouseID;
    }
    
}
