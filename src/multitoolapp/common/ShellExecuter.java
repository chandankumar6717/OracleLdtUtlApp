/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.common;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chandan
 */
public class ShellExecuter {

    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void downloadFndlaod(String[] dwnldFndloadinfo, String ComponentType, String ComponentName) {
        String host = dwnldFndloadinfo[1];
        String user = dwnldFndloadinfo[2];
        String password = dwnldFndloadinfo[3];
        String EnvFileCmd = dwnldFndloadinfo[4];
        String varTmp = dwnldFndloadinfo[5];
        String jdbcString = dwnldFndloadinfo[6];
        String appsPwd = dwnldFndloadinfo[7];

        logr.info("in shell executer");

        // log.logger.log(Level.INFO, "host:{0}", host);
        // log.logger.log(Level.INFO, "user:{0}", user);
        // log.logger.log(Level.INFO, "password:{0}", password);
        // log.logger.log(Level.INFO, "EnvFileCmd:{0}", EnvFileCmd);
        // log.logger.log(Level.INFO, "varTmp:{0}", varTmp);
        // log.logger.log(Level.INFO, "ComponentType:{0}", ComponentType);
        // log.logger.log(Level.INFO, "ComponentName:{0}", ComponentName);
        // log.logger.log(Level.INFO, "appsPwd:{0}", appsPwd);
        //logr.info("jdbcString:{0}" + jdbcString);

        // System.out.println("host:" + host);
        // System.out.println("user:" + user);
        // System.out.println("password:" + password);
        // System.out.println("EnvFileCmd:" + EnvFileCmd);
        // System.out.println("varTmp:" + varTmp);
        // System.out.println("ComponentType:" + ComponentType);
        // System.out.println("ComponentName:" + ComponentName);
        // System.out.println("appsPwd123:" + appsPwd);
        // System.out.println("jdbcString:" + dwnldFndloadinfo[6]);
        //
        BuildLdtCommand ldtcmd = new BuildLdtCommand();
        String command = ldtcmd.buildLDTdownloadCmd(ComponentType, ComponentName, appsPwd, jdbcString);
        System.out.println("LDT command" + command);

        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("exec");

            ((ChannelExec) channel).setCommand(EnvFileCmd + ";" + " cd " + varTmp + "; " + command);
            // "cd /u02/app/applmgr/122/biodevc; echo R|. ./EBSapps.env; cd /var/tmp;"
            System.out.println("EnvFileCmd:" + EnvFileCmd);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ee) {

                }
            }
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE in sheel executer");
        } catch (JSchException | IOException e) {
            logr.log(Level.SEVERE, "A fake error occurred mate!", e);
            
        } finally {

        }

        /**
         * finally{ channel.disconnect(); session.disconnect(); channel.exit();
         * channel.disconnect(); session.disconnect(); }
         *
         */
    }

    public void uploadFndlaod(String hostName, String ora_user, String ora_password, String comp_type,
            String LDTfileName, String appsPwd, String EnvFileCmd, String varTmp) {
        String host = hostName;
        String user = ora_user;
        String password = ora_password;

        BuildLdtCommand ldtcmd = new BuildLdtCommand();
        String command = ldtcmd.buildLDTloaderCmd(comp_type, LDTfileName, appsPwd, varTmp);

        // command = "FNDLOAD apps/" + appsPwd
        // + " 0 Y UPLOAD $FND_TOP/patch/115/import/afffload.lct "
        // + varTmp + "/" + LDTfileName;
        // System.out.println("command:"+command);
        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            // System.out.println("Connected");
            Channel channel = session.openChannel("exec");
            System.out.println("in shell esec and testing for various shell command");
            ((ChannelExec) channel).setCommand(EnvFileCmd + ";" + " cd " + varTmp + ";" + command);

            // "cd /u02/app/applmgr/122/biodevc; echo R|. ./EBSapps.env; cd /var/tmp;"+
            // command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    // System.out.println("exit-status: " +
                    // channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ee) {

                }
            }
            channel.disconnect();
            session.disconnect();
            // System.out.println("DONE");
        } catch (JSchException | IOException e) {
        }

        /**
         * finally{ channel.disconnect(); session.disconnect(); channel.exit();
         * channel.disconnect(); session.disconnect(); }
         *
         */
    }
}
