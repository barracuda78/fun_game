package com.barracuda.fun.service.xlsx;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateFormatter {

    public static final String LOCAL_DATE_PATTERN = "dd MM yyyy";

    public String format(LocalDate localDate) {
        return DateTimeFormatter.ofPattern(LOCAL_DATE_PATTERN).format(localDate);
    }

}
