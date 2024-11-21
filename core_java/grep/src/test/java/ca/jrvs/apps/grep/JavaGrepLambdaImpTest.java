package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class JavaGrepLambdaImpTest {
    enum OS {
        WINDOWS,
        LINUX
    }
    JavaGrepLambdaImp grep;
    OS os;

    @Before
    public void setUp() {
        this.grep = new JavaGrepLambdaImp();
        this.os = System.getProperty("os.name").toLowerCase().contains("windows") ? OS.WINDOWS : OS.LINUX;
        BasicConfigurator.configure();
    }

    @Test
    public void listFiles() {
        String rootPath = this.os == OS.WINDOWS ? "data\\" : "./data";
        grep.setRootPath(rootPath);
        Stream<File> tempFiles = grep.listFiles(grep.getRootPath());
        List<File> tempFilesArr = tempFiles.collect(Collectors.toList());
        assertTrue(tempFilesArr.size() != 0);
        assertEquals("shakespeare.txt", tempFilesArr.get(0).getName());
    }

    @Test
    public void readLines() {
        String filePath = this.os == OS.WINDOWS ? "data\\txt\\shakespeare.txt" : "./data/txt/shakespeare.txt";
        Stream<String> expected = Stream.of(
    "This is the 100th Etext file presented by Project Gutenberg, and",
            "is presented in cooperation with World Library, Inc., from their");
        Stream<String> actual = grep.readLines(new File(filePath));
        assertStreamEquals(expected, actual.limit(2));
    }

    @Test
    public void writeToFile() throws IOException {
        String outFile = this.os == OS.WINDOWS ? "out\\testFile.txt" : "./out/testFile.txt";
        grep.setOutFile(outFile);
        List<String> expected = new ArrayList<>();
        expected.add("this is");
        expected.add("a test file");
        grep.writeToFile(expected.stream());
        List<String> actual = grep.readLines(new File(grep.getOutFile())).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    public void testMain() {
        String[] inputs = new String[]{".*Romeo.*Juliet.*", "./data/txt", "./out/grep.out"};
        JavaGrepLambdaImp.main(inputs);
    }

    private static void assertStreamEquals(Stream<?> s1, Stream<?> s2) {
        Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
        while(iter1.hasNext() && iter2.hasNext())
            assertEquals(iter1.next(), iter2.next());
        assertTrue(!iter1.hasNext() && !iter2.hasNext());
    }
}