package webserver;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String DefaultURL = "/index.html";
    private static final String NEWLINE = "\n";

    private DataOutputStream dos;
    private HttpStatus httpStatus;
    private HttpResponseHeader header;
    private byte[] body;

    public HttpResponse(OutputStream outputStream) {
        dos = new DataOutputStream(outputStream);
        httpStatus = new HttpStatus();
        header = new HttpResponseHeader();
        body = new byte[0];
    }

    public void forward(String filePath) throws IOException {
        if (Strings.isNullOrEmpty(filePath)) {
            return;
        }
        String path = String.format("./webapp%s", filePath);
        body = Files.readAllBytes(new File(path).toPath());
        httpStatus.setStatus(200);
        String type = "text/html;charset=utf-8";
        if (filePath.endsWith("css")) {
            type = "text/css";
        } else if (filePath.endsWith("js")) {
            type = "application/javascript";
        }
        header.setContentType(type);
        header.setContentLength(body.length);

        sendResponse();
    }

    public void forwardBody(String data) {
        body = data.getBytes();
        header.setContentType("text/html;charset=utf-8");
        header.setContentLength(body.length);

        sendResponse();
    }

    public void sendRedirect(String locationUri) {
        httpStatus.setStatus(302);
        header.set302Location(locationUri);

        sendResponse();
    }

    private void sendResponse() {
        try {
            String headerMessage = makeResponseHeaderMessage();
            dos.writeBytes(headerMessage);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String makeResponseHeaderMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(httpStatus.getVersion()).append(" ")
                .append(httpStatus.getStatusNo()).append(" ")
                .append(httpStatus.getStatus()).append(NEWLINE);
        sb.append("Content-Length: ").append(body.length).append(NEWLINE);
        sb.append(header.getHeaders()).append(NEWLINE);
        log.debug("Response Header = {}", sb);
        return sb.toString();
    }

    public void setCookie(String key, String value) {
        header.setCookie(key, value);
    }
}
