package gitlet;

import java.io.File;
import java.io.Serializable;

public class MyUtils {
	
	
	/* exit the program and print a message */
	public static void exit(String message, Object...args) {
		Utils.message(message, args);
		System.exit(0);
	}
	
	/* Get a file instance with the Sha1-ID */
	
	public static File getObjectFiles(String id) {
		String dirName = getFileDirName(id);
		String fileName = getFileName(id);
		return Utils.join(fileName, dirName);
	}
	
	/* Get directory name for the Sha1-ID */

	public static String getFileDirName(String id) {
		return id.substring(0,2);
	}
	
	public static String getFileName(String id) {
		return id.substring(2);
	}
	
	
	public static void saveObjectFiles(File file, Serializable obj) {
		File dir = file.getParentFile();
		if(!dir.exists()) {
			dir.mkdir();
		}
		Utils.writeObject(file, obj);
	}
	
	
}
