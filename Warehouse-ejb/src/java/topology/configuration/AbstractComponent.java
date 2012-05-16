package topology.configuration;

/**
 * Abstrakný komponent deklarujúci metódy pre riadenie životného cyklu konkrétnych komponentov.
 * @author Martin Pakandl
 */
public abstract class AbstractComponent {
    /**
     * Inicializácia, bez ktorej nie je sú prístupné služby objektu.
     * @param code kód identifikujúci komponent
     * @param capacity kapacita komponentu pre iné komponenty
     */
    public abstract void init(String code, int capacity);
    /**
     * Pozastavenie činnosti komponentu.
     */
    public abstract void suspend();
    /**
     * Sprístupnenie komponentu po suspendovaní. 
     */
    public abstract void resume();
    /**
     * Informácie o komponente.
     * @return kód a kapacitu komponentu
     */
    public abstract String info();
    /**
     * Zistenie kódu identifikujúci komponent.
     * @return kód komponentu
     */
    public abstract String getCode();
}
  