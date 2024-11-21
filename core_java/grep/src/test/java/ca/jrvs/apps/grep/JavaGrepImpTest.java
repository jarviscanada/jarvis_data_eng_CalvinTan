package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JavaGrepImpTest {
    enum OS {
        WINDOWS,
        LINUX
    }
    JavaGrepImp grep;
    OS os;

    @Before
    public void setUp() {
        this.grep = new JavaGrepImp();
        this.os = System.getProperty("os.name").toLowerCase().contains("windows") ? OS.WINDOWS : OS.LINUX;
        BasicConfigurator.configure();
    }

    @Test
    public void listFilesDataTxt() throws IOException {
        String rootPath = this.os == OS.WINDOWS ? "data\\" : "./data";
        grep.setRootPath(rootPath);
        List<File> tempFiles = grep.listFiles(grep.getRootPath());
        assertTrue(!tempFiles.isEmpty());
        assertEquals("shakespeare.txt", tempFiles.get(0).getName());
    }

    @Test
    public void readLines() {
        String filePath = this.os == OS.WINDOWS ? "data\\txt\\shakespeare.txt" : "./data/txt/shakespeare.txt";
        List<String> expected = new ArrayList<>();
        expected.add("This is the 100th Etext file presented by Project Gutenberg, and");
        expected.add("is presented in cooperation with World Library, Inc., from their");
        List<String> actual = grep.readLines(new File(filePath));
        actual = actual.subList(0,2);
        assertEquals(expected, actual);
    }

    @Test
    public void containsPattern() {
        // test case pulled from regexone lesson 2
        grep.setRegex("...\\.");
        List<String> lines = new ArrayList<>();
        lines.add("cat.");
        lines.add("896.");
        lines.add("?=+.");
        lines.add("abc1");
        List<String> expected = lines.subList(0,3);
        List<String> actual = new ArrayList<>();
        for (String line : lines) {
            if (grep.containsPattern(line)) actual.add(line);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile() throws IOException {
        String outFile = this.os == OS.WINDOWS ? "out\\testFile.txt" : "./out/testFile.txt";
        grep.setOutFile(outFile);
        List<String> expected = new ArrayList<>();
        expected.add("this is");
        expected.add("a test file");
        grep.writeToFile(expected);
        List<String> actual = grep.readLines(new File(grep.getOutFile()));
        assertEquals(expected, actual);
    }
}