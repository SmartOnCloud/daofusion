/*
 * (c) Copyright Ervacon 2007.
 * All Rights Reserved.
 */

package com.anasoft.os.daofusion.bitemporal;

import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * Static utilities dealing with <i>time</i>.
 * 
 * @author Erwin Vervaet
 * @author Christophe Vanfleteren
 */
public class TimeUtils {

	// no need to instantiate this class
	private TimeUtils() {
	}


	// time framing functionality

	private static final ThreadLocal<DateTime> reference = new ThreadLocal<DateTime>();

	/**
	 * Determines whether or not a reference time has been set.
	 */
	public static boolean isReferenceSet() {
		return reference.get() != null;
	}

	/**
	 * Returns the reference time, or <i>wallclock now</i> if no reference
	 * time has been set.
	 */
	public static DateTime reference() {
		return isReferenceSet() ? reference.get() : current();
	}

	/**
	 * Set the reference time to the specified time.
	 * @param dateTime the reference time to set
	 */
	public static void setReference(DateTime dateTime) {
		reference.set(dateTime);
	}
	
	/**
	 * Clear the reference time.
	 */
	public static void clearReference() {
		reference.remove();
	}


	// general purpose date/time related utilities

    /**
     * Timestamp value that represents N/A date.
     */
    public static final long ACTUAL_END_OF_TIME = 38742530400000L;       //14.9.3197
	public static final long END_OF_TIME = ACTUAL_END_OF_TIME - 1;
	private static final DateTime endOfTime = new DateTime(END_OF_TIME);

	/**
	 * Create a {@link DateTime} object representing given day of given month
	 * in given year.
	 */
	public static DateTime day(int day, int month, int year) {
		return new DateTime(year, month, day, 0, 0, 0, 0);
	}

	/**
	 * Returns the current reference time. If a reference time is set, it is that
	 * reference time that will be returned.
	 * 
	 * @see #reference()
	 */
	public static DateTime now() {
		return reference();
	}

	/**
	 * Returns the current local time (<i>wallclock now</i>) (reference time is ignored).
	 */
	public static DateTime current() {
		return new DateTime();
	}

	/**
	 * Returns a {@link DateTime} object representing the end of time.
	 */
	public static DateTime endOfTime() {
		return endOfTime;
	}

	/**
	 * Returns a interval running for the specified period. The returned interval
	 * is half-open: it includes the start time, but not the end time.
	 */
	public static Interval interval(DateTime start, DateTime end) {
		return new Interval(start.getMillis(), end.getMillis());
	}

	/**
	 * Returns an interval running from given start time till the end of time.
	 * 
	 * @see #endOfTime()
	 */
	public static Interval from(DateTime start) {
		return interval(start, new DateTime(ACTUAL_END_OF_TIME));
	}

	/**
	 * Returns an interval running from now till the end of time.
	 */
	public static Interval fromNow() {
		return from(now());
	}

}
