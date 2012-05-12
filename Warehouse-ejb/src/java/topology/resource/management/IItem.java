/*
 * Item functionality
 */
package topology.resource.management;

import java.util.Date;

/**
 * Inteface defining item functionality.
 * @author Martin Lofaj
 */
public interface IItem {
    
    /** 
     * Returns amount of items of a particular type 
     * @return amount of items
     */
    public int getAmount();
    
    /** 
     * Gets the type of the item represented by this object.
     * @return type of the item
     */
    public String getType();
    
    /**
     * Gets description of the item represented by this object.
     * @return item's description
     */
    public String getDescription();
    
    /**
     * Gets expiration date of the item represented by this object.
     * @return item's expiration date
     */
    public Date getExpiration();
    
    /**
     * Gets position of the item in warehouse topology.
     * @return item's position
     */
    public Position getPosition();
}
