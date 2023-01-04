package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_URL = "/index.html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            if ("/user/create".equals(request.getPath())) {
                createUser(request, response);
            } else if ("/user/login".equals(request.getPath())) {
                loginUser(request, response);
            } else if ("/user/list".equals(request.getPath())) {
                listUser(request, response);
            } else {
                defaultForward(request, response);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void defaultForward(HttpRequest request, HttpResponse response) throws IOException {
        String url = request.getPath();
        if ("/".equals(url)) {
            url = DEFAULT_URL;
        }
        response.forward(url);
    }

    private void listUser(HttpRequest request, HttpResponse response) throws IOException {
        String uri = "/user/login";
        boolean logined = Boolean.parseBoolean(request.getCookie("logined"));
        log.debug("Is Login = {}", logined);
        if (!logined) {
            response.sendRedirect(uri);
        } else {
            String path = "/user/list.html";
            response.forward(path);
        }
    }

    private void loginUser(HttpRequest request, HttpResponse response) {
        String uri = DEFAULT_URL;
        boolean isSuccess = true;
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (user == null) {
            isSuccess = false;
            uri = "/user/login_failed.html";
        }
        response.setCookie("logined", String.valueOf(isSuccess));
        response.sendRedirect(uri);
    }

    private void createUser(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        log.debug("Join user = {}", user);
        DataBase.addUser(user);

        response.sendRedirect(DEFAULT_URL);
    }
}
