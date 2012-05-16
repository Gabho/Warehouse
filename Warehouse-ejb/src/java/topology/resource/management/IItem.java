/*
 * Item functionality
 */
package topology.resource.management;

import java.util.Date;

/**
 * Rozhranie definujúce operácie nad najmešou položkou skladu.
 * Item predstavuje položku (tovar), ktorá je na sklade.
 * @author Martin Lofaj
 */
public interface IItem {
    
    /** 
     * Prístup k množstvu tovaruv položke.
     * @return počet položiek.
     */
    public int getAmount();
    
    /** 
     * Vráti typ materialu, ktorý je uložený v položke.
     * @return typ materiálu.
     */
    public String getType();
    
    /**
     * Vráti popis pre danú položku.
     * @return popis materiálu.
     */
    public String getDescription();
    
    /**
     * Vráti dátum spotreby materiálu v položke.
     * @return dátum spotreby.
     */
    public Date getExpiration();
    
    /**
     * Vráti pozíciu položky, nad ktorou je metóda volaná, v skalde.
     * @return pozícia v sklade.
     */
    public Position getPosition();
    
    /**
     * Nastaví pozíciu pre daný tovar v skade.
     * @param position pozícia v sklade.
     */
    public void setPosition(Position position);

    /**
     * Nastaví množstvo daného tovaru v položke.
     * @param amount nové množstvo tovaru.
     */
    public void setAmount(int amount);
}
