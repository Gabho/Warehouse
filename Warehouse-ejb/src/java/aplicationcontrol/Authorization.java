/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicationcontrol;
import aplicationcontrol.UserData;

/**
 * Trieda zabezpečujúca autoriyáciu
 * @author kopytko
 */
public class Authorization {
    
    /* V tejto triede sa porovnajú používateľové práva s právami na 
    *  vykonanie danej operácie a vráti vzhodnotenie.
    */
    public boolean Authorize(int accessRights,Object rights){
        
        int usersRights = Integer.parseInt(rights.toString());
        if(accessRights <= usersRights){
           return true; 
        } else {
            return false;
        }
    }
    
}
