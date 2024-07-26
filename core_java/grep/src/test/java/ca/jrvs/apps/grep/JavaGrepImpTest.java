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

    JavaGrepImp grep;

    @Before
    public void setUp() {
        this.grep = new JavaGrepImp();
        BasicConfigurator.configure();
    }

    @Test
    public void listFilesDataTxt() throws IOException {
        grep.setRootPath("data\\txt");
        List<File> tempFiles = grep.listFiles(grep.getOutFile());
        assertTrue(!tempFiles.isEmpty());
        assertEquals("shakespeare.txt", tempFiles.get(0).getName());
    }

    @Test
    public void listFilesRoot() throws IOException {
        grep.setRootPath("");
        List<File> expected = new ArrayList<>();
        expected.add(new File("pom.xml"));
        expected.add(new File("README.md"));
        List<File> actual = grep.listFiles(grep.getOutFile());
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void readLines() {
        grep.setRootPath("data\\txt");
        List<String> expected = new ArrayList<>();
        expected.add("This is the 100th Etext file presented by Project Gutenberg, and");
        expected.add("is presented in cooperation with World Library, Inc., from their");
        List<String> actual = grep.readLines(new File("data\\txt\\shakespeare.txt"));
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
        grep.setOutFile("testFile.txt");
        List<String> expected = new ArrayList<>();
        expected.add("this is");
        expected.add("a test file");
        grep.writeToFile(expected);
        List<String> actual = grep.readLines(new File("out\\testFile.txt"));
        assertEquals(expected, actual);
    }
}