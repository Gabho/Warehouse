package topology.activeobject;

import java.util.List;
import javax.ejb.Local;
import persistence.MasterDataEntity;
import topology.resource.management.Item;

/**
 * Rozhranie definujúce funkcionalitu poskytujúcu klientom.
 * Definuje funkcionalitu Proxy.
 * @author Gabriel Cervenak
 */
@Local
public interface IFunctionality {
    
    /**
     * Vyhľadávanie položiek na základe reťazca.
     * @param search reťazec znakov, na základe ktorého prebieha vyhľadávanie.
     * @return vracia objekt Future,ktorý obsahuje výsledok operácie.
     */
    IFuture<SearchResult> search(String search);
    
    /**
     * Vloženie master dát do databázy.
     * @param masterData master dáta, ktoré majú byť vložené.
     */
    void insertMasterData(MasterDataEntity masterData);
    
    /**
     * Odstránenie master dát z databázy.
     * @param id identifikátor master dát, ktoré majú byť odstránené.
     */
    void removeMasterData(String id);
    
    /**
     * Vloženie novej položky do databázy.
     * @param item položka, ktorá má byť uložená.
     * @return objekt Fututre, ktorý obsahuje oznámenie o tom, či operácia prebehla správne.
     */
    IFuture<Boolean> insertNewItem(Item item);
    
    /**
     * Odstránenie položky z databázy.
     * @param quantity počet položiek, ktoré sa majú dostrániť.
     * @param masterData master dáta položiek, ktoré sa majú odstrániť.
     * @return objekt Future, ktorý obsahuje oznámenie o tom, či operácia prebehla správne.
     */
    IFuture<Boolean> removeItem(int quantity, MasterDataEntity masterData);
    
    /**
     * Vytvorenie objednávky.
     * @param items zoznam položiek objednávky.
     * @return zoznam položiek, ktoré sú aktuálne na sklade.
     */
    IFuture<List<Item>> makeOrder(List<Item> items);
    
}
