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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Query {

    public static ResultSet executeQuery(String Statement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        DataBase obj = null;
        ResultSet rs = null;
        try {
            obj = DataBase.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection SQL = obj.get_database();
        if (SQL == null) {
            JOptionPane.showMessageDialog(null, "Database Not Connected");
        } else {
            PreparedStatement pst = null;
            try {
                pst = SQL.prepareStatement(Statement);
            } catch (SQLException ex) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (pst != null)
                    try {
                rs = pst.executeQuery();
            } catch (SQLException ex) {
            }
        }
        return rs;
    }

    public static int executeUpdate(String Statement) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        int row_affected = 0;
        DataBase obj = null;
        try {
            obj = DataBase.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection SQL = obj.get_database();
        if (SQL == null) {
            JOptionPane.showMessageDialog(null, "Database Not Connected");
        } else {
            PreparedStatement pst = null;
            try {
                pst = SQL.prepareStatement(Statement);
            } catch (SQLException ex) {
                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                row_affected = pst.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error in Query:\n" + ex.toString());
            }
        }
        return row_affected;
    }
}
