package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import util.StartLine;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DefaultURL = "/index.html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);

            StartLine startLine = HttpRequestUtils.parseStartLine(br.readLine());
            HttpHeader header = HttpRequestUtils.parseHeaders(br);
            String data = null;
            if ("POST".equals(startLine.getMethod())) {
                int contentLength = Integer.parseInt(header.get(HttpHeader.CONTENT_LENGTH));
                data = IOUtils.readData(br, contentLength);
                log.debug("HTTP Request Data = {}", data);
            }

            byte[] body = "Hello World".getBytes();
            if ("/user/create".equals(startLine.getUrl())) {
                Map<String, String> dataMap = HttpRequestUtils.parseData(data);
                User user = new User(dataMap.get("userId"), dataMap.get("password"), dataMap.get("name"), dataMap.get("email"));
                log.debug("Join user = {}", user);

                response302Header(dos, header.get("Host"));
                return;
            } else {
                String url = startLine.getUrl();
                if ("/".equals(url)) {
                    url = DefaultURL;
                }
                String path = String.format("./webapp%s", url);
                body = Files.readAllBytes(new File(path).toPath());
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String host) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes(String.format("Location: http://%s%s\r\n", host, DefaultURL));
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
