package by.bsuir.iit.aipos;

import java.util.Map;
import java.util.HashMap;

public class SenderModel {
    private Map<String, String> headerMap = new HashMap<>();

    public void addHeader(String key, String value) {
        headerMap.put(key, value);
    }

    public void removeHeader(String key) {
        headerMap.remove(key);
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }
}
