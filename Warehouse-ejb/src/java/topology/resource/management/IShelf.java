package topology.resource.management;

import java.util.List;

/**
 *
 * @author Martin Lofaj
 */
public interface IShelf {
    
    //Dohodnuty interface
    public List<IItem> getItems();
    public int getCapacity();
    
    //item sa bude odovdavat alebo typ itemu?
    public int getItemCount(String type);
    public int getFreeSpace();
    public int getID();
    
    //Pravdepodobne potrebne metody
    public void insertItem(Item item);
    public void removeItem(Item item);
    //public void findItem(int id); // ziskanie itemu podla id ?
    
    
}
