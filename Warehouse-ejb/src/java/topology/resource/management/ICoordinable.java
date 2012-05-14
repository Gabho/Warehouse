package topology.resource.management;

/**
 * Part of the Task coordinator pattern
 * @author Martin Lofaj
 */
public interface ICoordinable {
    
    /** Prepares all necessary stuff
     *  @throws TaskFailureException if the task fails.
     */
    public void prepare(IItem item) throws TaskFailureException;
   
    /** Commits prepared results */
    public void commit();
    
    /** Aborts */
    public void abort();
}
