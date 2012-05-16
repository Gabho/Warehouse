package topology.storage;

import javax.ejb.Local;
import persistence.MasterDataEntity;
import topology.resource.management.Item;

/**
 * Lokálne rozhranie EJB Objekt Manažéra.
 *
 * @author Maartin Pakandl
 */
@Local
public interface IObjectManager {

    /**
     * Vloženie objektu do manažéra.
     * @param object objekt pre vloženie
     */
    public void insert(Object object);

    /**
     * Odstránenie objektu z manažéra.
     * @param hashKey kľúč objektu pre odstránenie
     * @return správu o vykonaní
     */
    public String remove(String hashKey);

    /**
     * Nájdenie objektu uloženého v Objekt Manažérovi.
     * @param hashKey ľúč objektu pre nájdenie
     * @return nájdený objekt
     */
    public Object find(String hashKey);

    /**
     * Pridanie položky do skladu.
     * @param item položka
     * @return stav vykonania
     */
    public boolean addItem(Item item);

    /**
     * Odobratie položky zo skladu.
     * @param quantity kvantita
     * @param masterData typ položky
     * @return stav vykonania
     */
    public boolean removeItem(int quantity, MasterDataEntity masterData);

    /**
     * Informácia o voľnom mieste v sklade.
     * @return voľné miesto
     */
    public int getFreeSpace();

    /**
     * Výpis objektov tvoriacich topológiu skladu.
     * @return výpis topológie
     */
    public String printStorage();
}
