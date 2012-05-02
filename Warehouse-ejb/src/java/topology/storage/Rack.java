/*
 * Rack implementation.
 * 
 */
package topology.storage;

import topology.configuration.AbstractComponent;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Mao
 */
@Stateless
@LocalBean
public class Rack extends AbstractComponent {

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
