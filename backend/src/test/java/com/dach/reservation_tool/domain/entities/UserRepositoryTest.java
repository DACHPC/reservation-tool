package com.dach.reservation_tool.domain.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User(UUID.randomUUID(), "anna.hensel@dachpc.com", "password", "Anna", "Hensel", Role.EMPLOYEE);
        userRepository.save(testUser);
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(testUser);
    }

    @Test
    void givenUser_whenSaved_thenCanBeFoundById() {
        User savedUser = userRepository.findById(testUser.getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals(testUser.getEmail(), savedUser.getEmail());
        assertEquals(testUser.getFirstName(), savedUser.getFirstName());
        assertEquals(testUser.getLastName(), savedUser.getLastName());
        assertEquals(testUser.getPassword(), savedUser.getPassword());
        assertEquals(testUser.getRole(), savedUser.getRole());
    }

    @Test
    void givenUser_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
        testUser.setFirstName("Sophia");
        userRepository.save(testUser);

        User updatedUser = userRepository.findById(testUser.getId()).orElse(null);

        assertNotNull(updatedUser);
        assertEquals("Sophia", updatedUser.getFirstName());
    }

    @Test
    void givenUserList_whenSaved_thenCountIsRight(){
        User testUser2 = new User(UUID.randomUUID(), "sophia.hensel@dachpc.com", "password2", "Sophia", "Hensel", Role.EMPLOYEE);
        User testUser3 = new User(UUID.randomUUID(), "nam.nguyen@dachpc.com", "password3", "Nam", "Nguyen", Role.ADMIN);
        ArrayList<User> users = new ArrayList<>();
        users.add(testUser2);
        users.add(testUser3);

        userRepository.saveAll(users);

        assertEquals(3, userRepository.count());
        userRepository.deleteAll();
        assertEquals(0, userRepository.count());
    }

}