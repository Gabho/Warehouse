package topology.activeobject;

/**
 * Rozhranie definijúce správanie objektu Future.
 * @author Gabriel Cervenak
 */
public interface IFuture<T> {

    /**
     * Získanie hodnoty odpovede z obejktu Future, ide o blokujúce čítanie.
     * @return hodnotu odpovede.
     */
    T get();

    /**
     * Neblokujúce overenie, či je odpoveď dostupná.
     * @return true, ak je dostupná, false, ak nie je.
     */
    boolean isDone();
}
