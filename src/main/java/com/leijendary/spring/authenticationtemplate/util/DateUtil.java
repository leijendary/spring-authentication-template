package com.leijendary.spring.authenticationtemplate.util;

import java.time.OffsetDateTime;
import java.util.Date;

import static com.leijendary.spring.authenticationtemplate.util.RequestContext.getTimeZone;

public class DateUtil {

    public static OffsetDateTime toOffsetDateTime(final Date date) {
        return date.toInstant().atZone(getTimeZone().toZoneId()).toOffsetDateTime();
    }
}
