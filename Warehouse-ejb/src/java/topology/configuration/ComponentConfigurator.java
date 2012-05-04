/*
 * Component Configurator pattern
 * 
 */
package topology.configuration;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import topology.storage.Storage;

/**
 *
 * @author Martin Pakandl
 */
@Stateless
@LocalBean
public class ComponentConfigurator {
    Storage storage;

    // Initialize the component configurator.
    public ComponentConfigurator() {
        storage = new Storage();
        
    }
    
    // Configure components from script at start of system.
    public void configure(String task) {
        
    }
    
    // Configure component at runtime from admin command.
    public void processTask(String task) {
        
    }
    
    // Get Object Manager repositary. 
    public Storage getStorage() {
        return storage;
    } 
}
