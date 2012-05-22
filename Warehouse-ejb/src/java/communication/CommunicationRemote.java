package communication;

import java.util.List;
import javax.ejb.Remote;

/**
 * Vzdialené rozhranie poskytujúce metódy, ktoré sú volané z iných inštancií bežiacich v iných adresných priestoroch.
 * @author Gabo
 */
@Remote
public interface CommunicationRemote {
    
    /**
     * Vyhľadávanie na lokálnom sklade, pričom funkcia bola zavolaná zo vzdialeného skladu.
     * @param search vyhľadávací reťazec.
     * @return zoznam reťazcov, ktoré predstavujú výsledok vyhľadávania.
     */
    public List<String> searchRemoteI(String search);
    
}
