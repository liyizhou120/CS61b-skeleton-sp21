package gitlet;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
	
	public static final String DEFAULT_BRANCH = "master";
	public static final String HEAD_BRANCH_REF_PREFIX = "ref: refs/heads/";
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File INDEX = join(GITLET_DIR, "index");
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    public static final File OBJECT_DIR = join(GITLET_DIR, "objects");
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    public static final File BRANCH_HEADS_DIR = join(REFS_DIR, "heads");
    
    
    
    
    
    /* TODO: fill in the rest of this class. */
    
    /** handles the init command*/
    /**
     * Initialize a repository at the current working directory.
     *
     * <pre>
     * .gitlet
     * ├── HEAD
     * ├── objects
     * └── refs
     *     └── heads
     * </pre>
     */
    
    public static void initRepo() {
    	if(GITLET_DIR.exists()) {
    		MyUtils.exit("A Gitlet version-control system already exists in the current directory");  //exit 
    	}
 		GITLET_DIR.mkdir();
 		INDEX.mkdir();
 		REFS_DIR.mkdir();
 		OBJECT_DIR.mkdir();
 		setCurrentBranch(DEFAULT_BRANCH);
 		createInitialCommit();
 		
    }
    
    /** Add the file to the staging area */ 
    
    public static void addRepo(String file){
    	File f = new File(file);
    	
    	
//    	byte[] content = Utils.readContents(f);
//    	String s = Utils.sha1(content);
//    	System.out.println("sha hash: " + s);
//    	System.out.println("contents" + new String(content, StandardCharsets.UTF_8));
    	
    	
    	
    }
    
    
    public static void setCurrentBranch(String branchName) {
    	Utils.writeContents(HEAD, HEAD_BRANCH_REF_PREFIX + branchName);
    }
    
    public static File getBranchHeadFile(String branchName) {
    	return Utils.join(BRANCH_HEADS_DIR, branchName);
    }
    
    
    public static void setBranchHeadCommit(String branchName, String CommitID) {
    	File branchHeadFile = getBranchHeadFile(branchName);
    	setBranchHeadCommit(branchHeadFile, CommitID);
    }
    
    public static void setBranchHeadCommit(File BranchHeadFile, String commitID) {
    	Utils.writeContents(BranchHeadFile, commitID);
    }
    
    
    public static void createInitialCommit() {
    	Commit initialCommit = new Commit(); 
    	initialCommit.save();
    	setBranchHeadCommit(DEFAULT_BRANCH, initialCommit.getID());
    } 
    
    
    
    
}
