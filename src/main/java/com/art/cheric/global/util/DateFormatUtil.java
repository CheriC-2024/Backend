package com.art.cheric.global.util;

import static org.apache.http.client.utils.DateUtils.formatDate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DateFormatUtil {

    public static final String DATE_FORMAT = "yyyy.MM.dd";

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        try {
            ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
            return Date.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            log.error("[LocalDateTime Formatting] Failed to convert LocalDateTime to Date: {}", e.getMessage(), e);
            return null;
        }
    }

    public static String formatDate(Date date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.format(date);
        } catch (Exception e) {
            log.error("[Date Formatting] Failed to format Date: {}", e.getMessage(), e);
            return null;
        }
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        try {
            Date date = convertLocalDateTimeToDate(localDateTime);
            return formatDate(date);
        } catch (Exception e) {
            log.error("[LocalDateTime Formatting] Failed to format LocalDateTime: {}", e.getMessage(), e);
            return null;
        }
    }
}
