package util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.junit.jupiter.api.Test;
import util.HttpRequestUtils.Pair;
import webserver.HttpMethod;
import webserver.HttpRequestHeader;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {

    @Test
    void parseStartLine() {
        String line = "GET /user/create?userId=asd HTTP/1.1";

        RequestLine requestLine = HttpRequestUtils.parseRequestLine(line);
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/user/create");
        assertThat(requestLine.getParams().get("userId")).isEqualTo("asd");
        assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parse_startLine_not_queryString() {
        String line = "GET /user/index.html HTTP/1.1";

        RequestLine requestLine = HttpRequestUtils.parseRequestLine(line);
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/user/index.html");
        assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseQueryString() {
        String queryString = "userId=javajigi";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId")).isEqualTo("javajigi");
        assertThat(parameters.get("password")).isNull();

        queryString = "userId=javajigi&password=password2";
        parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId")).isEqualTo("javajigi");
        assertThat(parameters.get("password")).isEqualTo("password2");
    }

    @Test
    void parseQueryString_null() {
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(null);
        assertThat(parameters.isEmpty()).isTrue();

        parameters = HttpRequestUtils.parseQueryString("");
        assertThat(parameters.isEmpty()).isTrue();

        parameters = HttpRequestUtils.parseQueryString(" ");
        assertThat(parameters.isEmpty()).isTrue();
    }

    @Test
    void parseQueryString_invalid() {
        String queryString = "userId=javajigi&password";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
        assertThat(parameters.get("userId")).isEqualTo("javajigi");
        assertThat(parameters.get("password")).isNull();
    }

    @Test
    void parseCookies() {
        String cookies = "logined=true; JSessionId=1234";
        Map<String, String> parameters = HttpRequestUtils.parseCookies(cookies);
        assertThat(parameters.get("logined")).isEqualTo("true");
        assertThat(parameters.get("JSessionId")).isEqualTo("1234");
        assertThat(parameters.get("session")).isNull();
    }

    @Test
    void getKeyValue() throws Exception {
        Pair pair = HttpRequestUtils.getKeyValue("userId=javajigi", "=");
        assertThat(pair).isEqualTo(new Pair("userId", "javajigi"));
    }

    @Test
    void getKeyValue_invalid() throws Exception {
        Pair pair = HttpRequestUtils.getKeyValue("userId", "=");
        assertThat(pair).isNull();
    }

    @Test
    void parseHeader() throws Exception {
        String header = "Content-Length: 59";
        Pair pair = HttpRequestUtils.parseHeader(header);
        assertThat(pair).isEqualTo(new Pair("Content-Length", "59"));
    }

    @Test
    void parseHeaders() throws IOException {
        System.setIn(new ByteArrayInputStream("Host: localhost:8080\nContent-Length: 59\n\n".getBytes()));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        HttpRequestHeader httpRequestHeader = HttpRequestUtils.parseHeaders(br);

        assertThat(httpRequestHeader.get("Host")).isEqualTo("localhost:8080");
        assertThat(httpRequestHeader.get("Content-Length")).isEqualTo("59");
    }

    @Test
    void parseData() {
        String data = "userId=asd&password=asd&name=asd&email=asd@asd";
        Map<String, String> map = HttpRequestUtils.parseData(data);

        assertThat(map.get("userId")).isEqualTo("asd");
        assertThat(map.get("password")).isEqualTo("asd");
        assertThat(map.get("name")).isEqualTo("asd");
        assertThat(map.get("email")).isEqualTo("asd@asd");
    }
}
