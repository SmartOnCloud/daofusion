package com.anasoft.os.daofusion.bitemporal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BitemporalTraceTest {
    private static final long INF = TimeUtils.END_OF_TIME;

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
        assertThat(tested.get(new DateTime(15), new DateTime(20)).size(), is(1));
        assertThat(tested.get(new DateTime(0), new DateTime(0)).size(), is(1));
        assertThat(tested.get(new DateTime(0), new DateTime(0)).size(), is(1));
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

    private Bitemporal bitemporalMock(long validFrom, long validTo, long recordFrom, long recordTo) {
        Bitemporal item1 = Mockito.mock(Bitemporal.class);
        when(item1.getValidityInterval()).thenReturn(new Interval(validFrom, validTo));
        when(item1.getRecordInterval()).thenReturn(new Interval(recordFrom, recordTo));
        return item1;
    }
}
