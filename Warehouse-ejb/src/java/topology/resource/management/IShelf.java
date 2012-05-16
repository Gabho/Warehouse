package topology.resource.management;

import java.util.List;

/**
 * Reprezentácia najmenšej úložnej jednotky v sklade (poličky).
 * @author Martin Lofaj
 */
public interface IShelf extends ICoordinable{
    
    //Dohodnuty interface
    /**
     * Vracia zoznam objektov typu IItem, uložených na poličke.
     * @return items stored in the shelf.
     */
    public List<IItem> getItems();
    
    /**
     * Vráti maximálnu kapacitu polčky.
     * @return kapacita poličky.
     */
    public int getCapacity();
    
    /**
     * Vráti počet položiek zadaného materiálu.
     * @param type typ materiálu.
     * @return počet položiek.
     */
    public int getItemCount(String type);
    
    /**
     * Vypočíta voľné miesto na poličke.
     * @return voľné miesto.
     */
    public int getFreeSpace();
    
    /**
     * Vráti identifikátor poličky.
     * @return identifikátor poličky.
     */
    public int getID();
    
    /**
     * Vloženie položky na poličku.
     * @param item položka, ktorá sa ma na poličku umiestniť.
     */
    public void insertItem(IItem item);
    
    /**
     * Vybratie položky z poličky.
     * @param item položka, ktorá má byť vybratá.
     * @return polžka odstránená z poličky.
     */
    public IItem removeItem(IItem item);
    
    /**
     * Odstráni celú poličku zo skaldu, zmaže jej údaje z databázy.
     * @return všetky položky, ktoré sa na poličke vyskytovali.
     */
    public List<IItem> remove();
    
}
