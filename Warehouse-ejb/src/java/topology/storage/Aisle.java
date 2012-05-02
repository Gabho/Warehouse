/*
 * Aisle implementation.
 * 
 */
package topology.storage;

import topology.configuration.AbstractComponent;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Martin Pakandl
 */
@Stateless
@LocalBean
public class Aisle extends AbstractComponent {

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void suspend() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String info() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
