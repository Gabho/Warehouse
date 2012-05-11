/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicationcontrol;
import aplicationcontrol.UserData;

/**
 *
 * @author kopytko
 */
public class Authorization {
    
    public boolean Authorize(int accessRights,Object rights){
        
        int usersRights = Integer.parseInt(rights.toString());
        if(accessRights <= usersRights){
           return true; 
        } else {
            return false;
        }
    }
    
}
