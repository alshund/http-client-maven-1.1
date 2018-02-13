package by.bsuir.iit.aipos.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class HTTPRequest implements Serializable {
    private static final long serialVersionUID = 7802723844148051352L;

    private String method;
    private String url;
    private Map<String, String> headerMap = new HashMap<>();

    public HTTPRequest(String method, String url, Map<String, String> headerMap) {
        this.method = method;
        this.url = url;
        this.headerMap = headerMap;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }
}
