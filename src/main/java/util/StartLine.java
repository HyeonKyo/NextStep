package util;

public class StartLine {
    private String method;
    private String url;
    private String queryString;
    private String version;

    public StartLine(String method, String url, String queryString, String version) {
        this.method = method;
        this.url = url;
        this.queryString = queryString;
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getVersion() {
        return version;
    }
}
