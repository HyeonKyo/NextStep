package webserver;

public class HttpStatus {
    private static final String OK = "OK";
    private static final String FOUND = "Found";

    private String version;
    private int statusNo;
    private String status;

    public HttpStatus(String version) {
        this.version = version;
    }

    public void setStatus(int statusNo) {
        this.statusNo = statusNo;
        if (statusNo == 200) {
            this.status = OK;
        } else if (statusNo == 302) {
            this.status = FOUND;
        }
    }

    public String getVersion() {
        return version;
    }

    public int getStatusNo() {
        return statusNo;
    }

    public String getStatus() {
        return status;
    }
}
