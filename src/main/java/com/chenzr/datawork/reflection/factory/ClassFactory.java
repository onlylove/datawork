package com.chenzr.datawork.reflection.factory;

import com.chenzr.datawork.reflection.ClassMetaInfo;
import javassist.CannotCompileException;

import java.util.Map;

public interface ClassFactory {

    Class<?> create(ClassMetaInfo classMetaInfo) throws CannotCompileException;

}
