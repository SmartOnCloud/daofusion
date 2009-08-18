/*
 * (c) Copyright Ervacon 2007.
 * All Rights Reserved.
 */

package com.anasoft.os.daofusion.bitemporal;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.time.Interval;

import com.anasoft.os.daofusion.entity.MutablePersistentEntity;

/**
 * Decorates a value with bitemporal information, making it possible to
 * bitemporally track the value in a {@link BitemporalTrace}. A
 * {@link BitemporalWrapper} allows you to bitemporally track existing value
 * classes, for instance strings.
 * <p>
 * Due to the nature of bitemporality, the wrapped value should be immutable.
 * The value itself will never change, instead new values will be added to the
 * {@link BitemporalTrace} to represent changes in the value. A
 * {@link BitemporalWrapper} itself is not immutable, its record interval can be
 * {@link #end() ended}.
 * <p>
 * Instances of this class are serializable if the wrapped value is
 * serializable.
 * <p>
 * Objects of this class are not thread-safe.
 * 
 * @author Erwin Vervaet
 * @author Christophe Vanfleteren
 * @author Igor Mihalik
 */
@MappedSuperclass
public abstract class BitemporalWrapper<V> extends MutablePersistentEntity implements Bitemporal {

    public static final String _VALUE = "value";

    public static final String _VALID_FROM = "validityInterval.start";
    public static final String _VALID_TO = "validityInterval.end";

    public static final String _RECORD_FROM = "recordInterval.start";
    public static final String _RECORD_TO = "recordInterval.end";

    @Columns(columns = { @Column(name = "validFrom"), @Column(name = "validTo") })
    @Type(type = PersistentInterval.TYPE)
    private Interval validityInterval;

    @Columns(columns = { @Column(name = "recordFrom"), @Column(name = "recordTo") })
    @Type(type = PersistentInterval.TYPE)
    private Interval recordInterval;

    protected BitemporalWrapper() {
        /* constructor for hibernate*/
    }

    /**
     * Bitemporally wrap given value. Validity will be as specified, and the
     * recording interval will be {@link TimeUtils#fromNow() from now on}.
     * @param value the value to wrap (can be null)
     * @param validityInterval the validity of the value
     */
    public BitemporalWrapper(V value, Interval validityInterval) {
        if (validityInterval == null)
            throw new IllegalArgumentException("The validity interval is required");
        this.validityInterval = validityInterval;
        this.recordInterval = TimeUtils.fromNow();
        setValue(value);
    }

    protected abstract void setValue(V value);

    /**
     * Returns the wrapped value, possibly null.
     */
    public abstract V getValue();

    public Interval getValidityInterval() {
        return validityInterval;
    }
    
    protected void setValidityInterval(Interval validityInterval) {
        this.validityInterval = validityInterval;
    }

    public Interval getRecordInterval() {
        return recordInterval;
    }

    public void end() {
        this.recordInterval = TimeUtils.interval(getRecordInterval().getStart(), TimeUtils.now());
    }

    public abstract Bitemporal copyWith(Interval validityInterval);

    @Override
    public String toString() {
        return getValidityInterval() + "  ~  " + getRecordInterval() + "  ~  "
                + String.valueOf(getValue());
    }

    /**
     * Returns true if given list of wrapped values contains given value.
     * @param list
     * @param value
     * @return true if given list of wrapped values contains given value, else
     *         returns false.
     */
    public static <T extends BitemporalWrapper<V>, V> boolean contains(List<T> list, V value) {
        for (T t : list) {
            if (t.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}