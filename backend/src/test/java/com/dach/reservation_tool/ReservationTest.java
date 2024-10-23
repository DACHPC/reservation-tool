package com.dach.reservation_tool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private static class TestReservation extends Reservation<String> {
        // A subclass is necessary to instantiate the abstract class.
    }

    @Test
    void testIdSetter() {
        // Arrange
        TestReservation reservation = new TestReservation();
        String testId = "test-id";

        // Act
        reservation.setId(testId);

        // Assert
        assertEquals(testId, reservation.getId(), "The ID should be set correctly.");
    }

    @Test
    void testIdGetter() {
        // Arrange
        TestReservation reservation = new TestReservation();
        String testId = "test-id";

        // Act
        reservation.setId(testId);  // Not testing the setter here, just setting up for the getter test

        // Assert
        assertEquals(testId, reservation.getId(), "The ID should be retrieved correctly.");
    }

    @Test
    void testId() {
        // Arrange
        TestReservation reservation1 = new TestReservation();
        TestReservation reservation2 = new TestReservation();

        // Act
        reservation1.setId("test-id");
        reservation2.setId("test-id");

        // Assert
        assertEquals(reservation1, reservation2, "Reservations with the same ID should be equal.");
        assertEquals(reservation1.hashCode(), reservation2.hashCode(), "Hash codes should be the same for equal objects.");

        // Act
        reservation2.setId("another-id");

        // Assert
        assertNotEquals(reservation1, reservation2, "Reservations with different IDs should not be equal.");
        assertNotEquals(reservation1.hashCode(), reservation2.hashCode(), "Hash codes should be different for unequal objects.");
    }
}

