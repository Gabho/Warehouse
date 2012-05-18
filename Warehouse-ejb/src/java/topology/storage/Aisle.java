package topology.storage;

import java.util.ArrayList;
import java.util.List;
import topology.configuration.AbstractComponent;

/**
 * Ulička v topológií skladu.
 * @author Martin Pakandl
 */
public class Aisle extends AbstractComponent implements IStorageComponent {
    //identifikátor uličky
    private String code;
    //kapacita uličky
    private int capacity;
    //zoznam regálov v uličke
    private List<Rack> racks;
    //status objektu uličky
    private Boolean isReady;
    
    /**
     * Bezparametrický konštruktor uličky. Pre potreby dynamického načítania triedy.
     */
    public Aisle() {
    }
    
    /**
     * Konštruktor uličky.
     * @param code identifikátor uličky
     * @param capacity kapacita uličky
     */
    public Aisle(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        racks = new ArrayList();
        isReady = false;
    }
    
    /**
     * Získanie informície o kapacite uličky.
     * @return kapacitu uličky
     */
    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getCode() {
        return code;
    }
    
    /**
     * Získanie informície o regáloch v uličke.
     * @return zoznam regálov
     * @throws Exception 
     */
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

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean addComponent(Object object) {
       if(racks.size() < capacity) {
            racks.add((Rack)object);  
            return true;
        } else return false;         
    }

    @Override
    public boolean removeComponent(Rack rack) {
        return racks.remove(rack);
    }
}
