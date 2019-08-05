package com.github.saphyra.testing;

import java.lang.reflect.Field;

public class ReflectionUtil {
    public static Object getField(Object o, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(o);
    }
}
