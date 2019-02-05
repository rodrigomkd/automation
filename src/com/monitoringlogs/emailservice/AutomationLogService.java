package com.monitoringlogs.emailservice;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AutomationLogService extends Thread {
	private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Utilidades");

	@Override
	public void run() {
		LOGGER.log(Level.INFO, "Start Automation Logs Service...");
		//check email every minute
		try {
			while(true) {
				EmailUtils.check();
				Thread.sleep(60000);
			}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String [] mkd) {
		Thread service = new AutomationLogService();
		service.start();
	}
}
