import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class LOC {

    private Set<Path> javaFilesSet;
    private Set<Path> directories;
    private Map<String, Path> fileContentMap;

    private int totalFiles;
    private int totalUniqueFiles;
    private int totalBlankLines;
    private int totalCommentLines;
    private int totalCodeLines;

    LOC() {
      this.javaFilesSet = new HashSet<>();
      this.directories = new HashSet<>();
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
                this.getListOfUniqueFiles(path);
                this.processFiles();
            }else if(file.isFile()) { // Check if path is file
                totalFiles = 1;
                totalUniqueFiles = 1;
                this.readFileByLine(Paths.get(path));
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
    public String readFile(Path filePath) throws IOException {
        return Files.readString(Paths.get(String.valueOf(filePath)));
    }

    /**
     * finds all java files inside a directory.
     * @param directory directory path
     */
    public void getListOfAllFiles(String directory) {
        try {
            int count = (int) Files.list(Paths.get(directory))
                        .filter(path -> path.toString()
                        .endsWith(".java"))
                        .count();
            totalFiles = count;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * finds unique java files inside a directory.
     * @param directory directory path
     */
    public void getListOfUniqueFiles(String directory) {
        try {
            Stream<Path> files = Files.list(Paths.get(directory))
                                .filter(path -> path.toString()
                                .endsWith(".java"));

            files.forEach(filePath -> {
                try {
                    String content = this.readFile(filePath);
                    if(!fileContentMap.containsKey(content)) {
                        fileContentMap.put(content, filePath);
                        javaFilesSet.add(filePath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            if(javaFilesSet.size() > 0) {
                totalUniqueFiles = javaFilesSet.size();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Process list of files inside a directory.
     */
    public void processFiles() {
        for (Path filePath : javaFilesSet) {
            this.readFileByLine(filePath);
        }
    }

    /**
     * Read a file line by line and count
     * blank lines, comments and source code lines.
     * @param filePath path of a file
     */
    public void readFileByLine(Path filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(filePath)))) {
            stream.forEach(line -> {
                String currentLine = line.trim();
                if (currentLine.isBlank() || currentLine == " ") {
                    totalBlankLines++;
                }else if (currentLine.startsWith("/*") || currentLine.startsWith("//")) {
                    totalCommentLines++;
                }else {
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
        return "LOC{" +
                "totalFiles=" + totalFiles +
                ", totalUniqueFiles=" + totalUniqueFiles +
                ", totalBlankLines=" + totalBlankLines +
                ", totalCommentLines=" + totalCommentLines +
                ", totalCodeLines=" + totalCodeLines +
                '}';
    }
}
