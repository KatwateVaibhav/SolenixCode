package com.solenix.events;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.solenix.constants.FTPConstants;
import com.solenix.constants.LogConstants;

public class EventTransfer implements LogConstants,FTPConstants {

	public void transmit() {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.setConnectTimeout(SESSION_TIMEOUT);
			ftpClient.connect(REMOTE_HOST, REMOTE_PORT);
			ftpClient.login(USERNAME, PASSWORD);
			ftpClient.enterLocalPassiveMode();
			

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			File eventFile = new File(FILE_TO_FTP);

			String firstRemoteFile = "fileToFtp.log";
			InputStream inputStream = new FileInputStream(eventFile);

			System.out.println("Start uploading first file");
			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
			inputStream.close();
			if (done) {
				System.out.println("The first file is uploaded successfully.");
			}

		} catch (IOException ex) {
			Logger.getLogger(EventTransfer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			shutDown(ftpClient);	
		}
	}
	
	public void shutDown(FTPClient ftpClient) {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
