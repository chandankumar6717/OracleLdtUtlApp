/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chandan
 */
public class PropertiesFileOperations {

    public static final File file = new File("Instance_configuration.properties");

    private void CreatePropertiesFile() {

        if (file.exists()) {
            //do nothing            
        } else {
            try {
                Properties properties = new Properties();

                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    properties.store(fileOut, "Instance Configurations");
                    fileOut.close();
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }
    }

    public void WritePropertiesFile(String PropKey, String PropKeyvalue) throws Exception {
        try {
            //System.out.println("saving config file has been inititated");
            PropertiesFileOperations propFileOper = new PropertiesFileOperations();
            propFileOper.CreatePropertiesFile();
            Properties properties = new Properties();
            //first load old one:
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();

            //modifies existing or adds new property
            properties.setProperty(PropKey, PropKeyvalue);

            //save modified property file
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Instance Configurations");
            fileOut.close();
        } catch (IOException e) {

        }
    }

    public String[] ReadPropertiesFile(String PropKey)  {

        PropertiesFileOperations propFileOper = new PropertiesFileOperations();

        propFileOper.CreatePropertiesFile();

        Properties properties = new Properties();
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesFileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            properties.load(fileInput);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesFileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

        String keysValue = properties.getProperty(PropKey, "null");

        String[] keyValuePair = new String[2];
        keyValuePair[0] = PropKey;
        keyValuePair[1] = keysValue;

        try {
            fileInput.close();
        } catch (IOException ex) {
            Logger.getLogger(PropertiesFileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return keyValuePair;

    }

    public String[] readInastanceSetupInfo(String InstanceName) {
        String[] CredencialsFrmConfig = new String[9];
        try {

            String[] InstanceInfoKeyArray = {InstanceName + "_INSTANCENAME", InstanceName + "_UNIX_HOST",
                InstanceName + "_UNIX_USER", InstanceName + "_UNIX_USER_PWD", InstanceName + "_UNIX_ENV_CMD",
                InstanceName + "_UNIX_TEMP_PATH", InstanceName + "_JDBC_CON_STRING", InstanceName + "_APPS_PWD"};

            String[] pro_INSTANCENAME = new String[2];
            String[] pro_UNIX_HOST = new String[2];
            String[] pro_UNIX_USER = new String[2];
            String[] pro_UNIX_USER_PWD = new String[2];
            String[] pro_UNIX_ENV_CMD = new String[2];
            String[] pro_UNIX_TEMP_PATH = new String[2];
            String[] pro_JDBC_CON_STRING = new String[2];
            String[] pro_APPS_PWD = new String[2];

            // reading the details from config file and setting in config window
            PropertiesFileOperations propFileOper = new PropertiesFileOperations();
            pro_INSTANCENAME = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[0]);
            pro_UNIX_HOST = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[1]);
            pro_UNIX_USER = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[2]);
            pro_UNIX_USER_PWD = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[3]);
            pro_UNIX_ENV_CMD = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[4]);
            pro_UNIX_TEMP_PATH = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[5]);
            pro_JDBC_CON_STRING = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[6]);
            pro_APPS_PWD = propFileOper.ReadPropertiesFile(InstanceInfoKeyArray[7]);
            

            CredencialsFrmConfig[0] = pro_INSTANCENAME[1];
            CredencialsFrmConfig[1] = pro_UNIX_HOST[1];
            CredencialsFrmConfig[2] = pro_UNIX_USER[1];
            CredencialsFrmConfig[3] = pro_UNIX_USER_PWD[1];
            CredencialsFrmConfig[4] = pro_UNIX_ENV_CMD[1];
            CredencialsFrmConfig[5] = pro_UNIX_TEMP_PATH[1];
            CredencialsFrmConfig[6] = pro_JDBC_CON_STRING[1];
            CredencialsFrmConfig[7] = pro_APPS_PWD[1];
            //CredencialsFrmConfig[8] = pro_DB_USR[1];
            //CredencialsFrmConfig[9] = pro_DB_USR_PWD[1];

        } catch (Exception ex) {
            Logger.getLogger(PropertiesFileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return CredencialsFrmConfig;

    }

}
