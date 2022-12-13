package ru.netology.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class GoneException extends RuntimeException {

    public GoneException() {
    }

    public GoneException(String message) {
        super(message);
    }

    public GoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoneException(Throwable cause) {
        super(cause);
    }

    public GoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
