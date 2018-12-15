package com.chenzr.datawork.reflection.factory;

import com.chenzr.datawork.reflection.ClassMetaInfo;
import javassist.*;

public class DefaultClassFactory implements ClassFactory {

    private static ClassPool pool = ClassPool.getDefault();

    @Override
    public Class<?> create(ClassMetaInfo classMetaInfo) throws CannotCompileException {
        CtClass ctClass = build(classMetaInfo);
        return ctClass.toClass();
    }

    private CtClass build(ClassMetaInfo classMetaInfo) throws CannotCompileException {
        CtClass ctClass = pool.makeClass(classMetaInfo.getId());
        //添加字段
        for (ClassMetaInfo.Field f : classMetaInfo.getFields()) {
            CtClass type = null;
            CtField field = null;
            if(ClassMetaInfo.FieldType.BASE ==f.getFieldType() || ClassMetaInfo.FieldType.ASSOCIATION ==f.getFieldType()){
                try {
                    type = pool.getCtClass(f.getType());
                    field = new CtField(type, f.getName(), ctClass);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
            if(ClassMetaInfo.FieldType.COLLECTION ==f.getFieldType()){
                try {
//                    type = pool.getCtClass(f.getType());
                    type = pool.getCtClass("java.util.List");
                    field = new CtField(type, f.getName(), ctClass);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
            field.setModifiers(Modifier.PRIVATE);
            ctClass.addField(field);

            //添加getXXX和setXXX方法
            ctClass.addMethod(CtNewMethod.getter("get" + getFirstCharUpper(f.getName()), field));
            ctClass.addMethod(CtNewMethod.setter("set" + getFirstCharUpper(f.getName()), field));
        }
        return ctClass;
    }


    public String getFirstCharUpper(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String s = str.substring(0,1).toUpperCase()+str.substring(1);
        return s;
    }
}

