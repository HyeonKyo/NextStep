package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import util.RequestLine;

import java.io.*;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private RequestLine requestLine;
    private HttpRequestHeader requestHeader;
    private Map<String, String> parameters;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        requestLine = HttpRequestUtils.parseRequestLine(br.readLine());
        log.debug("{}", requestLine);

        requestHeader = HttpRequestUtils.parseHeaders(br);

        HttpMethod method = requestLine.getMethod();
        parameters = requestLine.getParams();
        if (method.isPost()) {
            int contentLength = Integer.parseInt(requestHeader.get(HttpRequestHeader.CONTENT_LENGTH));
            String data = IOUtils.readData(br, contentLength);
            log.debug("HTTP Request Data = {}", data);
            parameters = HttpRequestUtils.parseQueryString(data);
        }
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getVersion() {
        return requestLine.getVersion();
    }

    public String getCookie(String key) {
        return requestHeader.getCookie(key);
    }
}
