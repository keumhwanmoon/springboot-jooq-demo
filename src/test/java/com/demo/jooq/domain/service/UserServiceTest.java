package com.demo.jooq.domain.service;

import com.demo.jooq.domain.models.UserRes;
import com.demo.jooq.domain.repositories.UserDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("모든 사용자 목록을 정상적으로 조회할 수 있다")
    void getAll_ShouldReturnAllUsers() {
        // given
        List<UserRes> expectedUsers = createSampleUsers();
        when(userDAO.findAllUsers()).thenReturn(expectedUsers);

        // when
        List<UserRes> actualUsers = userService.getAll();

        // then
        assertThat(actualUsers).isNotNull();
        assertThat(actualUsers).hasSize(2);
        assertThat(actualUsers.get(0).getLoginId()).isEqualTo("test1");
        assertThat(actualUsers.get(1).getLoginId()).isEqualTo("test2");
    }

    private List<UserRes> createSampleUsers() {
        UserRes user1 = new UserRes();
        ReflectionTestUtils.setField(user1, "id", 1L);
        ReflectionTestUtils.setField(user1, "loginId", "test1");
        ReflectionTestUtils.setField(user1, "userName", "Test User 1");
        ReflectionTestUtils.setField(user1, "email", "test1@example.com");
        ReflectionTestUtils.setField(user1, "createdAt", LocalDateTime.now());

        UserRes user2 = new UserRes();
        ReflectionTestUtils.setField(user2, "id", 2L);
        ReflectionTestUtils.setField(user2, "loginId", "test2");
        ReflectionTestUtils.setField(user2, "userName", "Test User 2");
        ReflectionTestUtils.setField(user2, "email", "test2@example.com");
        ReflectionTestUtils.setField(user2, "createdAt", LocalDateTime.now());

        return Arrays.asList(user1, user2);
    }
}