/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.HEAD;
import topology.resource.management.Item;
import topology.resource.management.Position;

/**
 *
 * @author Gabriel Cervenak
 */
//Java Bean zabezpečujúia prácu s databázou
@Stateless
@LocalBean
public class Database extends DatabaseMonitorObject {

    @PersistenceContext
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    //Metóda na vyhľadávanie
    @Override
    int synchronizedSearch(String searchString) {
        int quantity;
        searchString = searchString.toUpperCase();
        MasterDataEntity search = em.find(MasterDataEntity.class, searchString);
        if (search == null) {
            LOGGER.log(Level.INFO, "..............................Searched not found..............................");
            return 0;
        } else {
            quantity = search.getQuantity();
            LOGGER.log(Level.INFO, "..............................Searched string {0}..............................", searchString);
            LOGGER.log(Level.INFO, "..............................Searched quantity {0}..............................", Integer.toString(quantity));

            return quantity;
        }

    }

    //Pridávanie master dat do tabuľky
    @Override
    void synchronizedAddMasterData(MasterDataEntity masterData) {
        try{
            em.persist(masterData);
        }catch(Exception e){
        }
    }

    //Mazanie master dat z tabuľky
    @Override
    void synchronizedRemoveMasterData(String id) {
        MasterDataEntity masterData = em.find(MasterDataEntity.class, id);
        em.remove(masterData);
    }

    //Získanie zoznamu vsetkých master dát nachádzajúcich sa v tabuľke
    @Override
    List<MasterDataEntity> synchronizedGetMasterData() {
        TypedQuery<MasterDataEntity> getData = em.createQuery("SELECT m FROM MasterDataEntity m ORDER BY m.id", MasterDataEntity.class);
        List<MasterDataEntity> data = getData.getResultList();
        return data;
    }

    
    


    //Vráti obsah poličky na danej pozícii
    @Override
    List<Item> synchronizedGetShelf(Position position) {
        TypedQuery<ItemEntity> getItems = em.createQuery("SELECT i FROM ItemEntity i WHERE i.aisle =",ItemEntity.class);
        return null;
    }


}
