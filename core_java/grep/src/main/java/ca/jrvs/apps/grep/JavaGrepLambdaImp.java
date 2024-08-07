package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class JavaGrepLambdaImp implements JavaGrepLambda{

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();
        JavaGrepLambdaImp grep = new JavaGrepLambdaImp();

        grep.setRegex(args[0]);
        grep.setRootPath(args[1]);
        grep.setOutFile(args[2]);

        try{
            grep.process();
        } catch (Exception e) {
            grep.logger.error("Error: Unable to process", e);
        }
    }

    /**
     * Top level search workflow
     *
     * @throws IOException
     */
    @Override
    public void process() throws IOException {
        Stream<String> matchedLines = listFiles(this.getRootPath())
                .flatMap(this::readLines)
                .filter(this::containsPattern);
        writeToFile(matchedLines);
    }

    @Override
    public Stream<File> listFiles(String rootDir) {
        try {
            return Files.list(Paths.get(rootDir)).map(Path::toFile).filter(File::isFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<String> readLines(File inputFile) {
        try {
            return Files.lines(inputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if a line contains the regex pattern (passed by user)
     * @param line input string
     * @return true if there is a match
     */
    @Override
    public boolean containsPattern(String line) {
        Pattern pattern = Pattern.compile(this.regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    /**
     * Write lines to a file
     * @param lines lines to write
     * @throws IOException if write failed
     */
    @Override
    public void writeToFile(Stream<String> lines) throws IOException {
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter("out\\" + this.outFile));
        lines.forEach(line -> {
            try {
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {

            }
        });
        writer.close();
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

//    @Override
//    public void writeToFile(List<String> lines) throws IOException {
//        BufferedWriter writer;
//        writer = new BufferedWriter(new FileWriter("out\\" + this.outFile));
//        lines.stream().forEach(str -> {
//            try {
//                writer.write(str);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        writer.close();
//    }

