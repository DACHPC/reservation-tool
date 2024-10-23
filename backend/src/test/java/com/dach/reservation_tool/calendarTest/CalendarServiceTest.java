package com.dach.reservation_tool.calendarTest;

import com.dach.reservation_tool.calendar.CalendarService;
import jakarta.persistence.NamedStoredProcedureQueries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CalendarServiceTest {

    @InjectMocks
    private CalendarService calendarService;

    @BeforeEach
    void setUp() {
        calendarService = new CalendarService();
    }

    @Test
    void testGetMonthDetailsForLeapYear() {
        // GIVEN
        int year = 2024;
        int month = 2; // February in a leap year

        // WHEN
        List<Map<String, Object>> result = calendarService.getMonthDetails(year, month);

        // THEN
        assertEquals(29, result.size(), "February 2024 should have 29 days (leap year)");
    }

    @Test
    void testGetMonthDetailsFirstDayHasCorrectInformation() {
        // GIVEN
        int year = 2024;
        int month = 10; // October

        // WHEN
        List<Map<String, Object>> result = calendarService.getMonthDetails(year, month);
        Map<String, Object> firstDay = result.get(0);

        // THEN
        assertEquals(1, firstDay.get("dayOfMonth"));
        assertEquals(LocalDate.of(2024, 10, 1), firstDay.get("date"));
        assertEquals(DayOfWeek.TUESDAY, firstDay.get("dayOfWeek"));
        assertFalse((Boolean) firstDay.get("isWeekend"));
    }

    @Test
    void testGetMonthDetailsWithNullParametersReturnsCurrentMonth() {
        //GIVEN
        LocalDate today = LocalDate.now();
        var expectedYear = today.getYear();
        var expectedMonth = today.getMonth();

        // WHEN
        List<Map<String, Object>> result = calendarService.getMonthDetails(null, null);

        // THEN
        LocalDate firstDayInResult = (LocalDate) result.get(0).get("date");

        assertEquals(expectedYear, firstDayInResult.getYear());
        assertEquals(expectedMonth, firstDayInResult.getMonth());
        assertEquals(1, firstDayInResult.getDayOfMonth());
    }

    @Test
    void testGetMonthDetailsWithInvalidMonthThrowsException() {
        // GIVEN
        int year = 2024;
        int invalidMonth = 13;

        //WHEN // THEN
        assertThrows(DateTimeException.class, () -> {
            calendarService.getMonthDetails(year, invalidMonth);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "2024, 10", // Oktober 2024
            "2024, 2",  // Februar 2024 (Schaltjahr)
            "2023, 12", // Dezember 2023
            "2021, 6"   // Juni 2021
    })
    void testGetMonthDetailsConsistencyCheck(int year, int month) {
        //GIVEN is the csv source

        // WHEN
        List<Map<String, Object>> result = calendarService.getMonthDetails(year, month);

        // THEN
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
