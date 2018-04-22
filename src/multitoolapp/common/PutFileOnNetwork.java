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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Chandan
 */
public class PutFileOnNetwork {

    public void putFile(String hostName, String userName,
            String Password, String copyFromPath, String copyToPath,
            String fileName) {
        String user = userName;
        String host = hostName;
        String password = Password;
        String watchFolder = copyFromPath;// "C:\\Users\\671757\\Documents\\TCS_GE_WIP\\Devlopment\\Putty";
        String outputDir = copyToPath;// "/var/tmp";
        String filemask = '"'+fileName+'"';
        JSch jsch = new JSch();
        Session session = null;
        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session = jsch.getSession(user, host, 22);
            session.setConfig(config);
            session.setPassword(password);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            // System.out.println("connected");
            File[] files = findFile(watchFolder, filemask);

            for (File file : files) {
                putFile(file, sftpChannel, outputDir);
            }

            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
        }

    }

    public static void putFile(File file, ChannelSftp sftpChannel, String outputDir) {
        FileInputStream fis = null;
        // Change to output directory.
        try {
            sftpChannel.cd(outputDir);
        } catch (SftpException e) {
            // TODO Auto-generated catch block

        }

        // Upload file.
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

        }
        try {
            sftpChannel.put(fis, file.getName());
        } catch (SftpException e) {
            // TODO Auto-generated catch block

        }
        try {
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block

        }

    }

    public static File[] findFile(String dirName, final String mask) {
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(mask);
            }
        });
    }

}
