package ca.jrvs.apps.practice;

public interface RegexExc {

    /**
     * return true if filename extension is jpg or jpeg
     * @param filename
     * @return
     */
    public boolean matchJpeg(String filename);

    /**
     * return true if ip is valid
     * a valid ip is considered any IP address in the range 0.0.0.0 - 999.999.999.999
     * @param ip
     * @return
     */
    public boolean matchIP(String ip);

    /**
     * return true if line is empty
     * @param line
     * @return
     */
    public boolean isEmptyLine(String line);
}
