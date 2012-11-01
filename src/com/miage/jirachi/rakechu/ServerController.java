package com.miage.jirachi.rakechu;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;


public class ServerController {
    public final static int VERSION_MAJOR = 0;
    public final static int VERSION_MINOR = 1;
    public final static int VERSION_REVISION = 0;
    
    public static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger("MAIN");
    
	/**
	 * Main
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    System.out.println("Firing up logger...");
	    
	    PatternLayout logLayout = new PatternLayout("%d{HH:mm:ss} %-5p - %F:%-4L - %m%n");
	    LOG.addAppender(new ConsoleAppender(logLayout));
	    LOG.addAppender(new FileAppender(logLayout, "main.log", false));
	    LOG.setAdditivity(true);
	    
	    LOG.debug("Logger started!");
		LOG.info("===============================");
		LOG.info("  _____       _             _           ");
		LOG.info(" |  __ \\     | |           | |          ");
		LOG.info(" | |__) |__ _| | _____  ___| |__  _   _ ");
		LOG.info(" |  _  // _` | |/ / _ \\/ __| '_ \\| | | |");
		LOG.info(" | | \\ | (_| |   |  __| (__| | | | |_| |");
		LOG.info(" |_|  \\_\\__,_|_|\\_\\___|\\___|_| |_|\\__,_|");
		LOG.info("                                        ");
		LOG.info("===============================");
		LOG.info("");
		LOG.info("Version " + VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_REVISION);
		LOG.info("Copyright (c) 2012-2013 Guillaume Lesniak, Elise Richard, Myriam Delaruelle");
		LOG.info("");
        
		NetworkController.getInstance().startLoop();
	}

}
