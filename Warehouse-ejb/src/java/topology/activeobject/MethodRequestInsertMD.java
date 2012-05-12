/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.Database;
import persistence.MasterDataEntity;

/**
 *
 * @author Gabo
 */
public class MethodRequestInsertMD implements IMethodRequest {

    private Database database;
    private MasterDataEntity masterData;
    private static final Logger LOGGER = Logger.getLogger(MethodRequestInsertMD.class.getName());

    public MethodRequestInsertMD(MasterDataEntity masterData, Database database) {
        this.masterData = masterData;
        this.database = database;
    }

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
