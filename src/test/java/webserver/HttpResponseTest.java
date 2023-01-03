package webserver;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void response_forward() throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        HttpResponse httpResponse = new HttpResponse(out);
        httpResponse.forward("/index.html");

        String result = out.toString();
        assertThat(result).contains("HTTP/1.1 200 OK");
        assertThat(result).contains("<!DOCTYPE html>");
    }

    @Test
    void response_redirect() {
        OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        HttpResponse httpResponse = new HttpResponse(out);
        httpResponse.sendRedirect("/index.html");

        assertThat(out.toString()).contains("HTTP/1.1 302 Found");
        assertThat(out.toString()).contains("Location: /index.html");
    }

}
