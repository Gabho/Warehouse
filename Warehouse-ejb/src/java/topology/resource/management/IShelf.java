package topology.resource.management;

import java.util.List;

/**
 * Interface defining basic storage funcionality.
 * @author Martin Lofaj
 */
public interface IShelf extends ICoordinable{
    
    //Dohodnuty interface
    /**
     * Returns a list of items stored in the shelf.
     * @return items stored in the shelf.
     */
    public List<IItem> getItems();
    
    /**
     * Return value of a member variable capacity.
     * @return capacity of the shelf
     */
    public int getCapacity();
    
    //item sa bude odovdavat alebo typ itemu?
    
    /**
     * Return exact amount of items specfied in the parameter.
     * @param type of the desired item
     * @return amount of specified items
     */
    public int getItemCount(String type);
    
    /**
     * Computes free space in the shelf.
     * @return free space in the shelf
     */
    public int getFreeSpace();
    
    /**
     * Return id of acctual shelf.
     * @return shelf id.
     */
    public int getID();
    
    /**
     * Insert item into shelf if the amout of inserted item is less or
     * equal than free space in the shelf.
     * @param item item to store
     */
    public void insertItem(IItem item);
    
    /**
     * 
     * @param item item to be removed.
     * @return removed item or null if specified item is not stored
     */
    public IItem removeItem(IItem item);
    
    /**
     * Destroys entire shelf by easing all items stored in databaase.
     * @return list of all stored items
     */
    public List<IItem> remove();
    
}
