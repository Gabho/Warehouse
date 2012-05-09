/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabo
 */
public class Scheduler {

    private static final Logger LOGGER = Logger.getLogger(Scheduler.class.getName());
    private ActivationQueue activationQueue = new ActivationQueue();

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
    
    private void startThread(Thread thread){
        thread.start();
    }

    public void enqueue(IMethodRequest methodRequest) {
        activationQueue.enqueue(methodRequest);
        if(methodRequest != null)
            LOGGER.log(Level.INFO, "..............................Scheduler:Request zaradeny do radu....................");
    }
}
