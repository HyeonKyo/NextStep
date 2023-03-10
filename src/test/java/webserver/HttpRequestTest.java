package webserver;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources";

    @Test
    void request_GET() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "/Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    void request_POST() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "/Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }
}
