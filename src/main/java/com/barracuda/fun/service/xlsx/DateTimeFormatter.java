package com.barracuda.fun.service.xlsx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DateTimeFormatter {

    public static final String LOCAL_DATE_PATTERN = "dd MM yyyy";

    public static final String LOCAL_DATE_TIME_PATTERN = "dd-MM-yyyy-HH-mm";

    public String formatDate(LocalDate localDate) {
        return java.time.format.DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN).format(localDate);
    }

    public String formatDateTime(LocalDateTime localDateTime) {
        return java.time.format.DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN).format(localDateTime);
    }

}
