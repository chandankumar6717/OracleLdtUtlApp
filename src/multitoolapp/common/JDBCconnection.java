/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.common;

import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author Chandan
 */
public class JDBCconnection {

    String applShortName;
    //private String sql;

    public void connection(String hostString, String Schema, String DBpwd, String sqlCmd) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("JDBCconnection"+hostString);
            System.out.println("JDBCconnection"+Schema);
            System.out.println("JDBCconnection"+DBpwd);
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + hostString, Schema, DBpwd);
            // "jdbc:oracle:thin:@localhost:1521:xe", "HR", "hr"
            Statement st = con.createStatement();
            String sql = sqlCmd;//"select job_title from jobs where rownum <2";
            ResultSet rs;
            rs = st.executeQuery(sql);
            while (rs.next()) {
                applShortName = rs.getString(1);
            }
            con.close();

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public String getappShortName() {
        return applShortName;
    }

}
