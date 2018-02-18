package by.bsuir.iit.aipos.service;

public class FormatterUtil {
    public static final String BUNDLE_NAME = "httpPatterns";
    public static final String REQUEST_LINE = "httpPatterns.requestLine";

    public static final String URL_REGEXP = "^((http[s]?|ftp):\\/\\/)?\\/?([^\\/\\.]+\\.)*?([^\\/\\.]+\\.[^:\\/\\s\\.]{2,4}(\\.[^:\\/\\s\\.]{2,3})?(:\\d+)?)($|\\/)([^#?\\s]+)?(.*?)?(#[\\w\\-]+)?$";
    public static final int HOST_GROUP = 4;

    public static final String STATUS_LINE = "(HTTP\\/\\d\\.\\d)\\s+((\\d+)\\s+(.+))";
    public static final int PROTOCOL_TYPE = 1;
    public static final int STATUS_CODE = 3;
    public static final int STATUS_MESSAGE = 4;

    public static final String HEADER = "(.+):\\s+(.+)";
    public static final int HEADER_KEY = 1;
    public static final int HEADER_VALUE = 2;

    public static final String COOKIE = "(.*?)=(.*?)($|;|,(?! ))";
    public static final int COOKIE_NAME = 1;
    public static final int COOKIE_VALUE = 2;

    public static final String CRLF = "\r\n";

    private FormatterUtil() {}
}
