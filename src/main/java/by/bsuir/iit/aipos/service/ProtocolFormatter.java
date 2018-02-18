package by.bsuir.iit.aipos.service;

import by.bsuir.iit.aipos.domain.HTTPRequest;
import by.bsuir.iit.aipos.domain.http_parameters.ResponseStatus;
import by.bsuir.iit.aipos.exception.BadRequestException;
import by.bsuir.iit.aipos.exception.BadResponseException;

import java.util.Formatter;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtocolFormatter {
    private ResourceBundle bundle = ResourceBundle.getBundle(FormatterUtil.BUNDLE_NAME);

    public String toMessageFormat(HTTPRequest httpRequest) throws BadRequestException {

        if (isUrlValid(httpRequest.getUrl())) {
            String requestLine = prepareRequestLine(httpRequest.getMethod(), httpRequest.getUrl());
            String headerField = prepareHeaderField(httpRequest.getHeaderMap());
            String entityBody = httpRequest.getEntityBody();
            return requestLine + headerField + FormatterUtil.CRLF + entityBody;
        } else {
            throw new BadRequestException("Invalid url: " + httpRequest.getUrl());
        }
    }

    public String findHost(String url) throws BadRequestException {

        Matcher hostUrlMatcher = getMatcher(FormatterUtil.HOST_REGEXP, url);
        if (hostUrlMatcher.find()) {
            return hostUrlMatcher.group(FormatterUtil.HOST_GROUP);
        } else {
            throw new BadRequestException("Invalid url: " + url);
        }
    }

    public ResponseStatus getStatusCode(String response) throws BadResponseException {

        Matcher responseStatusMatcher = getMatcher(FormatterUtil.STATUS_CODE_REGEXP, response);
        if (responseStatusMatcher.find()) {
            String statusName = responseStatusMatcher.group(ResponseStatus.getStatusName());
            ResponseStatus responseStatus = ResponseStatus.valueOf(toEnumFormat(statusName));
            responseStatus.setMessage(responseStatusMatcher.group(ResponseStatus.getStatusMessage()));
            return responseStatus;
        } else {
            throw new BadResponseException("Invalid response: "  + response);
        }
    }

    private String toEnumFormat(String statusName) {

        return statusName.toUpperCase().replace(" ", "_");
    }

    private String prepareRequestLine(String method, String url) {

        Formatter requestLine = new Formatter();
        requestLine.format(bundle.getString(FormatterUtil.REQUEST_LINE), method, url);
        return requestLine.toString() + FormatterUtil.CRLF;
    }

    private String prepareHeaderField(Map<String, String> headerMap) {

        Formatter headerFormatter = new Formatter();
        Set<String> headerKeys = headerMap.keySet();
        for (String header : headerKeys) {
            headerFormatter.format(getHeaderCode(header) + FormatterUtil.CRLF, headerMap.get(header));
        }
        return headerFormatter.toString();
    }

    private String getHeaderCode(String key) {

        return bundle.getString(bundle.getBaseBundleName() + "." + key);
    }

    private boolean isUrlValid(String url) {

        Matcher regexpMatcher = getMatcher(FormatterUtil.URL_REGEXP, url);
        return regexpMatcher.matches();
    }

    private Matcher getMatcher(String regexp, String string) {

        Pattern regexpPattern = Pattern.compile(regexp);
        return regexpPattern.matcher(string);
    }
}
