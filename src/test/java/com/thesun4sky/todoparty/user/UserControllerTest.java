package com.thesun4sky.todoparty.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesun4sky.todoparty.CommonResponseDto;
import com.thesun4sky.todoparty.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Mock 객체 초기화
        MockitoAnnotations.openMocks(this);
        // UserController 인스턴스 생성
        userController = new UserController(userService, jwtUtil);
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void test1() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("rjsdn1234");
        userRequestDto.setPassword("1qaz2wsx");

        // When
        ResponseEntity<CommonResponseDto> responseEntity = userController.signup(userRequestDto);

        // Then
        verify(userService, times(1)).signup(userRequestDto);
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
        assertEquals("회원가입 성공", responseEntity.getBody().getMsg());
    }

    @Test
    @DisplayName("중복된 username으로 회원가입 실패 테스트")
    void test2() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("duplicateUser");
        userRequestDto.setPassword("password");

        doThrow(new IllegalArgumentException("중복된 username 입니다.")).when(userService).signup(userRequestDto);

        // When
        ResponseEntity<CommonResponseDto> responseEntity = userController.signup(userRequestDto);

        // Then
        verify(userService, times(1)).signup(userRequestDto);
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals("중복된 username 입니다.", responseEntity.getBody().getMsg());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void test3() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("rjsdn1234");
        userRequestDto.setPassword("password");

        doNothing().when(userService).login(userRequestDto);

        when(jwtUtil.createToken("rjsdn1234")).thenReturn("Token");

        // HttpServletResponse mock 객체 생성
        HttpServletResponse response = mock(HttpServletResponse.class);

        // When
        ResponseEntity<CommonResponseDto> responseEntity = userController.login(userRequestDto, response);

        // Then

        verify(userService, times(1)).login(userRequestDto);
        verify(response, times(1)).setHeader(JwtUtil.AUTHORIZATION_HEADER, "Token");
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("로그인 성공", responseEntity.getBody().getMsg());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void test4() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("rjsdn1234");
        userRequestDto.setPassword("1qaz2wsx3ed");

        doThrow(new IllegalArgumentException("비밀번호가 일치하지 않습니다.")).when(userService).login(userRequestDto);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // When
        ResponseEntity<CommonResponseDto> responseEntity = userController.login(userRequestDto, response);

        // Then
        verify(userService, times(1)).login(userRequestDto);
        verify(response, never()).setHeader(any(), any());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals("비밀번호가 일치하지 않습니다.", responseEntity.getBody().getMsg());
    }
}

