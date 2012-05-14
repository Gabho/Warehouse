/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.ArrayList;
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
    List<MasterDataEntity> synchronizedSearch(String searchString) {
        int quantity;
        searchString = searchString.toUpperCase();
        
        TypedQuery<MasterDataEntity> getMaster = em.createQuery("SELECT m FROM MasterDataEntity m WHERE m.id LIKE '%"+searchString+"%'", MasterDataEntity.class);
        List<MasterDataEntity>  masters = getMaster.getResultList();

        return masters;
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
        List<IItem> returnItems = new ArrayList<IItem>();
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
        try{
            synchronizedRemoveShelf(shelfId);
        }catch(Exception e){
            LOGGER.log(Level.INFO,"..............................Database: Shelf doesnt exist....................");
        }
        for(IItem item : items){
            MasterDataEntity masterData = em.find(MasterDataEntity.class, item.getType());
            ItemEntity i = new ItemEntity(item.getPosition().getAisle(), item.getPosition().getRack(), item.getPosition().getShelf(), item.getAmount(), item.getExpiration(), masterData);
            em.persist(i);
            em.flush();
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
