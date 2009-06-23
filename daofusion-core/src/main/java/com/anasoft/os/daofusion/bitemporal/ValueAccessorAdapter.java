package com.anasoft.os.daofusion.bitemporal;

/**
 * {@link ValueAccessor} adapter.
 * 
 * @author igor.mihalik
 * 
 * @param <V> Value to be wrapped by bitemporal wrapper
 * @param <T> Wrapper implementation that wrapr required value type
 */
public abstract class ValueAccessorAdapter<V, T extends BitemporalWrapper<V>> implements
        ValueAccessor<V, T> {

    public V extractValue(T t) {
        if (t == null)
            return null;
        return t.getValue();
    }
}