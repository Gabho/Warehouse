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

    private int code;
    private int capacity;
    private List<Shelf> shelfs;
    private Boolean isReady;

    public Rack(int code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        shelfs = new ArrayList();
        isReady = false;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCode() {
        return code;
    }

    public List<Shelf> getShelfs() throws Exception {
        if (isReady) {
            return shelfs;
        } else {
            throw new Exception("Rack is not inicialized!");
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
        return "This is a Rack for Shelfs.\nCode: " + code + "\n" + "Capacity: " + capacity;
    }
}
