/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author RFaiz
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DataBase {

    private volatile static DataBase uniqueInstance = null;
    private Connection conn = null;

    private DataBase() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

    }

    public Connection get_database() {
        return conn;
    }

    public void connect_database() {
        connect_database("root", null);
    }

    public boolean connect_database(String user, String pass) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:MYSQL://localhost/studentmanagement";
            conn = DriverManager.getConnection(dbURL, user, pass);
        } catch (SQLException ex) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Error Connecting Database Server " + ex);
            System.exit(1);
        } catch (ClassNotFoundException ex1) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, "Error Connecting Database Server " + ex1);
            System.exit(1);
        }
        return true;
    }

    public void disconnect_database() throws SQLException {
        conn.close();
        conn = null;
    }

    public static DataBase getInstance() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (uniqueInstance == null) { //Single Checked
            synchronized (DataBase.class) {
                if (uniqueInstance == null) { //Double Checked
                    uniqueInstance = new DataBase();
                }
            }
        }
        return uniqueInstance;
    }
}
