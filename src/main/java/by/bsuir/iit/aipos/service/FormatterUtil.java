package by.bsuir.iit.aipos.service;

public class FormatterUtil {
    public static final String BUNDLE_NAME = "httpPatterns";
    public static final String REQUEST_LINE = "httpPatterns.requestLine";

    public static final String URL_REGEXP = "^((http[s]?|ftp):\\/\\/)?\\/?([^\\/\\.]+\\.)*?([^\\/\\.]+\\.[^:\\/\\s\\.]{2,4}(\\.[^:\\/\\s\\.]{2,3})?(:\\d+)?)($|\\/)([^#?\\s]+)?(.*?)?(#[\\w\\-]+)?$";
    public static final String HOST_REGEXP = "^(https?://)([^:^/]*)(:\\d*)?(.*)?$";

    public static final String STATUS_CODE_REGEXP = "(HTTP\\/\\d\\.\\d)\\s+((\\d+)\\s+(.+))";
    public static final int HOST_GROUP = 2;

    public static final String CRLF = "\r\n";

    private FormatterUtil() {}
}
