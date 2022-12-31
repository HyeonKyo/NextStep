package webserver;

import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    public static final String CONTENT_LENGTH = "Content-Length";

    private Map<String, String> headers;

    public HttpHeader() {
        headers = new HashMap<>();
    }

    public String get(String key) {
        return headers.get(key);
    }

    public void save(HttpRequestUtils.Pair pair) {
        headers.put(pair.getKey(), pair.getValue());
    }
}
