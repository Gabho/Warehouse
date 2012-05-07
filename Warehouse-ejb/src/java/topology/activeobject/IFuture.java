/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

/**
 *
 * @author Gabo
 */
public interface IFuture<T> {
        
        T get();

        boolean isDone();
    
}
