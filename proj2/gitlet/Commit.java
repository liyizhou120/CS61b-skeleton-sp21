package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable{
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

	
	/*Created Date */
	
	private final Date date; 
    
	/*The message of Commit*/ 
	
	private final String message;
	
	/*parent commits of SHA1-id */
	
    private final List<String> parent; 
    
    /* The SHA1 id */ 
    private final String id;
    
    /* File of this instance*/
    
    private final File file;
    
    /* Sha1 id and associated text */
    
    private final Map<String,String> trackedFiles;
    
    
    public Commit(String message, List<String> parent, Map<String,String> trackedFiles){
    	date = new Date(); 
    	this.message = message; 
    	this.parent = parent; 
    	id = generateID();
    	this.trackedFiles = trackedFiles;
    	file = MyUtils.getObjectFiles(id);
    }

    
    public Commit() {
		// Initial Commit 
    	date = new Date(0);
    	message = "initial Commit";
    	parent = new ArrayList<>();
    	trackedFiles = new HashMap<>(); 
    	id = generateID();
    	file = MyUtils.getObjectFiles(id);
    	
	}


	public String getMessage() {
    	return message;
    }
    
    public List<String> getParent() {
    	return parent;
    }
    
    public String getID() {
    	return id;
    }
    
    public Date getDate() {
    	return date; 
    }
    
    public String getTimeStamp() {
    	DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
    	return dateFormat.format(date);
    }
    
    public String generateID() {
    	return Utils.sha1(getTimeStamp(), parent, trackedFiles.toString());
    }
    
    public Commit fromFiles(String id) {
    	return Utils.readObject(MyUtils.getObjectFiles(id), Commit.class);
    }
    
    public void save() {
    	MyUtils.saveObjectFiles(file, this);
    }
   
    
    
    
   
    /* TODO: fill in the rest of this class. */
}
