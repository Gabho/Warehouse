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
 *
 * @author Mao 
 */
@Startup
@Singleton
public class StartUpTopology {
    @EJB ComponentConfigurator cc;
    //ComponentConfigurator cc;
    static final Logger LOGGER = Logger.getLogger(StartUpTopology.class.getName());
    @PostConstruct 
    public void onInit() {
        //cc = new ComponentConfigurator();
        LOGGER.info("################################RUNNING################################"); 
        cc.configure();

        LOGGER.info("################################RUNNING ok ################################"); 
    }
    @PreDestroy
    public void preDestroy() {
        LOGGER.info("################################DESTROING################################"); 
    }
}
