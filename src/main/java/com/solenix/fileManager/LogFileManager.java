package com.solenix.fileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.solenix.constants.LogConstants;
import com.solenix.events.Event;

public class LogFileManager implements LogConstants{
	

	public List<String> read(File file) {
		String pattern = READ_FILE_PATTERN;
		return readLinesUtil(file, pattern);
	}

	public List<Event> parse(List<String> listofLines, FileWriter writer) {
		List<Event> eventList = new ArrayList<Event>();
		Event event = new Event();
		String pattern = EVENT_PATTERN;
		
		try {
			for (String string : listofLines) {
				if (string.contains(pattern)) {
					event.setEvent(string);
					eventList.add(event);
					writer.write(string + System.lineSeparator());
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return eventList;
	}

	
	public List<String> readLinesUtil(File file, String pattern) {
		List<String> filteredLines = new ArrayList<String>();
		String line;
		int lines = 0;
		int characters = 0;
		try {
			try (BufferedReader in = new BufferedReader(new FileReader(file))) {
				Pattern p = Pattern.compile(pattern);
				in.skip(characters);

				while ((line = in.readLine()) != null) {
					lines++;
					characters += line.length() + System.lineSeparator().length();
					if (p.matcher(line).find()) {
						filteredLines.add(line);
					}
				}

			}
		} catch (IOException ex) {
			Logger.getLogger(LogFileManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
		return filteredLines;
	}
}
