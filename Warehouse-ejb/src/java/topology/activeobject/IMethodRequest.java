package topology.activeobject;

/**
 * Rozhranie definujúce požiadavku na metódu.
 * @author Gabriel Cervenak
 */
public interface IMethodRequest {
    
    /**
     * Zavolanie obsluhy pre vykonanie požadovanej operácie.
     */
    public void call();
    
}
