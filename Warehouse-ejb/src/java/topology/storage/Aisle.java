/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

import topology.configuration.AbstractComponent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mao
 */
public class Aisle extends AbstractComponent {
     private String code;
    private int capacity;
    private List<Rack> racks;
    private Boolean isReady;
   
    public Aisle() {
    }
    
    public Aisle(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        racks = new ArrayList();
        isReady = false;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getCode() {
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
    public void init(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        racks = new ArrayList();
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
