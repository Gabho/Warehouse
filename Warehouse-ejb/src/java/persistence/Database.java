/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Gabo
 */
@Stateless
@LocalBean
public class Database extends DatabaseMonitorObject{
    
    @PersistenceContext private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    @Override
    int synchronizedSearch(String searchString) {
        int quantity;
        MasterDataEntity search = em.find(MasterDataEntity.class, searchString);
        quantity = search.getQuantity();
        LOGGER.log(Level.INFO, "..............................Searched string {0}..............................", searchString);
        LOGGER.log(Level.INFO, "..............................Searched quantity {0}..............................", Integer.toString(quantity));
        
        return quantity;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
