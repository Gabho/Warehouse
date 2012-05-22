package topology.activeobject;

import javax.ejb.Local;

/**
 * Lokálne rozhranie Proxy
 * @author Gabo
 */
@Local
public interface IProxyLocal {
    
    /**
     * Vracia identifikátor skladu.
     * @return reťazec predstavujúci ID skladu.
     */
    public String getWarehouseID();
    
}
