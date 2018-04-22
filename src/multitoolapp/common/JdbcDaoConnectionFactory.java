package multitoolapp.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import multitoolapp.ui.LdtMain.LdtMainWindowController;

public class JdbcDaoConnectionFactory {

    String driverClassName = "oracle.jdbc.driver.OracleDriver";

    private static JdbcDaoConnectionFactory connectionFactory = null;

    private JdbcDaoConnectionFactory() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(String InstanceName) throws SQLException {
        LdtMainWindowController instanceName = new LdtMainWindowController();

        PropertiesFileOperations propFile = new PropertiesFileOperations();

        String[] connInfo = propFile.readInastanceSetupInfo(instanceName.getSelectedInstanceName());

        String[] nonAppUsr = propFile.ReadPropertiesFile(InstanceName + "_NONAPPS_USR");

        String[] nonAppUsrPwd = propFile.ReadPropertiesFile(InstanceName + "_NONAPPS_USR_PWD");

       //String[] nonAppUsrPwd = propFile.ReadPropertiesFile("HOME_NONAPPS_USR_PWD");
        String connectionUrl = connInfo[6];//"jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = nonAppUsr[1];//"SYSTEM";
        String dbPwd = nonAppUsrPwd[1];//"adm1n";
        System.out.println(dbUser + "" + dbPwd+" "+connectionUrl);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
  //      alert.setTitle("Error!!");
    //    alert.setHeaderText(null);
      //  alert.setContentText("DB User and password!!");
        //alert.showAndWait();
        Connection conn = null;
        conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
        return conn;
    }

    public static JdbcDaoConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new JdbcDaoConnectionFactory();
        }
        return connectionFactory;
    }
}
