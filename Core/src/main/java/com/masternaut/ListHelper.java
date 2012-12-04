package com.masternaut;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public final class ListHelper {
    private ListHelper() {
    }

    public static <T> T getSingleOrDefault(Iterable<T> results) {
        Iterator<T> iterator = results.iterator();
        T returnValue;

        if (iterator.hasNext()) {
            returnValue = iterator.next();
        } else {
            return null;
        }

        if (iterator.hasNext()) {
            String error = String.format("Too many results. Expected 0 or 1 but found more than 1");
            throw new PaddingtonException(error);
        }

        return returnValue;
    }

    public static <T> T getSingle(Iterable<T> results) {
        Iterator<T> iterator = results.iterator();
        T returnValue;

        if (iterator.hasNext()) {
            returnValue = iterator.next();
        } else {
            throw new PaddingtonException("No single item in list. Expected 1");
        }

        if (iterator.hasNext()) {
            String error = String.format("Too many results. Expected 1 but found more than 1");
            throw new PaddingtonException(error);
        }

        return returnValue;
    }

    public static <T> T getFirstOrDefault(Iterable<T> list) {
        Iterator<T> iterator = list.iterator();

        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public static <T> T getFirst(Iterable<T> list) {
        T t = getFirstOrDefault(list);

        if (t == null) {
            throw new PaddingtonException("Empty list");
        }

        return t;
    }

    public static <K, T> Map<K, List<T>> groupBy(Iterable<T> items, Selector<T, K> selector) {
        HashMap<K, List<T>> results = new HashMap<K, List<T>>();

        for (T t : items) {
            K key = selector.action(t);

            List<T> list = results.get(key);
            if (list == null) {
                list = new ArrayList<T>();
                results.put(key, list);
            }

            list.add(t);
        }

        return results;
    }

    public static <T> Map<String, T> listToMap(Iterable<T> items, Selector<T, String> selector) {
        Map<String, T> map = new HashMap<String, T>();

        for(T t : items){
            String key = selector.action(t);

            map.put(key, t);
        }

        return map;
    }

    private static <T> Method getPropertyDescription(BeanInfo beanInfo, String propertyName, Class<T> clazz) {
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            if (descriptor.getName().equals(propertyName)) {
                return descriptor.getReadMethod();
            }
        }

        String error = String.format("No read property called '%s' in '%s' found", propertyName, clazz.getSimpleName());
        throw new PaddingtonException(error);
    }

    private static <T> String tryGetPropertyValue(T t, Method getterMethod) {
        Object value;
        try {
            value = getterMethod.invoke(t);
        } catch (Exception e) {
            throw new PaddingtonException(e);
        }

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    private static <T> BeanInfo getBeanInfo(Class<T> clazz) {
        try {
            return Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            throw new PaddingtonException(e);
        }
    }

    public static <T, TResult> List<TResult> select(Iterable<T> list, Selector<T, TResult> selector) {
        List<TResult> values = new ArrayList<TResult>();

        for (T t : list) {
            TResult value = selector.action(t);

            values.add(value);
        }

        return values;
    }

    public static <T> List<String> select(Iterable<T> list, String propertyName, Class<T> clazz) {

        BeanInfo beanInfo = getBeanInfo(clazz);
        final Method method = getPropertyDescription(beanInfo, propertyName, clazz);

        return select(list, new Selector<T, String>() {
            @Override
            public String action(T t) {
                return tryGetPropertyValue(t, method);
            }
        });
    }

    public static <T> List<T> take(List<T> list, int numberToTake) {
        ArrayList<T> results = new ArrayList<T>();
        Iterator<T> iterator = list.iterator();

        int i = 0;

        while (iterator.hasNext()) {
            i++;
            if (i > numberToTake) {
                return results;
            }

            results.add(iterator.next());
        }

        return results;
    }

    public static <T> boolean contains(List<T> list, match<T> matchPredicate) {

        if (list != null) {
            Iterator<T> iterator = list.iterator();

            while (iterator.hasNext()) {
                T next = iterator.next();

                if (matchPredicate.isMatch(next)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static <K, V> HashMap<K, V> copyMap(Map<K, V> source) {
        if (source == null) {
            return null;
        }

        HashMap<K, V> copy = new HashMap<K, V>();

        for (K key : source.keySet()) {
            copy.put(key, source.get(key));
        }

        return copy;
    }

    public static <T> List<T> toList(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<T>();

        for(T t : iterable){
            list.add(t);
        }

        return list;
    }

    public interface Selector<T, TResult> {
        TResult action(T t);
    }

    public interface match<T> {
        boolean isMatch(T t);
    }
}
