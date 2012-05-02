/*
 * Abstract Component for Component Configurator pattern. Configurable component must implemet 
 * Configurable component must implemet this methods.
 */
package topology.configuration;

/**
 *
 * @author Martin Pakandl
 */
public abstract class AbstractComponent {
    
    public abstract void init();
    public abstract void suspend();
    public abstract void resume();
    public abstract String info();
    
}
