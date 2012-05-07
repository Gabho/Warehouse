/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

/**
 *
 * @author Gabo
 */
public class Scheduler {

    private ActivationQueue<Runnable> activationQueue = new ActivationQueue<Runnable>();

    public Scheduler() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                while (true) {
                    activationQueue.dequeue().run();
                }
            }
        };
        startThread(thread);
    }
    
    private void startThread(Thread thread){
        thread.start();
    }

    public void enqueue(Runnable runnable) {
        activationQueue.enqueue(runnable);
    }
}
