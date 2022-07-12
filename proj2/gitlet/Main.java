package gitlet;

import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
    	if(args.length == 0) {
    		System.out.println("Please input a command");
    	}
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
//            	File cwd = new File(System.getProperty("user.dir"));
//            	Commit initial = new Commit("initial commit", null);
            	validateNumArgs(args, 1);
            	Repository.initRepo();
            	
                break;
            case "add":
                // TODO: handle the `add [filename]` command
            	String filename = args[1];
            	Repository.addRepo(filename);
            	
                break;
            // TODO: FILL THE REST IN
        }
        
    }
    
    public static void validateNumArgs(String[] args, int n) {
    	if(args.length != n) {
    		MyUtils.exit("please enter the correct input");
    	}
    }
}
