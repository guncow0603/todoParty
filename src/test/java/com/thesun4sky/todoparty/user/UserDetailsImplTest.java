package com.thesun4sky.todoparty.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        // Given
        User user = new User("rjsdn123", "1qaz2wsx");
        userDetails = new UserDetailsImpl(user);
    }

    @Test
    @DisplayName("사용자 상세 정보 - 권한 테스트")
    void test1() {
        // When
        var authorities = userDetails.getAuthorities();

        // Then
        assertEquals(null, authorities);
    }

    @Test
    @DisplayName("사용자 상세 정보 - 비밀번호 테스트")
    void test2() {
        // When
        var password = userDetails.getPassword();

        // Then
        assertEquals("1qaz2wsx", password);
    }

    @Test
    @DisplayName("사용자 상세 정보 - 사용자 이름 테스트")
    void test3() {
        // When
        var username = userDetails.getUsername();

        // Then
        assertEquals("rjsdn123", username);
    }

    @Test
    @DisplayName("사용자 상세 정보 - 계정 만료 여부 테스트")
    void test4() {
        // When
        var accountNonExpired = userDetails.isAccountNonExpired();

        // Then
        assertEquals(false, accountNonExpired);
    }

    @Test
    @DisplayName("사용자 상세 정보 - 계정 잠김 여부 테스트")
    void test5() {
        // When
        var accountNonLocked = userDetails.isAccountNonLocked();

        // Then
        assertEquals(false, accountNonLocked);
    }

    @Test
    @DisplayName("사용자 상세 정보 - 자격 증명 만료 여부 테스트")
    void test6() {
        // When
        var credentialsNonExpired = userDetails.isCredentialsNonExpired();

        // Then
        assertEquals(false, credentialsNonExpired);
    }

    @Test
    @DisplayName("사용자 상세 정보 - 사용 가능 여부 테스트")
    void test7() {
        // When
        var enabled = userDetails.isEnabled();

        // Then
        assertEquals(false, enabled);
    }
}
