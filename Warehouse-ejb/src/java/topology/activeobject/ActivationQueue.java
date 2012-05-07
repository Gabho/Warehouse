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
public class ActivationQueue<T> {
    private Deque<T> queue = new ArrayDeque<T>();
    
    public synchronized void enqueue(T command) {
		queue.addLast(command);
		notifyAll();
	}
	
	public synchronized T dequeue() {
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
