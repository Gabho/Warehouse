package persistence;

/**
 * Trieda reprezentujúca zámok monitor objektu
 * @author Gabriel Cervenak
 */
public class MonitorLock {
    
    volatile boolean locked = false;
    Thread thread = null;
    
    /**
     * Získanie zámku
     */
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
    
    /**
     * Uvoľnenie zámku
     */
    public synchronized void unlock(){
        if(locked && Thread.currentThread() == this.thread){
            thread = null;
            locked = false;
            notify();
        }
    }
    
}
