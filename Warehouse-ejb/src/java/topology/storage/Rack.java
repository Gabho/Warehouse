package topology.storage;

import java.util.ArrayList;
import java.util.List;
import topology.configuration.AbstractComponent;
import topology.resource.management.IShelf;
import topology.resource.management.ProxyShelf;

/**
 * Regál pre topológiu skladu.
 *
 * @author Martin Pakandl
 */
public class Rack extends AbstractComponent implements IStorageComponent {
    //identifikátor uličky

    private String code;
    //kapacita uličky
    private int capacity;
    //zoznam poličiek v regále
    private List<IShelf> shelfs;
    //status objektu uličky
    private Boolean isReady;

    /**
     * Bezparametrický konštruktor uličky. Pre potreby dynamického načítania
     * triedy.
     */
    public Rack() {
    }

     /**
     * Konštruktor regálu.
     * @param code identifikátor regálu
     * @param capacity kapacita regálu
     */
    public Rack(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        shelfs = new ArrayList();
        isReady = false;
    }
 /**
     * Získanie informície o kapacite regálu.
     * @return kapacitu regálu
     */
    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getCode() {
        return code;
    }
 /**
     * Získanie informície o poličkách v regále.
     * @return zoznam poličiek
     * @throws Exception 
     */
    public List<IShelf> getShelfs() throws Exception {
        if (isReady) {
            return shelfs;
        } else {
            throw new Exception("Rack is not inicialized!");
        }
    }

    @Override
    public void init(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        shelfs = new ArrayList();
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

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int addComponent(Object object) {
        if (shelfs.size() < capacity) {
            shelfs.add((ProxyShelf) object);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int removeComponent(Rack rack) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
