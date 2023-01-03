package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import util.RequestLine;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DefaultURL = "/index.html";
    private static final String NEWLINE = "\r\n";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);

            HttpStatus httpStatus = new HttpStatus(request.getVersion());
            HttpResponseHeader responseHeader = new HttpResponseHeader();
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = new byte[0];
            if ("/user/create".equals(request.getPath())) {
                User user = new User(request.getParameter("userId"),
                        request.getParameter("password"),
                        request.getParameter("name"),
                        request.getParameter("email"));
                log.debug("Join user = {}", user);
                DataBase.addUser(user);

                httpStatus.setStatus(302);
                responseHeader.set302Location(request.getHeader("Host"), DefaultURL);
            } else if ("/user/login".equals(request.getPath())) {
                String uri = DefaultURL;
                boolean isSuccess = true;
                User user = DataBase.findUserById(request.getParameter("userId"));
                if (user == null) {
                    isSuccess = false;
                    uri = "/user/login_failed.html";
                }
                httpStatus.setStatus(302);
                responseHeader.set302Location(request.getHeader("Host"), uri);
                responseHeader.addCookie("logined", isSuccess);
            } else if ("/user/list".equals(request.getPath())) {
                String uri = "/user/login";
                boolean logined = Boolean.parseBoolean(request.getCookie("logined"));
                log.debug("Is Login = {}", logined);
                if (!logined) {
                    httpStatus.setStatus(302);
                    responseHeader.set302Location(request.getHeader("Host"), uri);
                } else {
                    httpStatus.setStatus(200);
                    String path = "./webapp/user/list.html";
                    body = Files.readAllBytes(new File(path).toPath());
                }
            } else {
                String url = request.getPath();
                if ("/".equals(url)) {
                    url = DefaultURL;
                }
                String path = String.format("./webapp%s", url);
                body = Files.readAllBytes(new File(path).toPath());
                httpStatus.setStatus(200);
                String accept = request.getHeader("Accept");
                if (url.contains("css") && accept.contains("text/css")) {
                    responseHeader.set("Content-Type", "text/css");
                }
            }
            response(dos, httpStatus, responseHeader, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response(DataOutputStream dos, HttpStatus status, HttpResponseHeader header, byte[] body) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(status.getVersion()).append(" ")
                            .append(status.getStatusNo()).append(" ")
                            .append(status.getStatus()).append(NEWLINE);
            sb.append("Content-Length: ").append(body.length).append(NEWLINE);
            sb.append(header.getHeaders()).append(NEWLINE);
            log.debug("Response Header = {}", sb);
            dos.writeBytes(sb.toString());
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
