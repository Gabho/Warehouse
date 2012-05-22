package communication;

import java.util.List;
import javax.ejb.Local;

/**
 * Lokálne rozhranie poskytujúce metódy, ktoré su volané lokálne.
 * @author Gabo
 */
@Local
public interface CommunicationLocal {
    
    /**
     * Vyhľadávanie položiek na inej bežiacej inštancii skladu na základe vyhľadávacieho reťazca.
     * @param search je vyhľadávací reťazec.
     * @return zoznam reťazcov, ktoré predstavujú výsledok vyhľadávania.
     */
    public List<String> searchRemote(String search);
    
}
