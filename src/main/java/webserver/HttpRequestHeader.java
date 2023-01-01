package webserver;

import com.google.common.base.Strings;
import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequestHeader {
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String COOKIE = "Cookie";

    private Map<String, String> headers;

    public HttpRequestHeader() {
        headers = new HashMap<>();
    }

    public String get(String key) {
        return headers.get(key);
    }

    public String getCookie(String key) {
        String cookies = headers.get(COOKIE);
        if (!Strings.isNullOrEmpty(cookies)) {
            StringTokenizer st = new StringTokenizer(cookies, " ");
            while (st.hasMoreTokens()) {
                String[] cookie = st.nextToken().split("=");
                String cookieKey = cookie[0];
                if (cookieKey.equals(key)) {
                    return cookie[1];
                }
            }
        }
        return "";
    }

    public void save(HttpRequestUtils.Pair pair) {
        headers.put(pair.getKey(), pair.getValue());
    }
}
