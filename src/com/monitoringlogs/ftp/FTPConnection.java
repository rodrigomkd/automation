package com.monitoringlogs.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.monitoringlogs.entities.Message;

public class FTPConnection {
	
	public List<Message> readLogsFile(String path, Timestamp timestamp) {
		
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
				if(m.getTime().after(timestamp)) {
					logs.add(m);
				}
				
				message = "";
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();		
		}
		
		return logs;
	}
}

