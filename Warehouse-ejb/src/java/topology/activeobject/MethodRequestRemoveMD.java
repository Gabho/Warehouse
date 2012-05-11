/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.activeobject;

import persistence.Database;

/**
 *
 * @author Gabo
 */
public class MethodRequestRemoveMD implements IMethodRequest {
    
    private Database database;
    private String id;
    
    public MethodRequestRemoveMD(String id, Database database){
        this.id = id;
        this.database = database;
    }

    @Override
    public void call() {
        Thread thread = new Thread(){
            @Override
            public void run(){
                database.removeMasterData(id);
            }
        };
        thread.start();
    }
    
}
