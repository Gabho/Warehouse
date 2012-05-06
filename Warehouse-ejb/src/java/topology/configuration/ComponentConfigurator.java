/*
 * Component Configurator pattern
 * 
 */
package topology.configuration;

import javax.ejb.EJB;
import topology.storage.Storage;

/**
 *
 * @author Martin Pakandl
 */
public class ComponentConfigurator {
    
    @EJB Storage storage;
    // Initialize the component configurator.
    public ComponentConfigurator() { 
 
    }
    
    // Configure components from script at start of system.
    public void configure(String task) {
         
    }
    
    // Configure component at runtime from admin command.
    public void processTask(String task) {
        
    }
}
