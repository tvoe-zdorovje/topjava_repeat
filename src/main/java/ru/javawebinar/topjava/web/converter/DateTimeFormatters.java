package ru.javawebinar.topjava.web.converter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {
        @Override
        public LocalDate parse(String text, Locale locale) {
            return LocalDate.parse(text);
        }

        @Override
        public String print(LocalDate localDate, Locale locale) {
            return DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {
        @Override
        public LocalTime parse(String text, Locale locale) {
            return LocalTime.parse(text);
        }

        @Override
        public String print(LocalTime localTime, Locale locale) {
            return DateTimeFormatter.ISO_LOCAL_TIME.format(localTime);
        }
    }
}
