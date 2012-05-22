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
    private String logged = "error";

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
        createTable();
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT username FROM employee WHERE username ='" + this.name + "' AND password =" + "'" + this.pass + "'");
        if (rs.next() == false) {
            this.name = null;
            this.setRights(0);
        } else {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT accessrights FROM employee WHERE username ='" + this.name + "' AND password =" + "'" + this.pass + "'");
            rs.next();
            this.setRights(rs.getInt(1));
            this.setLogged("logged");
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

    /**
     * @return the logged
     */
    public String isLogged() {
        return logged;
    }

    /**
     * @param logged the logged to set
     */
    public void setLogged(String logged) {
        this.logged = logged;
    }

    /**
     * Vytvorí tabuľku employee, pokiaľ ešte nie je vytvorená
     * a vloží záznam pre admina, pokiaľ sa v tabuľke už nenachádza
     *
     * @throws SQLException
     */
    public void createTable() throws SQLException {
        String createString =
                "create table "
                + "admin.EMPLOYEE "
                + "(USERNAME varchar(20) NOT NULL, "
                + "PASSWORD varchar(20) NOT NULL, "
                + "ACCESSRIGHTS integer NOT NULL)";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createString);
        } catch (SQLException e) {
        }

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username FROM employee WHERE username ='admin' AND password ='admin'");
            if (rs.next() == false) {
                stmt = conn.createStatement();
                stmt.executeUpdate("INSERT INTO EMPLOYEE VALUES('admin', 'admin', 3)");
            }
        } catch (SQLException e) {
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
