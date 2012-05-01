/*
 * Item functionality
 */
package topology.resource.management;

import java.util.Date;

/**
 *
 * @author Martin Lofaj
 */
public interface IItem {
    public int getAmount();
    public String getType();
    public String getDescription();
    public Date getExpiration();
    public Position getPosition();
}
