package ca.jrvs.apps.grep;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
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

    public static void main(String[] args) throws IOException {
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
    public List<File> listFiles(String rootDir) throws IOException {
        List<File> files = new ArrayList<File>();
        try {
            Stream<Path> pathStream = Files.list(Paths.get(this.rootPath));
            files = pathStream.map(Path::toFile).filter(File::isFile).collect(Collectors.toList());
        } catch (IOException e) {
            this.logger.error("Error: Unable to list Files", e);
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) {
        return Collections.emptyList();
    }

    @Override
    public boolean containsPattern(String line) {
        pattern = Pattern.compile(this.regex);
        matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {

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
