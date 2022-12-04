package co.casterlabs.apiutil.web;

public class ApiException extends Exception {
    private static final long serialVersionUID = -6064238003046069345L;

    public ApiException(String message, Exception reason) {
        super(message, reason);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Exception reason) {
        super(reason);
    }

}
