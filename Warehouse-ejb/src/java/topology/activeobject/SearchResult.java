/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.List;
import persistence.MasterDataEntity;

/**
 *
 * @author Gabriel Cervenak
 */
public class SearchResult {
    
    private List<MasterDataEntity> masterData;
    //konstatnta identifikujúca sklad
    private static final String warehouseID = "Gabo-Warehouse";
    
    public SearchResult(List<MasterDataEntity> masterData){
        this.masterData = masterData;
    }

    public List<MasterDataEntity> getMasterData() {
        return masterData;
    }

    public String getWarehouseID() {
        return warehouseID;
    }
    
}
