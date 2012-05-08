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

    private ActivationQueue<IMethodRequest> activationQueue = new ActivationQueue<IMethodRequest>();

    public Scheduler() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                while (true) {
                    activationQueue.dequeue().call();
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
    }
}
