package com.chenzr.datawork.executor.result;


import com.chenzr.datawork.session.ResultContext;

public interface ResultHandler<T> {

    void handleResult(ResultContext<? extends T> resultContext);

}
