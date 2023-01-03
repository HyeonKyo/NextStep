package webserver;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpResponseHeader {
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";

    private Map<String, String> headers;

    public HttpResponseHeader() {
        headers = Maps.newHashMap();
    }

    public void setCookie(String key, String value) {
        String preCookie = headers.get(SET_COOKIE);
        headers.put(SET_COOKIE, String.format("%s, %s=%s", preCookie, key, value));
    }

    public void set302Location(String host, String uri) {
        set302Location(String.format("http://%s%s", host, uri));
    }

    public void set302Location(String location) {
        headers.put("Location", location);
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

    public void setContentType(String type) {
        headers.put(CONTENT_TYPE, type);
    }

    public void setContentLength(int length) {
        headers.put(CONTENT_LENGTH, String.valueOf(length));
    }
}
