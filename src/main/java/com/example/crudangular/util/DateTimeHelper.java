package com.example.crudangular.util;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate convertStringToLocalDate(String date){
        return LocalDate.parse(date,formatter);
    }
    public static  String convertLocalDateToString(LocalDate date){
        return date.format(formatter);
    }
}
