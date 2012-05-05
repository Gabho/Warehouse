/*
 * Aisle implementation.
 * 
 */
package topology.storage;

import java.util.ArrayList;
import java.util.List;
import topology.configuration.AbstractComponent;

/**
 *
 * @author Martin Pakandl
 */

public class Aisle extends AbstractComponent {
    private int code;
    private int capacity;
    private List<Rack> racks;
    
    public Aisle(int code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        racks = new ArrayList();
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
