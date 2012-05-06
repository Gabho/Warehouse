/*
 * Interface for Component Configurator
 */
package topology.storage;

import javax.ejb.Local;

/**
 *
 * @author Martin Pakandl
 */
@Local
public interface IObjectManager {
    public void insert(Object object);
    public void remove(Object object);
    public Object find(Object object);
}
