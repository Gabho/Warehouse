package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;
import topology.resource.management.Item;
import topology.storage.IObjectManager;

/**
 * Trieda reprezentujúca požiadavku na vloženie položiek do databázy.
 * @author Gabriel Cervenak
 */
public class MethodRequestInsertItem implements IMethodRequest {

    private Item item;
    private Future<Boolean> result;
    private IObjectManager manager;

    /**
     * Vytvorenie novej požiadavky na vloýenie.
     * @param item položka, ktorá má byť vložená.
     * @param result Future, do ktorého má byť zapísaný výsledok operácie.
     * @param manager Objekt manažér, ktorý implementuje funkcionalitu vloženia položky.
     */
    public MethodRequestInsertItem(Item item, Future<Boolean> result, IObjectManager manager) {
        this.item = item;
        this.result = result;
        this.manager = manager;
    }

    /**
     * Zavolanie metódy pridania položky na inštancii objekt manažera, pričom sa oznámenie o prebehnutí
     * operácie uloží do objektu Future.
     */
    @Override
    public void call() {
        result.addResult(manager.addItem(item));
        Logger LOGGER = Logger.getLogger(MethodRequestInsertItem.class.getName());
        LOGGER.log(Level.INFO, "..............................MethodRequest Insert Item: {0} {1}", new Object[]{item.getType(), item.getAmount()});
    }
}
