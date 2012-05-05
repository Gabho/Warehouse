/*
 * Rack implementation.
 * 
 */
package topology.storage;

import java.util.ArrayList;
import java.util.List;
import topology.configuration.AbstractComponent;
import topology.resource.management.Shelf;

/**
 *
 * @author Mao
 */
public class Rack extends AbstractComponent {
    private List<Shelf> shelfs; 

    public Rack() {
        shelfs = new ArrayList();
    }
    
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
