package com.monitoringlogs.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.monitoringlogs.entities.Message;

public class FTPConnection {
	
	public List<Message> readLogsFile(String path) {
		
		List<Message> logs = new ArrayList<Message>();
		File file = new File(path); 
		  
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		
			String st;
			String message = "";
			while ((st = br.readLine()) != null) {
				message = st;
				if(st.contains("\t")) {
					message += st;
					continue;
				}
				Message m = new Message(message);
				System.out.println(m);
				logs.add(m);
				message = "";
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();		
		}
		
		return logs;
	}
	
	public static void main(String mkd []) {
		FTPConnection ftp = new FTPConnection();
		ftp.readLogsFile("/Users/moncada/eclipse-workspace-orion/ManagerLogs/resources/business_objects.log");
	}
}

