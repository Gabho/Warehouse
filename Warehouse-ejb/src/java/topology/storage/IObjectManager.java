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
    public void delete();
    public void insert();
    public void remove();
    public void find();
}
