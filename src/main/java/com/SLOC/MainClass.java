package com.SLOC;

/**
 * @author Khyatibahen Chaudhary
 * *** Source Lines Of Codes SLOC Program ****
 * ********** Requirements ***********
 * 1. The total number of Java files.
 * 2. The total number of unique Java files.
 * 3. The total number of blank lines. (in all unique java files)
 * 4. The total number of comment lines. (in all unique java files)
 * 5. The total number of code lines. (only code line excluding comments and blanks)
 * 6. Create a jar
 */

public class MainClass {
    public static void main(String[] args) {
        LOC locObj = new LOC();
        if(args.length == 0) {
            System.out.println("Please provide a path of Directory or File!");
        }else {
            locObj.start(String.join(" ", args).trim());
            System.out.println(locObj.toString());
        }
    }
}
