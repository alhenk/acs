package kz.trei.acs.util;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Before;

import java.text.SimpleDateFormat;

public class DateStampTest extends TestCase {
    private static final Logger LOGGER = Logger.getLogger(DateStampTest.class);
    private DateStamp dateStamp;
    @Before
    public void setUp() throws Exception {
        dateStamp = new DateStamp();
    }

    public void testIsDateStampValid() throws Exception {
        assertTrue(dateStamp.isDateStampValid("2014.05.26"));
        assertFalse(dateStamp.isDateStampValid("2014.13.26"));
        assertFalse(dateStamp.isDateStampValid("2014.11.42"));
        assertFalse(dateStamp.isDateStampValid("2014.05.26asdfa"));
        assertTrue(dateStamp.isDateStampValid("20143.05.26"));
    }
}
