package com.thesun4sky.todoparty.user;  // 패키지 선언

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UserDTOTest {

    @Test
    @DisplayName("UserDTO 생성 - 사용자가 주어진 경우")
    void test1() {
        // Given
        User user = new User();
        user.setUsername("rjsdn1234");

        // When:
        UserDTO userDTO = new UserDTO(user);

        assertEquals("rjsdn1234", userDTO.getUsername());
    }

}
