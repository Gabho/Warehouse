package persistence;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trieda reprezentuje podmienku monitor objektu
 * @author Gabriel Cervenak
 */
public class MonitorCondition {
    
    private MonitorLock lock;
    private Deque<Long> threadIDs;
    private volatile boolean notify = true;
    
    /**
     * Konštruktor s jedným parametrom, ktorý vytvorí inštanciu podmienky a nastaví zámok
     * @param lock zámok monitor objektu
     */
    public MonitorCondition(MonitorLock lock) {
        this.lock = lock;
        this.threadIDs = new ArrayDeque<Long>();
    }
        
    /**
     * Vlákno uvoľní zámok a čaká, pokiaľ nebude môcť pokračovať vo vykonávaní
     * Pred pokračovaním potrebuje opätovne získať zámok
     */
    void goWait() {
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
    
    /**
     * Obnovenie čakajúceho vlákna
     */
    void goNotify() {
        threadIDs.removeFirst();
    }
}
