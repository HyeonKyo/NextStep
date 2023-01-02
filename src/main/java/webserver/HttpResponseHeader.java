package webserver;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpResponseHeader {
    public static final String SET_COOKIE = "Set-Cookie";

    private Map<String, String> headers;

    public HttpResponseHeader() {
        headers = Maps.newHashMap();
    }

    public void addCookie(String key, Object value) {
        headers.put(SET_COOKIE, String.format("%s=%s", key, value.toString()));
    }

    public void set302Location(String host, String uri) {
        headers.put("Location", String.format("http://%s%s", host, uri));
    }

    public String getHeaders() {
        StringBuilder sb = new StringBuilder();
        headers.entrySet().stream()
                .forEach(entry -> sb.append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append("\n"));
        return sb.toString();
    }

    public void set(String key, String value) {
        headers.put(key, value);
    }
}
