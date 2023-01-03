package util;

import webserver.HttpMethod;

import java.util.Map;

public class RequestLine {
    private HttpMethod method;
    private String path;
    private String version;
    private Map<String, String> params;

    public RequestLine(String method, String path, String version, Map<String, String> params) {
        this.method = HttpMethod.valueOf(method);
        this.path = path;
        this.version = version;
        this.params = params;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
