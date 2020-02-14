import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class LOC {

    private List<File> uniqueFiles;
    private List<File> allFiles;
    private Map<String, File> fileContentMap;

    private int totalFiles;
    private int totalUniqueFiles;
    private int totalBlankLines;
    private int totalCommentLines;
    private int totalCodeLines;

    LOC() {
      this.uniqueFiles = new ArrayList<>();
      this.allFiles = new ArrayList<>();
      this.fileContentMap = new HashMap<>();

      this.totalFiles = 0;
      this.totalUniqueFiles = 0;
      this.totalBlankLines = 0;
      this.totalCommentLines = 0;
      this.totalCodeLines = 0;
    }

    /**
     * Program Start Point.
     */
    public void start(String path) {
        File file = new File(path);
        if(file.exists()) { // Check if file or directory exists
            if(file.isDirectory()) { // Check if path is directory
                this.getListOfAllFiles(path);
                this.getListOfUniqueFiles();
                this.processFiles();
            }else if(file.isFile()) { // Check if path is file
                totalFiles = 1;
                totalUniqueFiles = 1;
                this.readFileByLine(new File(path));
            }
        }else {
            System.out.println("Error! path: "+path+" does not exist!");
        }
    }

    /**
     * Read java file.
     * @return content of a file
     * @param filePath path of a file
     */
    public String readFile(File filePath) throws IOException {
        return Files.readString(Paths.get(String.valueOf(filePath)));
    }

    /**
     * finds all java files inside a directory.
     * @param directory directory path
     */
    public void getListOfAllFiles(String directory) {
        SuffixFileFilter ext = new SuffixFileFilter(".java");
        allFiles = (List<File>) FileUtils.listFiles(new File(directory),
                            ext, TrueFileFilter.INSTANCE);
        totalFiles = allFiles.size();
    }

    /**
     * finds unique java files inside a directory.
     */
    public void getListOfUniqueFiles() {
        allFiles.forEach(filePath -> {
            try {
                String content = this.readFile(filePath);
                if(!fileContentMap.containsKey(content)) {
                    fileContentMap.put(content, filePath);
                    uniqueFiles.add(filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if(uniqueFiles.size() > 0) {
            totalUniqueFiles = uniqueFiles.size();
        }
    }

    /**
     * Process list of files inside a directory.
     */
    public void processFiles() {
        for (File filePath : uniqueFiles) {
            this.readFileByLine(filePath);
        }
    }

    /**
     * Read a file line by line and counts
     * blank lines, comments and source code lines.
     * @param filePath path of a file
     */
    public void readFileByLine(File filePath) {
        AtomicBoolean isComment = new AtomicBoolean(false);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(filePath)))) {
            stream.map(String::trim).forEach(currentLine -> {
                if (!isComment.get() && (currentLine.isBlank() || currentLine.equals(" "))) { // counts blank lines
                    totalBlankLines++;
                } else if (!isComment.get() && currentLine.startsWith("//")) { // counts single line comment
                    totalCommentLines++;
                } else if (!isComment.get() && currentLine.startsWith("/*")) {
                    if (!currentLine.endsWith("*/")) { // counts single line comment
                        isComment.set(true); // enable multiple comment code on
                    }
                    totalCommentLines++;
                } else if (isComment.get()) { //counts multiple lines comments
                    totalCommentLines++;
                    if(currentLine.endsWith("*/")) {
                        isComment.set(false);
                    }
                } else { // counts code lines
                    totalCodeLines++;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public int getTotalUniqueFiles() {
        return totalUniqueFiles;
    }

    public int getTotalBlankLines() {
        return totalBlankLines;
    }

    public int getTotalCommentLines() {
        return totalCommentLines;
    }

    public int getTotalCodeLines() {
        return totalCodeLines;
    }

    @Override
    public String toString() {
        return  totalFiles +
                "-" + totalUniqueFiles +
                "-" + totalBlankLines +
                "-" + totalCommentLines +
                "-" + totalCodeLines;
    }
}
