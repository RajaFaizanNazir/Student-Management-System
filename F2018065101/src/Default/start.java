package Default;

import java.sql.SQLException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author RFaiz
 */
public class start {

    public static void main(String[] str) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Database.DataBase.getInstance().connect_database();
        MAIN.showGUI();
    }
}
