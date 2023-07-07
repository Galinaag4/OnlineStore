package com.example.onlinestore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class NotFoundUserNameException extends Exception {
    public NotFoundUserNameException(String format) {
    }
}
