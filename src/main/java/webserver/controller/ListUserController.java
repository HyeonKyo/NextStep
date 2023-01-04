package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public class ListUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
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
}
