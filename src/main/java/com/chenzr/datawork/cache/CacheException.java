package com.chenzr.datawork.cache;


import com.chenzr.datawork.exceptions.PersistenceException;

public class CacheException extends PersistenceException {

    private static final long serialVersionUID = -193202262468464650L;

    public CacheException() {
        super();
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }

}
