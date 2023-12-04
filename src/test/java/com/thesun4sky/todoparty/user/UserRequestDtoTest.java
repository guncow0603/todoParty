package com.thesun4sky.todoparty.user;  // 패키지 선언

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserRequestDtoTest {

    @Test
    @DisplayName("유효한 사용자 이름과 비밀번호")
    void test1() {
        // Given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("rjsdn123");
        userRequestDto.setPassword("rjs12345");

        // When

        // Then
        assertAll(
                () -> assertDoesNotThrow(() -> {
                    // 객체가 정상적으로 생성되었는지 확인
                    assertNotNull(userRequestDto);
                }),
                () -> assertEquals("rjsdn123", userRequestDto.getUsername()),
                () -> assertEquals("rjs12345", userRequestDto.getPassword())
        );
    }
}
