package webserver;

import webserver.controller.*;

import java.util.Map;

public class RequestMapping {

    private static Map<String, Controller> controllers;
    private static final Controller defaultController = new DefaultController();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginUserController());
        controllers.put("/user/list", new ListUserController());
    }

    public static Controller getController(String requestUrl) {
        Controller controller = controllers.get(requestUrl);
        if (controller == null) {
            controller = defaultController;
        }
        return controller;
    }

}
