package ca.jrvs.apps.jdbc.exercises;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private static final Logger logger = LoggerFactory.getLogger(Logger.class);
    static { BasicConfigurator.configure(); };

    public static Logger getLogger() {
        return logger;
    }
}
