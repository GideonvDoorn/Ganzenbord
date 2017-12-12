package utils;

import shared.Game;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogger {

    private GameLogger(){

    }

    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    public static void logMessage(String message, Level logLevel){

        LOGGER.log(logLevel, message);

    }

    public static void setLoggerLevel( Level logLevel){

        LOGGER.setLevel(logLevel);

    }
}
