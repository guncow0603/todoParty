package com.thesun4sky.todoparty.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName(" 존재하지 않는 유저로 회원가입 했을 때 테스트")
    void test1() {

        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("rjsdn123");
        userRequestDto.setPassword("1234qwer");


        // Mock 객체에 대한 행동 설정
        when(userRepository.findByUsername("rjsdn123")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234qwer")).thenReturn("hashedPassword");

        // When
        userService.signup(userRequestDto);

        // Then
        verify(userRepository, times(1)).findByUsername("rjsdn123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("이미 존재하는 유저로 가입 했을 때 테스트")
    void test2() {


        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("rjsdn123");
        userRequestDto.setPassword("1234qwer");


        // When
        when(userRepository.findByUsername("rjsdn123")).thenReturn(Optional.of(new User()));

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.signup(userRequestDto));
        verify(userRepository, times(1)).findByUsername("rjsdn123");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("올바른 로그인 했을 때 테스트")
    void test3() {

        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("rjsdn123");
        userRequestDto.setPassword("1234qwer");

        User user = new User("rjsdn123", "hashedPassword");


        when(userRepository.findByUsername("rjsdn123")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234qwer", "hashedPassword")).thenReturn(true);

        // When
        userService.login(userRequestDto);

        // Then
        verify(userRepository, times(1)).findByUsername("rjsdn123");
        verify(passwordEncoder, times(1)).matches("1234qwer", "hashedPassword");
    }

    @Test
    @DisplayName("올바르지 않은 로그인 했을 때 테스트")
    void test4() {

        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("rjsdn123");
        userRequestDto.setPassword("1234qwer5");

        User user = new User("rjsdn123", "hashedPassword");

        // When
        when(userRepository.findByUsername("rjsdn123")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234qwer5", "hashedPassword")).thenReturn(false);

        // Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(userRequestDto));
        verify(userRepository, times(1)).findByUsername("rjsdn123");
        verify(passwordEncoder, times(1)).matches("1234qwer5", "hashedPassword");
    }
}
