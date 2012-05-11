package persistence;

/**
 * 
 * @author Gabriel Cervenak
 */
//Trieda predstavujúca zámok pre monitor objekt
public class MonitorLock {
    
    volatile boolean locked = false;
    Thread thread = null;
    
    //Metóda na získanie zámku
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
    
    //Uvoľnenie zámku
    public synchronized void unlock(){
        if(locked && Thread.currentThread() == this.thread){
            thread = null;
            locked = false;
            notify();
        }
    }
    
}
