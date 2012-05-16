package topology.activeobject;

import persistence.Database;

/**
 * Trieda reprezentujúca požiadavku na odstránenie master dát z databázy.
 * @author Gabriel Cervenak
 */
public class MethodRequestRemoveMD implements IMethodRequest {
    
    private Database database;
    private String id;
    
    /**
     * Vytvorenie novej požiadavky na odstránenie master dát.
     * @param id identifikátor master dát, ktoré chceme odstrániť.
     * @param database EJB poskytujúci prístup k databáze.
     */
    public MethodRequestRemoveMD(String id, Database database){
        this.id = id;
        this.database = database;
    }

    /**
     * Vytvorenie nového vlákna, v ktorom prebehne operácia odstránenia master dát.
     */
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
