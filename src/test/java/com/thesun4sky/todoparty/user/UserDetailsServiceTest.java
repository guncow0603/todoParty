package com.thesun4sky.todoparty.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        // Given: Mock 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자 정보 가져오기 사용자가 있는 경우")
    void test1() {
        // Given
        String username = "rjsdn1234";
        User user = new User(username, "1qaz2wsx");

        // When
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        UserDetailsImpl userDetails = userDetailsService.getUserDetails(username);

        // Then
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    @DisplayName("사용자 정보 가져오기 사용자 없는경우")
    void test2() {
        // Given
        String username = "noUser";

        // When
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Then
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.getUserDetails(username));
    }
}

