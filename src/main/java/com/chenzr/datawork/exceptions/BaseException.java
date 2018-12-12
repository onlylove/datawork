package com.chenzr.datawork.exceptions;

public class BaseException extends RuntimeException {


    private static final long serialVersionUID = 3880206998166270511L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }


}
