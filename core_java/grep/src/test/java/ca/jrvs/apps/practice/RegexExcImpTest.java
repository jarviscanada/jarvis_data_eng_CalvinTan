package ca.jrvs.apps.practice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RegexExcImpTest {

    RegexExcImp regex;

    @Before
    public void setUp(){
        regex = new RegexExcImp();
    }

    @Test
    public void matchJpeg1() {
        String filename = ".jpg";
        boolean result = regex.matchJpeg(filename);
        Assert.assertFalse(result);
    }

    @Test
    public void matchJpeg2() {
        String filename = ".jpg";
        boolean result = regex.matchJpeg(filename);
        Assert.assertFalse(result);
    }

    @Test
    public void matchJpeg3() {
        String filename = "a.jpg";
        boolean result = regex.matchJpeg(filename);
        Assert.assertTrue(result);
    }

    @Test
    public void matchIP1() {
        String ip = "0.0.0.0";
        boolean result = regex.matchIP(ip);
        Assert.assertTrue(result);
    }

    @Test
    public void matchIP2() {
        String ip = "0.1.22.333";
        boolean result = regex.matchIP(ip);
        Assert.assertTrue(result);
    }

    @Test
    public void matchIP3() {
        String ip = "0.0.0.0.0";
        boolean result = regex.matchIP(ip);
        Assert.assertFalse(result);
    }

    @Test
    public void matchIP4() {
        String ip = "0.0.0";
        boolean result = regex.matchIP(ip);
        Assert.assertFalse(result);
    }
    @Test
    public void matchIP5() {
        String ip = "0000.0.0.0";
        boolean result = regex.matchIP(ip);
        Assert.assertFalse(result);
    }

    @Test
    public void isEmptyLine1() {
        String line = "";
        boolean result = regex.isEmptyLine(line);
        Assert.assertTrue(result);
    }

    @Test
    public void isEmptyLine2() {
        String line = " ";
        boolean result = regex.isEmptyLine(line);
        Assert.assertTrue(result);
    }

    @Test
    public void isEmptyLine3() {
        String line = " ";
        boolean result = regex.isEmptyLine(line);
        Assert.assertTrue(result);
    }

    @Test
    public void isEmptyLine4() {
        String line = " a";
        boolean result = regex.isEmptyLine(line);
        Assert.assertFalse(result);
    }
}