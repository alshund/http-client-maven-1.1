package by.bsuir.iit.aipos.service;

import by.bsuir.iit.aipos.domain.HTTPRequest;
import by.bsuir.iit.aipos.domain.HTTPResponse;
import by.bsuir.iit.aipos.exception.BadRequestException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtocolFormatter {
    private ResourceBundle bundle = ResourceBundle.getBundle(FormatterUtil.BUNDLE_NAME);

    public String toMessageFormat(HTTPRequest httpRequest) throws BadRequestException {

        if (isUrlValid(httpRequest.getUrl())) {
            String requestLine = prepareRequestLine(httpRequest.getMethod(), httpRequest.getUrl());
            String headerField = prepareRequestHeader(httpRequest.getHeaderMap());
            String entityBody = httpRequest.getEntityBody();
            return requestLine + headerField + FormatterUtil.CRLF + entityBody;
        } else {
            throw new BadRequestException("Invalid url: " + httpRequest.getUrl());
        }
    }

    public String toMessageFormat(HTTPResponse httpResponse) {

        String responseStatus = prepareResponseStatus(httpResponse);
        String headerFiled = prepareResponseHeader(httpResponse.getHeaderMap());
        String cookie = prepareCookie(httpResponse);
        return responseStatus + headerFiled + cookie + FormatterUtil.CRLF;
    }

    private String prepareCookie(HTTPResponse httpResponse) {

        List<String> cookieList = httpResponse.getCookieList();
        StringBuilder stringBuilder = new StringBuilder();
        for (String cookie: cookieList) {
            stringBuilder.append(cookie).append(FormatterUtil.CRLF);
        }
        return stringBuilder.toString();
    }

    private String prepareResponseStatus(HTTPResponse httpResponse) {

        return httpResponse.getProtocolType() + " " + httpResponse.getStatusCode() + " " +
               httpResponse.getStatusMessage() + FormatterUtil.CRLF;
    }

    public String findHost(String url) throws BadRequestException {

        Matcher hostUrlMatcher = getMatcher(FormatterUtil.URL_REGEXP, url);
        if (hostUrlMatcher.find()) {
            return hostUrlMatcher.group(FormatterUtil.HOST_GROUP);
        } else {
            throw new BadRequestException("Invalid url: " + url);
        }
    }

    private String prepareRequestLine(String method, String url) {

        Formatter requestLine = new Formatter();
        requestLine.format(bundle.getString(FormatterUtil.REQUEST_LINE), method, url);
        return requestLine.toString() + FormatterUtil.CRLF;
    }

    private String prepareRequestHeader(Map<String, String> headerMap) {

        Formatter headerFormatter = new Formatter();
        Set<String> headerKeys = headerMap.keySet();
        for (String header : headerKeys) {
            headerFormatter.format(getHeaderCode(header) + FormatterUtil.CRLF, headerMap.get(header));
        }
        return headerFormatter.toString();
    }

    private String prepareResponseHeader(Map<String, String> headerMap) {

        Set<String> headerKeys = headerMap.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (String header : headerKeys) {
            stringBuilder.append(header).append(": ").append(headerMap.get(header)).append(FormatterUtil.CRLF);
        }
        return stringBuilder.toString();
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
