/*
 * (c) Copyright Ervacon 2007.
 * All Rights Reserved.
 */

package com.anasoft.os.daofusion.bitemporal;

import java.util.Collection;

/**
 * {@link BitemporalProperty} implementation that uses {@link BitemporalWrapper}
 * s.
 * 
 * @author Erwin Vervaet
 * @author Christophe Vanfleteren
 */
public class WrappedBitemporalProperty<V, T extends BitemporalWrapper<V>> extends BitemporalProperty<V, T> {

    public WrappedBitemporalProperty(Collection<? extends Bitemporal> data,
            ValueAccessor<V,T> valueAccessor) {
        super(data, valueAccessor);
    }
}