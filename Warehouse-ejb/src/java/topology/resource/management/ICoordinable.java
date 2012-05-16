package topology.resource.management;

/**
 * Časť návrhového vzoru Task Coordinator.
 * Predstavuje účastníkov, ktorí sa podieľajú na koordinovanej úlohe.
 * Toto rozhranie je volané koordinátorom.
 * @author Martin Lofaj
 */
public interface ICoordinable {
    
    /** 
     * Príprava pre vykonanie danej úlohy.
     * V prípade, že počas prípravy vznikne chyba vyhodí výnimku.
     * @throws TaskFailureException v prípade, že pri príprave vyznikne chyba.
     */
    public void prepare(IItem item) throws TaskFailureException;
   
    /** Vykoná zmeny, ktoré sa vykonali počas prípravy. */
    public void commit();
    
    /** Zruší zmeny, ktoré sa vykonali počas prípravy. */
    public void abort();
}
