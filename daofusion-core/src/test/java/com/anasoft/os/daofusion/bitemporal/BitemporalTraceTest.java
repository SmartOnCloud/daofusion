package com.anasoft.os.daofusion.bitemporal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

public class BitemporalTraceTest {
    private static final long INF = TimeUtils.ACTUAL_END_OF_TIME;

    private ArrayList<Bitemporal> data;
    private BitemporalTrace tested;

    private Bitemporal a_1;
    private Bitemporal a_2;
    private Bitemporal b_1;
    private Bitemporal b_2;
    private Bitemporal c_1;
    private Bitemporal c_2;
    private Bitemporal d;

    @Before
    public void setUp() {
        data = new ArrayList<Bitemporal>();
        tested = new BitemporalTrace(data);
        a_1 = bitemporalMock(0, INF, 0, 10);
        a_2 = bitemporalMock(0, 15, 10, INF);
        b_1 = bitemporalMock(15, INF, 10, 20);
        b_2 = bitemporalMock(15, 30, 20, INF);
        c_1 = bitemporalMock(30, INF, 20, 30);
        c_2 = bitemporalMock(30, 45, 30, INF);
        d = bitemporalMock(45, INF, 30, INF);
        data.addAll(Arrays.asList(new Bitemporal[] { a_1, a_2, b_1, b_2, c_1, c_2, d }));
    }

    @Test
    public void testHistory() {
        assertThat(tested.getHistory(new DateTime(0)).size(), is(1));
        assertThat(tested.getHistory(new DateTime(10)).size(), is(2));
        assertThat(tested.getHistory(new DateTime(20)).size(), is(3));
        assertThat(tested.getHistory(new DateTime(30)).size(), is(4));
        assertThat(tested.getHistory(new DateTime(INF - 1)).size(), is(4));
    }

    @Test
    public void testEvolution() {
        assertThat(tested.getEvolution(new DateTime(0)).size(), is(2));
        assertThat(tested.getEvolution(new DateTime(15)).size(), is(3));
        assertThat(tested.getEvolution(new DateTime(30)).size(), is(4));
        assertThat(tested.getEvolution(new DateTime(45)).size(), is(4));
        assertThat(tested.getEvolution(new DateTime(INF - 1)).size(), is(4));
    }

    @Test
    public void testGet() throws Exception {
        Collection<Bitemporal> collection = tested.get(new DateTime(15), new DateTime(20));
        assertThat(collection.size(), is(1));
        assertThat(collection.contains(b_2), is(true));

        Collection<Bitemporal> collection2 = tested.get(new DateTime(0), new DateTime(0));
        assertThat(collection2.size(), is(1));
        assertThat(collection2.contains(a_1), is(true));

        Collection<Bitemporal> collection3 = tested.get(new DateTime(50), new DateTime(50));
        assertThat(collection3.size(), is(1));
        assertThat(collection3.contains(d), is(true));

    }

    @Test
    public void testgetItemsThatNeedsToBeEnded() throws Exception {
        TimeUtils.setReference(new DateTime(100));
        Collection<Bitemporal> itemsThatNeedsToBeEnded = tested.getItemsThatNeedsToBeEnded(bitemporalMock(
                60, INF, 40, INF));
        assertThat(itemsThatNeedsToBeEnded.size(), is(1));
        assertThat(itemsThatNeedsToBeEnded.contains(d), is(true));

        Collection<Bitemporal> itemsThatNeedsToBeEnded2 = tested.getItemsThatNeedsToBeEnded(bitemporalMock(
                10, INF, 10, INF));

        assertThat(itemsThatNeedsToBeEnded2.size(), is(4));
        assertThat(itemsThatNeedsToBeEnded2.contains(d), is(true));
        assertThat(itemsThatNeedsToBeEnded2.contains(c_2), is(true));
        assertThat(itemsThatNeedsToBeEnded2.contains(b_2), is(true));
        assertThat(itemsThatNeedsToBeEnded2.contains(a_2), is(true));
    }

    @Test
    public void testRevertToC1() throws Exception {
        ArrayList<Bitemporal> data2 = new ArrayList<Bitemporal>();
        Bitemporal a_1 = bitemporalMock(0, INF, 0, 10);
        Bitemporal a_2 = bitemporalMock(0, 15, 10, INF);
        Bitemporal b_1 = bitemporalMock(15, INF, 10, 20);
        Bitemporal b_2 = bitemporalMock(15, 30, 20, INF);
        Bitemporal c_1 = bitemporalMock(30, INF, 20, INF);
        data2.addAll(Arrays.asList(new Bitemporal[] { a_1, a_2, b_1, b_2, c_1 }));

        tested.revert(new DateTime(25));
        Collection<Bitemporal> data3 = tested.getData();
        assertThat(new HashSet<Bitemporal>(data3), equalTo(new HashSet<Bitemporal>(data2)));
    }

    @Test
    public void testRevertToA1() throws Exception {
        ArrayList<Bitemporal> data2 = new ArrayList<Bitemporal>();
        Bitemporal a_1 = bitemporalMock(0, INF, 0, INF);
        data2.addAll(Arrays.asList(new Bitemporal[] { a_1 }));
        tested.revert(new DateTime(0));
        Collection<Bitemporal> data3 = tested.getData();
        assertThat(new HashSet<Bitemporal>(data3), equalTo(new HashSet<Bitemporal>(data2)));
        
        tested.revert(new DateTime(10));
        Collection<Bitemporal> data4 = tested.getData();
        assertThat(new HashSet<Bitemporal>(data4), equalTo(new HashSet<Bitemporal>(data2)));

    }

    private Bitemporal bitemporalMock(long validFrom, long validTo, long recordFrom, long recordTo) {
        return new BitemporalMock(new Interval(validFrom, validTo), new Interval(recordFrom, recordTo));
    }

    public static class BitemporalMock implements Bitemporal {

        private Interval validity;
        private Interval record;

        public BitemporalMock(Interval validity, Interval record) {
            this.validity = validity;
            this.record = record;
        }

        public Bitemporal copyWith(Interval validityInterval) {
            return new BitemporalMock(validityInterval, record);
        }

        public void end() {
            this.record = TimeUtils.interval(record.getStart(), TimeUtils.now());
        }

        public Interval getRecordInterval() {
            return record;
        }

        public Interval getValidityInterval() {
            return validity;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((record == null) ? 0 : record.hashCode());
            result = prime * result + ((validity == null) ? 0 : validity.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            BitemporalMock other = (BitemporalMock) obj;
            if (record == null) {
                if (other.record != null)
                    return false;
            } else if (!record.equals(other.record))
                return false;
            if (validity == null) {
                if (other.validity != null)
                    return false;
            } else if (!validity.equals(other.validity))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return validity + " " + record;
        }

        public void resurrect() {
            this.record = TimeUtils.from(record.getStart());
        }

    }
}
