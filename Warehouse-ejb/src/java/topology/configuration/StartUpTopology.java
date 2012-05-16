/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.configuration;

import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/** 
 * Spustenie konfigurácie systému pri jeho štarte.
 * @author Martin Pakandl 
 */
@Startup
@Singleton
public class StartUpTopology {
    //sprístupnenie EJB Komponent Konfigurátora
    @EJB ComponentConfigurator cc;
    //zaznamenávanie pre server
    static final Logger LOGGER = Logger.getLogger(StartUpTopology.class.getName());
    /**
     * Spustenie konfigurácie systému pri jeho inicializácií.
     */
    @PostConstruct 
    public void onInit() {
        //cc = new ComponentConfigurator();
        LOGGER.info("################################RUNNING################################"); 
        cc.configure();

        LOGGER.info("################################RUNNING OK #############################"); 
    }
    /**
     * Správa o ukončení systému.
     */
    @PreDestroy
    public void preDestroy() {
        LOGGER.info("################################DESTROING################################"); 
    }
}
