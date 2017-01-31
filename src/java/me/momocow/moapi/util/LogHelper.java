package me.momocow.moapi.util;

import org.apache.logging.log4j.Level;

import net.minecraftforge.fml.common.FMLLog;

public class LogHelper
{
	private final String mod;
	
	public LogHelper(String m) {
		this.mod = m;
	}
	
	private void createLog(Level logLevel, Object object){
		FMLLog.log(this.mod, logLevel, String.valueOf(object));
	}
	
	public void off(Object object) { createLog(Level.OFF,object); }
	
	public void fatal(Object object) { createLog(Level.FATAL,object); }
	
	public void error(Object object) { createLog(Level.ERROR,object); }
	
	public void warn(Object object) { createLog(Level.WARN,object); }
	
	public void info(Object object) { createLog(Level.INFO,object); }
	
	public void debug(Object object){ 
		createLog(Level.DEBUG,object);
	}
	
	public void trace(Object object) { createLog(Level.TRACE,object); }
	
	public void all(Object object) { createLog(Level.ALL,object); }
	
	
}