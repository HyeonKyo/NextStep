package webserver;

import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {
    public static final String CONTENT_LENGTH = "Content-Length";

    private Map<String, String> headers;

    public HttpRequestHeader() {
        headers = new HashMap<>();
    }

    public String get(String key) {
        return headers.get(key);
    }

    public void save(HttpRequestUtils.Pair pair) {
        headers.put(pair.getKey(), pair.getValue());
    }
}
