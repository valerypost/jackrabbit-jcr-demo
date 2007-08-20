package org.apache.jackrabbit.demo.blog.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class used to read error codes amd error messages from a xml
 */
public class ErrorConfig {
	
	private static Properties errorCodes;
	public static String ERROR_CODE_XML;
	public static boolean DEBUG = true;
	
	public static void init() throws Exception {
		try {
			
			errorCodes = new  Properties();
			File file = new File(ERROR_CODE_XML);
			errorCodes.loadFromXML(new FileInputStream(file));
			
			System.out.println("JACKRABBIT-JCR-DEMO: error codes loaded ...");
		

		} catch (IOException e) {
			throw e;
		}
	}
	
	public static String getErrorMessage(String code) {
		
		return errorCodes.getProperty(code);
		
	}

}
