package com.dach.reservation_tool.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User testUser = new User(UUID.randomUUID(), "anna.hensel@dachpc.com", "password", "Anna", "Hensel", Role.EMPLOYEE);

    @Test
    void getId() {
        assertNotNull(testUser.getId());
    }

    @Test
    void getEmail() {
        assertEquals("anna.hensel@dachpc.com", testUser.getEmail());
    }

    @Test
    void getPassword() {
        assertEquals("password", testUser.getPassword());
    }

    @Test
    void getFirstName() {
        assertEquals("Anna", testUser.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Hensel", testUser.getLastName());
    }

    @Test
    void getRole() {
        assertEquals(Role.EMPLOYEE, testUser.getRole());
    }


    @Test
    void setEmail() {
        testUser.setEmail("nicklaus.widjaja@dachpc.com");
        assertEquals("nicklaus.widjaja@dachpc.com", testUser.getEmail());
    }

    @Test
    void setPassword() {
        testUser.setPassword("password2");
        assertEquals("password2", testUser.getPassword());
    }

    @Test
    void setFirstName() {
        testUser.setFirstName("Nicklaus");
        assertEquals("Nicklaus", testUser.getFirstName());
    }

    @Test
    void setLastName() {
        testUser.setLastName("Widjaja");
        assertEquals("Widjaja", testUser.getLastName());
    }

    @Test
    void setRole() {
        testUser.setRole(Role.ADMIN);
        assertEquals(Role.ADMIN, testUser.getRole());
    }
}