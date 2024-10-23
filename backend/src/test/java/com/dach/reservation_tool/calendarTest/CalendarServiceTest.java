package com.dach.reservation_tool.calendarTest;

import com.dach.reservation_tool.calendar.CalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

    @InjectMocks
    private CalendarService calendarService;

    @BeforeEach
    void setUp() {
        calendarService = new CalendarService();
    }

    @Test
    void getMonthDetails_WithSpecificMonthYear_ReturnsCorrectNumberOfDays() {
        // Given
        int year = 2024;
        int month = 2; // February in a leap year

        // When
        List<Map<String, Object>> result = calendarService.getMonthDetails(year, month);

        // Then
        assertEquals(29, result.size(), "February 2024 should have 29 days (leap year)");
    }

    @Test
    void getMonthDetails_FirstDayHasCorrectInformation() {
        // Given
        int year = 2024;
        int month = 10; // October

        // When
        List<Map<String, Object>> result = calendarService.getMonthDetails(year, month);
        Map<String, Object> firstDay = result.get(0);

        // Then
        assertEquals(1, firstDay.get("dayOfMonth"));
        assertEquals(LocalDate.of(2024, 10, 1), firstDay.get("date"));
        assertEquals(DayOfWeek.TUESDAY, firstDay.get("dayOfWeek"));
        assertFalse((Boolean) firstDay.get("isWeekend"));
    }

    @Test
    void getMonthDetails_WeekendDaysAreCorrectlyMarked() {
        // Given
        int year = 2024;
        int month = 10; // October

        // When
        List<Map<String, Object>> result = calendarService.getMonthDetails(year, month);

        // Then
        // October 5, 2024 is a Saturday (index 4)
        assertTrue((Boolean) result.get(4).get("isWeekend"));
        // October 6, 2024 is a Sunday (index 5)
        assertTrue((Boolean) result.get(5).get("isWeekend"));
        // October 7, 2024 is a Monday (index 6)
        assertFalse((Boolean) result.get(6).get("isWeekend"));
    }

    @Test
    void getMonthDetails_WithNullParameters_ReturnsCurrentMonth() {
        // When
        List<Map<String, Object>> result = calendarService.getMonthDetails(null, null);

        // Then
        LocalDate today = LocalDate.now();
        LocalDate firstDayInResult = (LocalDate) result.get(0).get("date");

        assertEquals(today.getYear(), firstDayInResult.getYear());
        assertEquals(today.getMonth(), firstDayInResult.getMonth());
        assertEquals(1, firstDayInResult.getDayOfMonth());
    }

    @Test
    void getMonthDetails_WithInvalidMonth_ThrowsException() {
        // Given
        int year = 2024;
        int invalidMonth = 13;

        // Then
        assertThrows(DateTimeException.class, () -> {
            calendarService.getMonthDetails(year, invalidMonth);
        });
    }

    @Test
    void getMonthDetails_ConsistencyCheck() {
        // Given
        int year = 2024;
        int month = 10; // October

        // When
        List<Map<String, Object>> result = calendarService.getMonthDetails(year, month);

        // Then
        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> day = result.get(i);
            LocalDate date = (LocalDate) day.get("date");

            // Check day of month is sequential
            assertEquals(i + 1, day.get("dayOfMonth"));

            // Check date matches day of month
            assertEquals(i + 1, date.getDayOfMonth());

            // Check month and year are consistent
            assertEquals(month, date.getMonthValue());
            assertEquals(year, date.getYear());

            // Check weekend flag consistency
            DayOfWeek dayOfWeek = (DayOfWeek) day.get("dayOfWeek");
            boolean isWeekend = (Boolean) day.get("isWeekend");
            assertEquals(
                    dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY,
                    isWeekend
            );
        }
    }
}
