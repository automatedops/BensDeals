package net.bensdeals.utils;

import android.content.Context;

import net.bensdeals.support.RobolectricTestRunnerWithInjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static com.pivotallabs.robolectricgem.expect.Expect.expect;

@RunWith(RobolectricTestRunnerWithInjection.class)
public class DateFormatterTest {
    @Inject TestCurrentDateProvider dateProvider;
    @Inject Context context;
    public Date now;

    @Before
    public void setup() throws Exception {
        now = DateFormatter.stringToDate("Tue, 03 Jul 2012 22:13:20 -0700");
        dateProvider.set(now);
    }

    @Test
    public void shouldParseStringToDate() throws Exception {
        Date date = DateFormatter.stringToDate("Tue, 03 Jul 2012 22:13:20 -0700");
        expect(date.getTime()).toEqual(1341378800000l);
    }

    @Test
    public void shouldFormatDateWithInOneMinuteToString() throws Exception {
        Date date = DateFormatter.stringToDate("Tue, 03 Jul 2012 22:12:19 -0700");
        String s = DateFormatter.formatDate(dateProvider, date, context);
        expect(s).toEqual("1 minute ago");
    }

    @Test
    public void shouldFormatDateWithInXMinutesToString() throws Exception {
        Date date = DateFormatter.stringToDate("Tue, 03 Jul 2012 22:08:19 -0700");
        String s = DateFormatter.formatDate(dateProvider, date, context);
        expect(s).toEqual("5 minutes ago");
    }

    @Test
    public void shouldFormatDateWithInOneHourToString() throws Exception {
        Date date = DateFormatter.stringToDate("Tue, 03 Jul 2012 21:12:19 -0700");
        String s = DateFormatter.formatDate(dateProvider, date, context);
        expect(s).toEqual("1 hour ago");
    }

    @Test
    public void shouldFormatDateWithInXHoursToString() throws Exception {
        Date date = DateFormatter.stringToDate("Tue, 03 Jul 2012 10:08:19 -0700");
        String s = DateFormatter.formatDate(dateProvider, date, context);
        expect(s).toEqual("12 hours ago");
    }

    @Test
    public void shouldFormatDateWithInOneDayToStringIfNotSameDate() throws Exception {
        Date date = DateFormatter.stringToDate("Tue, 02 Jul 2012 23:59:59 -0700");
        String s = DateFormatter.formatDate(dateProvider, date, context);
        expect(s).toEqual("1 day ago");
    }

    @Test
    public void shouldFormatDateWithInXDaysToStringIfNotSameDate() throws Exception {
        Date date = DateFormatter.stringToDate("Tue, 01 Jul 2012 23:59:59 -0700");
        String s = DateFormatter.formatDate(dateProvider, date, context);
        expect(s).toEqual("2 days ago");
    }
}
