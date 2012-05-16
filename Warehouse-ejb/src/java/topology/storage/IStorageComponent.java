package topology.storage;

/**
 *
 * @author Martin Pakandl
 */
public interface IStorageComponent {

    /**
     * Nastavenie identifikátora komponentu.
     * @param code identifikátor
     */
    public void setCode(String code);

    /**
     * Nastavenie kapacity komponentu.
     * @param capacity kapacita
     */
    public void setCapacity(int capacity);

    /**
     * Pridanie komponentu.
     * @param object komponent
     * @return stav vykonania
     */
    public int addComponent(Object object);

    /**
     * Odstránenie regálu z uličky.
     * @param rack regál
     * @return stav vykonania
     */
    public int removeComponent(Rack rack);
}
