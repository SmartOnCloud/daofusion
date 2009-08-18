package com.anasoft.os.daofusion.bitemporal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.joda.time.Interval;

/**
 * {@link ValueAccessor} adapter.
 * 
 * @author igor.mihalik
 * 
 * @param <V> Value to be wrapped by bitemporal wrapper
 * @param <T> Wrapper implementation that wrapper required value type
 */
public abstract class ValueAccessorAdapter<V, T extends BitemporalWrapper<V>> implements
        ValueAccessor<V, T> {

    public V extractValue(T t) {
        if (t == null)
            return null;
        return t.getValue();
    }

    public static <V, T extends BitemporalWrapper<V>> ValueAccessorAdapter<V, T> create(
            final Class<T> clazz) {
        return new ValueAccessorAdapter<V, T>() {
            public T wrapValue(V value, Interval validityInterval) {
                try {
                    Constructor<T> ctor = clazz.getDeclaredConstructor();
                    makeAccessible(ctor);
                    T newInstance = ctor.newInstance();
                    newInstance.setValue(value);
                    newInstance.setValidityInterval(validityInterval);
                    return newInstance;
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (SecurityException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

        };
    }

    private static <T> void makeAccessible(Constructor<T> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
                && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }
}