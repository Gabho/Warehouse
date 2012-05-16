package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.Database;
import persistence.MasterDataEntity;

/**
 * Trieda reprezentujúca požiadavku na vykonanie operácie vloženia master dát.
 * @author Gabriel Cervenak
 */
public class MethodRequestInsertMD implements IMethodRequest {

    private Database database;
    private MasterDataEntity masterData;
    private static final Logger LOGGER = Logger.getLogger(MethodRequestInsertMD.class.getName());

    /**
     * Vytvorenie novej požiadavky na vloženie master dát.
     * @param masterData master dáta, ktoré majú byť vložené.
     * @param database EJB poskytujúci prístup k databáze.
     */
    public MethodRequestInsertMD(MasterDataEntity masterData, Database database) {
        this.masterData = masterData;
        this.database = database;
    }

    /**
     * Vytvorenie nového vlákna, v ktorom prebehne pridávanie master dát do databázy.
     */
    @Override
    public void call() {
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                database.addMasterData(masterData);
                }catch(Exception e){
                LOGGER.log(Level.INFO, "..............................Database Exception while inserting MD....................");
                }
            }
        };
        thread.start();
    }
}
