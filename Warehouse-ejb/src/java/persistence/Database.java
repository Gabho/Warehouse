/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.List;
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

    @Override
    int synchronizedSearch(String searchString) {
        MasterDataEntity search = em.find(MasterDataEntity.class, searchString);
        return search.getQuantity();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
