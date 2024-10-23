package com.dach.reservation_tool.calendar;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarService {

    public List<Map<String, Object>> getMonthDetails(Integer year, Integer month) {
        YearMonth yearMonth;

        if (year == null || month == null) {
            yearMonth = YearMonth.now();
        } else {
            yearMonth = YearMonth.of(year, month);
        }

        List<Map<String, Object>> monthDetails = new ArrayList<>();
        LocalDate firstDay = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = firstDay.plusDays(day - 1);
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            Map<String, Object> dayDetails = new HashMap<>();
            dayDetails.put("date", date);
            dayDetails.put("dayOfMonth", day);
            dayDetails.put("dayOfWeek", dayOfWeek);
            dayDetails.put("isWeekend", dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);

            monthDetails.add(dayDetails);
        }

        return monthDetails;
    }
}

