package ca.jrvs.apps.grep;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    private Pattern pattern;
    private Matcher matcher;

    public static void main(String[] args) {
        if (args.length != 3) {
            //throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();
        JavaGrepImp grep = new JavaGrepImp();

        grep.setRegex(args[0]);
        grep.setRootPath(args[1]);
        grep.setOutFile(args[2]);

        try{
            grep.process();
        } catch (Exception e) {
            grep.logger.error("Error: Unable to process", e);
        }
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<String>();
        for (File file : listFiles(this.rootPath)){
            for (String line : readLines(file)) {
                if (containsPattern(line)) matchedLines.add(line);
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> files = new ArrayList<>();
        try {
            Stream<Path> pathStream = Files.list(Paths.get(this.rootPath));
            files = pathStream.map(Path::toFile).filter(File::isFile).collect(Collectors.toList());
        } catch (IOException e) {
            this.logger.error("Error: Unable to list files", e);
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            this.logger.error("Error: File not found", e);
        } catch (IOException e) {
            this.logger.error("Error: IOException", e);
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        pattern = Pattern.compile(this.regex);
        matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("out\\test.txt"));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            this.logger.error("Error: Failed to write to file", e);
        }
    }

    @Override
    public String getRootPath() {
        return this.rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return this.outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
