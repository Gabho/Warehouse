/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicationcontrol;

import java.sql.*;
import persistence.Database;

/**
 *
 * @author kopytko
 */
public class UserData {
    
    private Database database;
    private String name = null;
    private String pass = null;
    private Statement st;
    private Connection conn;
    private int rights = 0;
    Object o = new Object();
    
    
   
    
    
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
    public void check() throws SQLException {
        String url = "jdbc:derby://localhost:1527/warehouse";
        conn = DriverManager.getConnection(url, "admin", "admin");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT username FROM employee WHERE username ='"+this.name+"' AND password ="+"'"+this.pass+"'");
        if(rs.next() == false){
            this.name = null;
            this.setRights(0);
        } else {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT accessrights FROM employee WHERE username ='"+this.name+"' AND password ="+"'"+this.pass+"'");
            rs.next();
            this.setRights(rs.getInt(1));
            
        }
        
    }

    /**
     * @return the rights
     */
    public int getRights() {
        return rights;
    }

    /**
     * @param rights the rights to set
     */
    public void setRights(int rights) {
        this.rights = rights;
    }
    
    
}
