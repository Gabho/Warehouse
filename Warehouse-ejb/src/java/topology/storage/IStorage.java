/*
 * Interface with business method.
 * 
 */
package topology.storage;

import javax.ejb.Local;

/**
 *
 * @author Martin Pakandl
 */
@Local
public interface IStorage {
    public int addItem();
    public int removeItem();
    public int findItem();
    public int getFreeSpace();
}
