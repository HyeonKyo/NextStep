package webserver.controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public class DefaultController implements Controller{
    private static final String DEFAULT_URL = "/index.html";

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String url = request.getPath();
        if ("/".equals(url)) {
            url = DEFAULT_URL;
        }
        response.forward(url);
    }
}
