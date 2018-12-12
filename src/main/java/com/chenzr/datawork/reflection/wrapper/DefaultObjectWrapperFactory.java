package com.chenzr.datawork.reflection.wrapper;


import com.chenzr.datawork.reflection.MetaObject;
import com.chenzr.datawork.reflection.ReflectionException;

public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }

}
