package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    private static final String DEFAULT_URL = "/index.html";

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        log.debug("Join user = {}", user);
        DataBase.addUser(user);

        response.sendRedirect(DEFAULT_URL);
    }
}
