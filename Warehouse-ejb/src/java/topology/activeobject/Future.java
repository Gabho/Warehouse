/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

/**
 *
 * @author Gabo
 */
public class Future<T> implements IFuture<T> {

    private T result;

    public synchronized void addResult(T result) {
        this.result = result;
        notifyAll();
    }

    @Override
    public synchronized T get() {
        while (result == null) {
            waitForResult();
        }
        return result;
    }

    @Override
    public boolean isDone() {
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    private void waitForResult() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
