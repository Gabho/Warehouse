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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import topology.resource.management.IItem;
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
            em.persist(masterData);
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
    List<IItem> synchronizedGetShelf(int shelfId) {
        TypedQuery<ItemEntity> getItems = em.createQuery("SELECT i FROM ItemEntity i WHERE i.shelf=" + shelfId + "", ItemEntity.class);
        List<ItemEntity> items = getItems.getResultList();
        List<IItem> returnItems = null;
        for (ItemEntity item : items) {
            Position pos = new Position(item.getShelf(), item.getRack(), item.getAisle());
            IItem i = new Item(item.getId(), item.getQuantity(), item.getMasterData().getId(), item.getMasterData().getDescription(), item.getExpDate(), pos);
            returnItems.add(i);
        }
        return returnItems;
    }

    //Odstráni celú poličku z databázy
    @Override
    void synchronizedRemoveShelf(int shelfId) {
        TypedQuery<ItemEntity> getItems = em.createQuery("SELECT i FROM ItemEntity i WHERE i.shelf=" + shelfId + "", ItemEntity.class);
        List<ItemEntity> items = getItems.getResultList();
        for(ItemEntity item : items){
            em.remove(item);
            updateQuantity(item.getMasterData());
        }
    }

    @Override
    void synchronizedUpdateShelf(List<IItem> items, int shelfId) {
        synchronizedRemoveShelf(shelfId);
        for(IItem item : items){
            MasterDataEntity masterData = em.find(MasterDataEntity.class, item.getType());
            ItemEntity i = new ItemEntity(item.getPosition().getAisle(), item.getPosition().getRack(), item.getPosition().getShelf(), item.getAmount(), item.getExpiration(), masterData);
            em.persist(i);
            updateQuantity(i.getMasterData());
        }
    }
    
    void updateQuantity(MasterDataEntity masterData){
        int quantity = 0;
        for(ItemEntity item : masterData.getItemEntitys()){
                quantity = quantity + item.getQuantity();
            }
        Query update = em.createQuery("UPDATE MasterDataEntity m SET m.quantity = "+quantity+" WHERE m.id='"+masterData.getId()+"'", MasterDataEntity.class);
        int execute = update.executeUpdate();
    }

}
