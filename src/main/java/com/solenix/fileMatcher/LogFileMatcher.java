package com.solenix.fileMatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.solenix.constants.LogConstants;

public class LogFileMatcher implements LogConstants {

	public List<File> getFiles() {
		List<File> result = null;
		try {
			result = Files.list(Paths.get(FOLDER_PATH)).filter(Files::isRegularFile)
					.filter(path -> path.toString().endsWith(".log")).map(Path::toFile).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}
}
