package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import topology.resource.management.IItem;
import topology.resource.management.Item;
import topology.resource.management.Position;

/**
 * Enterprise Java Bean implementujúci operácie, ktoré môžeme vykonať nad
 * databázou
 *
 * @author Gabriel Cervenak
 */
@Stateless
@LocalBean
public class Database extends DatabaseMonitorObject {

    @PersistenceContext
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    @Override
    List<MasterDataEntity> synchronizedSearch(String searchString) {
        int quantity;
        searchString = searchString.toUpperCase();

        TypedQuery<MasterDataEntity> getMaster = em.createQuery("SELECT m FROM MasterDataEntity m WHERE m.id LIKE '%" + searchString + "%'", MasterDataEntity.class);
        List<MasterDataEntity> masters = getMaster.getResultList();

        return masters;
    }

    @Override
    void synchronizedAddMasterData(MasterDataEntity masterData) {
        em.setFlushMode(FlushModeType.COMMIT);
        em.persist(masterData);
        em.flush();
    }

    @Override
    void synchronizedRemoveMasterData(String id) {
        em.setFlushMode(FlushModeType.COMMIT);
        MasterDataEntity masterData = em.find(MasterDataEntity.class, id);
        em.remove(masterData);
        em.flush();
    }

    @Override
    List<MasterDataEntity> synchronizedGetMasterData() {
        TypedQuery<MasterDataEntity> getData = em.createQuery("SELECT m FROM MasterDataEntity m ORDER BY m.id", MasterDataEntity.class);
        List<MasterDataEntity> data = getData.getResultList();
        return data;
    }

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

    @Override
    void synchronizedRemoveShelf(int shelfId) {
        em.setFlushMode(FlushModeType.COMMIT);
        TypedQuery<ItemEntity> getItems = em.createQuery("SELECT i FROM ItemEntity i WHERE i.shelf=" + shelfId + "", ItemEntity.class);
        List<ItemEntity> items = getItems.getResultList();
        for (ItemEntity item : items) {
            em.remove(item);
            em.flush();
            updateQuantity(item.getMasterData());
        }
    }

    @Override
    void synchronizedUpdateShelf(List<IItem> items, int shelfId) {
        em.setFlushMode(FlushModeType.COMMIT);
        try {
            synchronizedRemoveShelf(shelfId);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "..............................Database: Shelf doesnt exist....................");
        }
        for (IItem item : items) {
            MasterDataEntity masterData = em.find(MasterDataEntity.class, item.getType());
            ItemEntity i = new ItemEntity(item.getPosition().getAisle(), item.getPosition().getRack(), item.getPosition().getShelf(), item.getAmount(), item.getExpiration(), masterData);
            em.persist(i);
            em.flush();
            updateQuantity(i.getMasterData());
        }
    }

    void updateQuantity(MasterDataEntity masterData) {
        int quantity = 0;
        em.refresh(masterData);
        for (ItemEntity item : masterData.getItemEntitys()) {
            quantity = quantity + item.getQuantity();
        }
        System.out.println("............................Database: quantity to update: " + quantity);
        Query update = em.createQuery("UPDATE MasterDataEntity m SET m.quantity = " + quantity + " WHERE m.id='" + masterData.getId() + "'", MasterDataEntity.class);
        int execute = update.executeUpdate();
    }
}
