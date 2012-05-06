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
    private Boolean isReady;
    
    public Aisle(int code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        racks = new ArrayList();
        isReady = false;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCode() {
        return code;
    }

    public List<Rack> getRacks() throws Exception {
        if (isReady) {
            return racks;
        } else {
            throw new Exception("Aisle is not inicialized!");
        }        
    }
    
    @Override
    public void init() {
        isReady = true;
    }

    @Override
    public void suspend() {
        isReady = false;
    }

    @Override
    public void resume() {
        isReady = true;
    }

    @Override
    public String info() {
        return "This is an Aisle for Racks.\nCode: " + code + "\n" + "Capacity: " + capacity;
    }

}
