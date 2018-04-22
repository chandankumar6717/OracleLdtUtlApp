/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.log;

import java.io.IOException;
import java.util.logging.*;

/**
 *
 * @author Chandan
 */
public class LogFile {

    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static void setupLogger() {
        LogManager.getLogManager().reset();
        logr.setLevel(Level.ALL);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logr.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("MyLogger.log");//for appending put true

            fh.setLevel(Level.FINE);
            logr.addHandler(fh);
            logr.info("Logging has been started");

        } catch (IOException | SecurityException ex) {
            logr.log(Level.SEVERE, "FIle logger not working", ex);
        }

    }

    public static void start_logging() {
        setupLogger();

    }

}
