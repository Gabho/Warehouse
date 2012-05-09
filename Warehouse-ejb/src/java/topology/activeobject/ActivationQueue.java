/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author Gabo
 */
public class ActivationQueue {
    private Deque<IMethodRequest> queue = new ArrayDeque<IMethodRequest>();
    
    public synchronized void enqueue(IMethodRequest request) {
		queue.addLast(request);
		notifyAll();
	}
	
	public synchronized IMethodRequest dequeue() {
		while (queue.isEmpty()) {
			waitForNotification();
		}
		return queue.removeFirst();
	}

	private void waitForNotification() {
		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
