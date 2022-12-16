package com.fitback.ssu.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtil {
    public static String dateTimeToString(LocalDateTime time){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        return simpleDateFormat.format(time);
        return time.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public static Integer calDeadLine(String eTime, String sTime) throws ParseException {
        SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date start = beforeFormat.parse(sTime);
        Date end = beforeFormat.parse(eTime);
        return Math.toIntExact(((end.getTime() - start.getTime()) / 1000) / (24 * 60 * 60));
    }
}
