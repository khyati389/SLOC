/**
 * @author Khyatibahen Chaudhary
 * *** Source Lines Of Codes SLOC Program ****
 * ********** Requirements ***********
 * 1. The total number of Java files. >done
 * 2. The total number of unique Java files. >done
 * 3. The total number of blank lines. (in all unique java files)
 * 4. The total number of comment lines. (in all unique java files)
 * 5. The total number of code lines. (only code line excluding comments and blanks)
 */

public class DriverClass {
    public static void main(String[] args) {
        LOC locObj = new LOC();
        locObj.start("C:\\Users\\Manan\\Desktop\\tempTest");
        System.out.println(locObj.toString());
    }
}
