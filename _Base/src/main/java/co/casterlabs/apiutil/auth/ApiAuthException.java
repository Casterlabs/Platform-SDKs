package co.casterlabs.apiutil.auth;

import co.casterlabs.apiutil.web.ApiException;

public class ApiAuthException extends ApiException {
    private static final long serialVersionUID = 4659183660754993391L;

    public ApiAuthException(String message, Exception reason) {
        super(message, reason);
    }

    public ApiAuthException(String message) {
        super(message);
    }

    public ApiAuthException(Exception reason) {
        super(reason);
    }

}
