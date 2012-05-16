package topology.activeobject;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Trieda reprezentujúca rad požiadaviek, ktoré sa majú vykonať.
 * @author Gabriel Cervenak
 */
public class ActivationQueue {

    private Deque<IMethodRequest> queue = new ArrayDeque<IMethodRequest>();

    /**
     * Vloženie požiadavky do radu požiadaviek na jeho koniec.
     * @param request požiadavka, ktorá má byť vložená.
     */
    public synchronized void enqueue(IMethodRequest request) {
        queue.addLast(request);
        notifyAll();
    }

    /**
     * Odobratie požiadavky z radu požiadaviek z prvej pozície v rade.
     * @return požiadavka, ktorá je vrátená.
     */
    public synchronized IMethodRequest dequeue() {
        while (queue.isEmpty()) {
            waitForNotification();
        }
        return queue.removeFirst();
    }

    /**
     * Pozastavenie vlákna, kým je rad požiadaviek prázdny.
     * Akonáhle sa do radu vložia nejaké požiadavky, vlákno pokračuje vo vykonávaní.
     */
    private void waitForNotification() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
