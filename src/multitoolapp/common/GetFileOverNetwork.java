/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.common;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.util.Properties;
//import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
//import multitoolapp.log.LogFile;

/**
 *
 * @author Chandan
 */
public class GetFileOverNetwork {

    public void getFile(String hostName, String userName, String Password, String copyFromPath, String copyToPath) {
        try {
            String hostname = hostName;
            String username = userName;
            String password = Password;
            String copyFrom = copyFromPath;// "file_path_in_remote_linux_server";
            String copyTo = copyToPath;// "path_in_local_server";
            JSch jsch = new JSch();
            Session session = null;
            System.out.println("Trying to connect to get the file.....");
            System.out.println("hostname:" + hostname);
            System.out.println("username:" + username);
            System.out.println("password:" + password);
            System.out.println("copyFrom:" + copyFrom);
            System.out.println("copyTo:" + copyTo);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session = jsch.getSession(username, hostname, 22);
            session.setConfig(config);
            session.setPassword(password);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(copyFrom, copyTo);
            System.out.println("getting file");
            sftpChannel.exit();
            session.disconnect();

            System.out.println("Done !!");
        } catch (JSchException | SftpException ex) {
            Logger.getLogger(GetFileOverNetwork.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

}
