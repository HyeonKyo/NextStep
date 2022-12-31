package util;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class HttpRequestUtils {
    public static final String METHOD = "method";
    public static final String URL = "url";
    public static final String VERSION = "version";

    private static final Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);

    public static StartLine parseStartLine(String startLine) {
        if (startLine == null || startLine.isBlank()) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(startLine, " ");
        String method = st.nextToken();
        String url = st.nextToken();
        String[] splitUrl = url.split("\\?");
        String uri = splitUrl[0];
        String queryString = null;
        if (splitUrl.length >= 2) {
            queryString = splitUrl[1];
        }
        String version = st.nextToken();
        log.debug("method = {}, url = {}, queryString = {}, version = {}", method, uri, queryString, version);
        return new StartLine(method, uri, queryString, version);
    }

    /**
     * queryString은 URL에서 ? 이후에 전달되는 field1=value1&field2=value2 형식임
     */
    public static Map<String, String> parseQueryString(String queryString) {
        return parseValues(queryString, "&");
    }

    /**
     * 쿠키값은 name1=value1; name2=value2 형식임
     */
    public static Map<String, String> parseCookies(String cookies) {
        return parseValues(cookies, ";");
    }

    private static Map<String, String> parseValues(String values, String separator) {
        if (Strings.isNullOrEmpty(values)) {
            return Maps.newHashMap();
        }

        String[] tokens = values.split(separator);
        return Arrays.stream(tokens).map(t -> getKeyValue(t, "=")).filter(Objects::nonNull)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    static Pair getKeyValue(String keyValue, String regex) {
        if (Strings.isNullOrEmpty(keyValue)) {
            return null;
        }

        String[] tokens = keyValue.split(regex);
        if (tokens.length != 2) {
            return null;
        }

        return new Pair(tokens[0], tokens[1]);
    }

    public static Pair parseHeader(String header) {
        return getKeyValue(header, ": ");
    }

    public static class Pair {
        String key;
        String value;

        Pair(String key, String value) {
            this.key = key.trim();
            this.value = value.trim();
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Pair other = (Pair) obj;
            if (key == null) {
                if (other.key != null)
                    return false;
            } else if (!key.equals(other.key))
                return false;
            if (value == null) {
                return other.value == null;
            } else return value.equals(other.value);
        }

        @Override
        public String toString() {
            return "Pair [key=" + key + ", value=" + value + "]";
        }
    }
}
