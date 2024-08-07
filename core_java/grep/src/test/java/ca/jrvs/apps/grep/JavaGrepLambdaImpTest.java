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

    JavaGrepLambdaImp grep;

    @Before
    public void setUp() {
        this.grep = new JavaGrepLambdaImp();
        BasicConfigurator.configure();
    }

    @Test
    public void listFiles() {
        grep.setRootPath("data\\txt");
        Stream<File> tempFiles = grep.listFiles(grep.getRootPath());
        List<File> tempFilesArr = tempFiles.collect(Collectors.toList());
        assertTrue(tempFilesArr.size() != 0);
        assertEquals("shakespeare.txt", tempFilesArr.get(0));
    }

    @Test
    public void readLines() {
        grep.setRootPath("data\\txt");
        Stream<String> expected = Stream.of(
    "This is the 100th Etext file presented by Project Gutenberg, and",
            "is presented in cooperation with World Library, Inc., from their");
        Stream<String> actual = grep.readLines(new File("data\\txt\\shakespeare.txt"));
        assertStreamEquals(expected, actual);
    }

    @Test
    public void writeToFile() throws IOException {
        grep.setOutFile("testFile.txt");
        List<String> expected = new ArrayList<>();
        expected.add("this is");
        expected.add("a test file");
        grep.writeToFile(expected.stream());
        List<String> actual = grep.readLines(new File("out\\testFile.txt")).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    public void testMain() {
        String[] inputs = new String[]{".*Romeo.*Juliet.*", "data/txt", "out/grep.out"};
        JavaGrepLambdaImp.main(inputs);
    }

    private static void assertStreamEquals(Stream<?> s1, Stream<?> s2) {
        Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
        while(iter1.hasNext() && iter2.hasNext())
            assertEquals(iter1.next(), iter2.next());
        assertTrue(!iter1.hasNext() && !iter2.hasNext());
    }
}