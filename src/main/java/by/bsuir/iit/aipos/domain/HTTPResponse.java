package by.bsuir.iit.aipos.domain;

import by.bsuir.iit.aipos.service.FormatterUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class HTTPResponse implements Serializable {

    private static final long serialVersionUID = 78438895578939905L;

    private String protocolType;
    private String statusMessage;
    private String statusCode;
    private Map<String, String> headerMap = new HashMap<>();
    private List<String> cookieList = new ArrayList<>();

    private StringBuilder entityBody = new StringBuilder();

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void addHeader(String key, String value) {
        headerMap.put(key, value);
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getEntityBody() {
        return entityBody.toString();
    }

    public void appendEntityBody(String entityBodyLine) {
        this.entityBody.append(entityBodyLine + FormatterUtil.CRLF);
    }

    public List<String> getCookieList() {
        return cookieList;
    }

    public void addCookie(String cookie) {
        cookieList.add(cookie);
    }
}
