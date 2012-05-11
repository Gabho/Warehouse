/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import persistence.Database;
import persistence.MasterDataEntity;

/**
 *
 * @author Gabo
 */
public class MethodRequestInsertMD implements IMethodRequest {

    private Database database;
    private MasterDataEntity masterData;

    public MethodRequestInsertMD(MasterDataEntity masterData, Database database) {
        this.masterData = masterData;
        this.database = database;
    }

    @Override
    public void call() {
        Thread thread = new Thread(){
            @Override
            public void run(){
                database.addMasterData(masterData);
            }
        };
        thread.start();
    }
}
