package topology.activeobject;

/**
 * Trieda reprezentujúca odpoveď, ide o generickú triedu, nakoľko odpoveď môže byť rôzneho typu.
 * Implementácia návrhového vzoru Future, ktorý poskytne odpoveď hneď po vyvolaní metódy, aj keď
 * hodnota odpovede ešte nie je dostupná.
 * @author Gabriel Cervenak
 */
public class Future<T> implements IFuture<T> {

    private T result;

    /**
     * Vloženie hodnoty do odpovede.
     * @param result hodnota odpovede.
     */
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

    /**
     * Čakanie vlákna, pokým odpoveď nebude dostupná.
     */
    private void waitForResult() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
