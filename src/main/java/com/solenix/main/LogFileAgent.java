package com.solenix.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.solenix.constants.LogConstants;
import com.solenix.events.EventTransfer;
import com.solenix.fileManager.LogFileManager;
import com.solenix.fileMatcher.LogFileMatcher;

public class LogFileAgent implements LogConstants {
	
	Path logFile;

	public static void main(String[] args) {
		new LogFileAgent().execute();
	}

	private void execute() {
		LogFileMatcher logFileMatcher = new LogFileMatcher();
		LogFileManager fileManager = new LogFileManager();
		EventTransfer eventTransfer = new EventTransfer();
		try {
			FileWriter writer = new FileWriter(FILE_TO_FTP);
			List<File> files = logFileMatcher.getFiles();
			for (File file : files) {
				logFile = Paths.get(file.getName());
				Logger.getLogger(LogFileAgent.class.getName()).info(logFile.toString());
				
				writer.write(System.lineSeparator());
				writer.write("<------------------------------------------Trx log for file " + logFile+ "------------------------------------------>");
				writer.write(System.lineSeparator());
				List<String> lines = fileManager.read(file);
				fileManager.parse(lines, writer);
				writer.write(System.lineSeparator());
				writer.write("<------------------------------------------End of log for " + logFile+ "------------------------------------------>");
				//eventTransfer.transmit();
			}
			writer.close();	
		} catch (IOException e) {
			Logger.getLogger(LogFileAgent.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		
	}


}
