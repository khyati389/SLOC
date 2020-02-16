package com.SLOC;

import java.util.Arrays;

/**
 * @author Khyatibahen Chaudhary
 * *** Source Lines Of Codes SLOC Program ****
 * ********** Requirements ***********
 * 1. The total number of Java files. >done
 * 2. The total number of unique Java files. >done
 * 3. The total number of blank lines. (in all unique java files) >done
 * 4. The total number of comment lines. (in all unique java files) >done
 * 5. The total number of code lines. (only code line excluding comments and blanks) >done
 * 6. Add user enabled input
 * 7. Refactor code & remove unused variables
 * 8. Create a jar
 * 9. verify
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
