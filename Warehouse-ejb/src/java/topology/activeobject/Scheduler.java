package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Plánovač, ktorý zabezpečuje vyberanie požiadaviek z radu požiadaviek a ich následné vykonanie.
 * @author Gabriel Cervenak
 */
public class Scheduler {

    private static final Logger LOGGER = Logger.getLogger(Scheduler.class.getName());
    private ActivationQueue activationQueue = new ActivationQueue();

    /**
     * Vytvorenie nového polánovača a jeho spustenie v samostatnom vlákne.
     */
    public Scheduler() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                while (true) {
                    IMethodRequest request = activationQueue.dequeue();
                    if(request != null){
                        LOGGER.log(Level.INFO, "..............................Scheduler:Request najdeny....................");
                        request.call();
                    }
                    else
                        LOGGER.log(Level.INFO, "..............................Scheduler:Request nenajdeny....................");
                }
            }
        };
        startThread(thread);
    }
    
    /**
     * Spustenie plánovača v novom vlákne.
     * @param thread vlákno, v ktorom má byť plánovač spustený.
     */
    private void startThread(Thread thread){
        thread.start();
    }

    /**
     * Vloženie požiadavky do radu požiadaviek.
     * @param methodRequest požiadavka, ktorá má byť vložená.
     */
    public void enqueue(IMethodRequest methodRequest) {
        activationQueue.enqueue(methodRequest);
        if(methodRequest != null)
            LOGGER.log(Level.INFO, "..............................Scheduler:Request zaradeny do radu....................");
    }
}
