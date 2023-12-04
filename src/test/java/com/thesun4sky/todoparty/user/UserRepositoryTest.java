package com.thesun4sky.todoparty.user;  // 패키지 선언

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;  // UserRepository 주입

    @Test
    @DisplayName("사용자 이름으로 사용자 찾기 사용자가 존재하는 경우")
    void test1() {
        // Given
        User savedUser = new User();
        savedUser.setUsername("rjsdn1234");
        savedUser.setPassword("1qaz2ws");

        userRepository.save(savedUser);

        // When
        Optional<User> foundUser = userRepository.findByUsername("rjsdn1234");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("rjsdn1234", foundUser.get().getUsername());
    }

    @Test
    @DisplayName("사용자 이름으로 사용자 찾기 사용자가 존재하지 않는 경우")
    void test2() {
        // Given
        Optional<User> foundUser = userRepository.findByUsername("noUser");

        // Then
        assertFalse(foundUser.isPresent());
    }
}
