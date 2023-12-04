package com.thesun4sky.todoparty.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    @Test
    @DisplayName("생성자 및 게터 테스트")
    void test1() {
        User user1 = new User("user1", "password1");
        User user2 = new User("user1", "password1");
        User user3 = new User("user2", "password2");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }
}
