package persistence;

import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Cervenak
 */
//Trieda predstavujúca podmienku monitor objektu
public class MonitorCondition {
    
    private MonitorLock lock;
    private Deque<Long> threadIDs;
    private volatile boolean notify = true;
    
    public MonitorCondition(MonitorLock lock) {
        this.lock = lock;
    }
        
    //Čakanie vlákna, pokiaľ nebude splnená podmienka
    void goWait(long id) {
        lock.unlock();
        synchronized (this) {
            threadIDs.addLast(Thread.currentThread().getId());
            while (threadIDs.contains(Thread.currentThread().getId())) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MonitorCondition.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        lock.lock();
    }
    
    //Metóda na upovedomenie vlákna o splnení podmienky
    void goNotify() {
        threadIDs.removeFirst();
    }
}
