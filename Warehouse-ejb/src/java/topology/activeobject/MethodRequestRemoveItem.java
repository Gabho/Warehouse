package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.MasterDataEntity;
import topology.storage.IObjectManager;

/**
 * Trieda reprezentujúca požiadavku na odstránenie položiek z databázy.
 * @author Gabriel Cervenak
 */
public class MethodRequestRemoveItem implements IMethodRequest{
    
    private IObjectManager manager;
    private int quantity;
    private MasterDataEntity masterData;
    private Future<Boolean> result;
    
    /**
     * Vytvorenie novej požiadavky na odstránenie položiek.
     * @param quantity počet položiek, ktoré chceme odstrániť.
     * @param masterData master dáta položiek, ktoré chceme odstrániť.
     * @param result Future, do ktorého bude zapísané oznámenie o výsledku operácie.
     * @param manager objekt manažér, ktorý implementuje funkcionalitu.
     */
    public MethodRequestRemoveItem(int quantity, MasterDataEntity masterData, Future<Boolean> result, IObjectManager manager){
        this.quantity = quantity;
        this.masterData = masterData;
        this.result = result;
        this.manager = manager;
    }

    /**
     * Zavolanie metódy na odstránenie položiek. Oznámenie o úspešnosti vykonanie operácie
     * sa zapíše do objektu Future.
     */
    @Override
    public void call() {
        result.addResult(manager.removeItem(quantity, masterData));
        Logger LOGGER = Logger.getLogger(MethodRequestRemoveItem.class.getName());
        LOGGER.log(Level.INFO, "..............................MethodRequest Remove Item: {0} {1}",new Object[]{masterData.getId(), quantity});
    }
    
}
