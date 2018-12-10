package com.chenzr.datawork.executor;

import java.util.List;

public interface Executor {

    int update();

    <E> List<E> query();
}
