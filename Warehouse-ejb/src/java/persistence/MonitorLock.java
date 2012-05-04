/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

/**
 *
 * @author Gabo
 */
public class MonitorLock {
    
    volatile boolean locked = false;
    Thread thread = null;
    
    public synchronized void lock(){
        while(locked){
            try{
                if(this.thread != Thread.currentThread())
                    wait();
                else
                    break;
            }catch(Exception e){}
        }
        
        this.thread = Thread.currentThread();
        locked = true;
    }
    
    public synchronized void unlock(){
        if(locked && Thread.currentThread() == this.thread){
            thread = null;
            locked = false;
            notify();
        }
    }
    
}
