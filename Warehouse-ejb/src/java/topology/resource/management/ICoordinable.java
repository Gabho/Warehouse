package topology.resource.management;

/**
 * Part of the Task coordinator pattern
 * @author Martin Lofaj
 */
public interface ICoordinable {
    
    /**
     * Prepares all necessary 
     */
    public void prepare();
    public void commit();
    public void abort();
}
