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
    public void insertItem(IItem item);
    public IItem removeItem(IItem item);
    
    public List<IItem> remove();
    //public void findItem(int id); // ziskanie itemu podla id ?
    
}
