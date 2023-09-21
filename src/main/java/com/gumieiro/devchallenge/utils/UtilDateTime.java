package com.gumieiro.devchallenge.utils;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UtilDateTime {
    public static Date strToSqlDate(String inputDateString) {
        inputDateString = Objects.requireNonNull(inputDateString, "The date is invalid! Date: " + inputDateString);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(inputDateString, inputFormatter);
        return Date.valueOf(localDate);
    }

    public static Time strToSqlTime(String inputTimeString) {
        inputTimeString = Objects.requireNonNull(inputTimeString, "The date is invalid! Date: " + inputTimeString);
        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("HHmmss");
            java.util.Date date = inputFormatter.parse(inputTimeString);
            return new Time(date.getTime());
        } catch(Exception e) {
            log.info("Error: " + e.getMessage());
            return null;
        }

    }
}
