package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class LoginUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(LoginUserController.class);
    private static final String DEFAULT_URL = "/index.html";

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String uri = DEFAULT_URL;
        boolean isSuccess = true;
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (user == null) {
            isSuccess = false;
            uri = "/user/login_failed.html";
        }
        log.debug("Login user = {}", user);
        response.setCookie("logined", String.valueOf(isSuccess));
        response.sendRedirect(uri);
    }
}
