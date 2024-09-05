package ca.jrvs.apps.practice;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexExcImp implements RegexExc{

    private Pattern pattern;
    private Matcher matcher;

    /**
     * return true if filename extension is jpg or jpeg
     * @param filename
     * @return
     */
    @Override
    public boolean matchJpeg(String filename) {
        pattern = Pattern.compile(".+(\\.(jpg|jpeg))$");
        matcher = pattern.matcher(filename);
        return matcher.find();

    }

    /**
     * return true if ip is valid
     * a valid ip is considered any IP address in the range 0.0.0.0 - 999.999.999.999
     * @param ip
     * @return
     */
    @Override
    public boolean matchIP(String ip) {
        pattern = Pattern.compile("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$");
        matcher = pattern.matcher(ip);
        return matcher.find();
    }

    /**
     * return true if line is empty
     * @param line
     * @return
     */
    @Override
    public boolean isEmptyLine(String line) {
        pattern = Pattern.compile("^\\s*$");
        matcher = pattern.matcher(line);
        return matcher.find();
    }
}
