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

    private int countOfAllFiles;
    private int countOfAllUniqueFiles;
    private int totalBlankLines;
    private int totalCommentLines;
    private int totalCodeLines;

    LOC() {
      this.javaFilesSet = new HashSet<>();
      this.directories = new HashSet<>();
      this.fileContentMap = new HashMap<>();

      this.countOfAllFiles = 0;
      this.countOfAllUniqueFiles = 0;
      this.totalBlankLines = 0;
      this.totalCommentLines = 0;
      this.totalCodeLines = 0;
    }

    /**
     * Program Start Point.
     */
    public void start(String path) {
        this.getListOfAllFiles(path);
        this.getListOfUniqueFiles(path);
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
     */
    public void getListOfAllFiles(String directory) {
        try {
            int count = (int) Files.list(Paths.get(directory))
                        .filter(path -> path.toString()
                        .endsWith(".java"))
                        .count();
            this.setCountOfAllFiles(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * finds unique java files inside a directory.
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
                this.setCountOfAllUniqueFiles(javaFilesSet.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCountOfAllFiles() {
        return countOfAllFiles;
    }

    public void setCountOfAllFiles(int countOfAllFiles) {
        this.countOfAllFiles = countOfAllFiles;
    }

    public int getCountOfAllUniqueFiles() {
        return countOfAllUniqueFiles;
    }

    public void setCountOfAllUniqueFiles(int countOfAllUniqueFiles) {
        this.countOfAllUniqueFiles = countOfAllUniqueFiles;
    }

    public int getTotalBlankLines() {
        return totalBlankLines;
    }

    public void setTotalBlankLines(int totalBlankLines) {
        this.totalBlankLines = totalBlankLines;
    }

    public int getTotalCommentLines() {
        return totalCommentLines;
    }

    public void setTotalCommentLines(int totalCommentLines) {
        this.totalCommentLines = totalCommentLines;
    }

    public int getTotalCodeLines() {
        return totalCodeLines;
    }

    public void setTotalCodeLines(int totalCodeLines) {
        this.totalCodeLines = totalCodeLines;
    }

    @Override
    public String toString() {
        return "LOC{" +
                "countOfAllFiles=" + countOfAllFiles +
                ", countOfAllUniqueFiles=" + countOfAllUniqueFiles +
                ", totalBlankLines=" + totalBlankLines +
                ", totalCommentLines=" + totalCommentLines +
                ", totalCodeLines=" + totalCodeLines +
                '}';
    }
}
