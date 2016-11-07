package io.f2xy.app.utils;

import io.f2xy.app.utils.interfaces.Getter;

import javax.annotation.Nullable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Java Reflection işlemleri için fonksiyonlar
 *
 * Date: 3/7/12
 *
 * @author Hakan GÜR <hakangur82@gmail.com>
 * @version 1.0
 */
public class ReflectionBase {

    /**
     * Paremetre olarak gelen sınıftaki set ile başlayan fonksiyonların listesini oluşturur.
     *
     * @param c
     * @return
     */
    public static List<Method> getSetterMethods(Class<?> c) {
        return getMethods(c, "set.*");
    }

    /**
     * Paremetre olarak gelen sınıftaki get ile başlayan fonksiyonların listesini oluşturur.
     *
     * @param c
     * @return
     */
    public static List<Method> getGetterMethods(Class<?> c) {
        return getMethods(c, "get.*");
    }

    /**
     * Paremetre olarak gelen sınıftaki regex'e uyan fonksiyonların listesini oluşturur.
     * @param c
     * @param regex
     * @return
     */
    public static List<Method> getMethods(Class<?> c, String regex) {

        List<Method> list = new ArrayList<Method>();

        for (Method m : c.getMethods()) {
            if (m.getName().matches(regex)) {
                list.add(m);
            }
        }

        return list;
    }

    /**
     * Generic bir interface'i implement'e etmiş sınıfın generic olarak atanmış sınıfını bulur.
     *
     * @param c
     * @return
     */
    @Nullable
    public static Class findGenericClass(Class c) {

        for(Type i : c.getGenericInterfaces()){

            if(i instanceof ParameterizedType){
                return (Class) ((ParameterizedType) i).getActualTypeArguments()[0];
                /*
                ParameterizedType tip = (ParameterizedType) i;

                for(Type j : tip.getActualTypeArguments()){

                    return (Class)j;
                }
                */
            }

        }
        return null;
    }

    public static void assignValues(Object dto, Getter source) {

        for (Method m : ReflectionBase.getSetterMethods(dto.getClass())) {
            if (m.getParameterTypes().length == 1) { //Metodun bir tane parametre olmalı

                String s = source.get(m.getName().substring(3)); //fonksiyonun başındaki set ifadesini at

                if (s != null) {

                    Object o;

                    try {

                        Class type = getObjectType(m.getParameterTypes()[0].getName());

                        if (type.equals(String.class)) { //Parametresi zaten String ise direk al
                            o = s;
                        } else {
                            Constructor c = type.getConstructor(String.class); //String olmayan parametrenin string constructor ı varmı?
                            o = c.newInstance(new Object[]{s}); //var ise yeni bir tane oluştur
                        }

                        m.invoke(dto, o);

                    } catch (Exception e) {
                        //e.printStackTrace();
                        //logger.error(e);
                    }

                }
            }
        }
    }

    public static <T> T getValue(Object source,String field) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = source.getClass();
        Field f = null;
        NoSuchFieldException noSuchFieldException = null;

        while (clazz != null){
            try{
                f = clazz.getDeclaredField(field);
                break;
            }catch (NoSuchFieldException e){
                noSuchFieldException = e;
            }
            clazz = clazz.getSuperclass();
        }

        if(f == null){
            throw noSuchFieldException;
        }

        if(!f.isAccessible())
            f.setAccessible(true);

        return (T) f.get(source);
    }

    private static Class getObjectType(String javaDataType) throws ClassNotFoundException {
        if (javaDataType.equals("boolean")) {
            return Boolean.class;
        } else if (javaDataType.equals("int")) {
            return Integer.class;
        } else if (javaDataType.equals("char")) {
            return Character.class;
        } else if (javaDataType.equals("byte")) {
            return Byte.class;
        } else if (javaDataType.equals("short")) {
            return Short.class;
        } else if (javaDataType.equals("long")) {
            return Long.class;
        } else if (javaDataType.equals("float")) {
            return Float.class;
        } else if (javaDataType.equals("double")) {
            return Double.class;
        } else if (javaDataType.equals("void")) {
            return Void.class;
        } else
            return Class.forName(javaDataType);
    }

}
