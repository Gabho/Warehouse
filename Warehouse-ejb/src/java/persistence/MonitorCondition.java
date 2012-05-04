/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

/**
 *
 * @author Gabo
 */
public class MonitorCondition {
    
    private MonitorLock lock;
    
    public MonitorCondition(MonitorLock lock){
        this.lock = lock;
    }
    
}
