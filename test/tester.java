
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import multitoolapp.common.JdbcDaoConnectionFactory;
import multitoolapp.common.PropertiesFileOperations;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Chandan
 */
public class tester {
    static Connection connection = null;

    private static Connection getConnectionr() {
        Connection conn = null;
        try {

            conn = JdbcDaoConnectionFactory.getInstance().getConnection("HOME");

        } catch (SQLException ex) {
            Logger.getLogger(tester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void main(String[] args) {
        connection  = getConnectionr();
        // JdbcDaoConnectionFactory tp = new JdbcDaoConnectionFactory();
    }

}
