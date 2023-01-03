package webserver;

public enum HttpMethod {
    GET, POST,
    ;

    public boolean isPost() {
        return POST.equals(this);
    }
}
