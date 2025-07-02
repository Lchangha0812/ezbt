package com.purefunction.ezbt.weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter {

    // 초단기실황 base_time 계산 (매시 10분 후)
    public static LocalDateTime getUltraSrtNcstBaseTime(LocalDateTime now) {
        int hour = now.getHour();
        int minute = now.getMinute();

        if (minute < 10) {
            // 현재 시각이 정시 10분 전이면 이전 시간의 10분으로 설정
            now = now.minusHours(1);
            hour = now.getHour();
            minute = 10;
        } else {
            // 현재 시각이 정시 10분 이후면 현재 시간의 10분으로 설정
            minute = 10;
        }
        return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute);
    }

    // 단기예보 base_time (0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300)
    public static LocalDateTime getVilageFcstBaseTime(LocalDateTime now) {
        int hour = now.getHour();
        int minute = now.getMinute();

        if (hour >= 23 || (hour >= 0 && hour < 2)) return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 0).minusDays(1);
        if (hour >= 20) return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 20, 0);
        if (hour >= 17) return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 17, 0);
        if (hour >= 14) return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 14, 0);
        if (hour >= 11) return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 11, 0);
        if (hour >= 8) return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 8, 0);
        if (hour >= 5) return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 5, 0);
        if (hour >= 2) return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 2, 0);
        return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 0).minusDays(1); // 기본값 (이전 날 23시)
    }
}
