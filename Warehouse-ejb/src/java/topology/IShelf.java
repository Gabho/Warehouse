package topology;

/**
 *
 * @author Martin Lofaj
 */
public interface IShelf {
    
    //Dohodnuty interface
    public Item[] getItems();
    public int getCapacity();
    public int getItemCount();
    public int getFreeSpace();
    public int getID();
    
    //Pravdepodobne potrebne metody
    public void insertItem(Item item);
    //public void removeItem(Item item); //vymazanie daneho itemu ?
    //public void findItem(int id); // ziskanie itemu podla id ? dohodli sme sa ze id je string ?
    
    
}
