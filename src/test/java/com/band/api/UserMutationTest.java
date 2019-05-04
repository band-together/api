package com.band.api;

import com.band.api.domain.User;
import com.band.api.exceptions.BaseGraphQLException;
import com.band.api.exceptions.InvalidInputException;
import com.band.api.resolvers.UserMutation;
import com.band.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessResourceFailureException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


class UserMutationTest {
    private UserService mockUserService;
    private UserMutation userMutation;

    @BeforeEach
    void setup() {
        mockUserService = Mockito.mock(UserService.class);
        userMutation = new UserMutation(mockUserService);
    }

    @Test
    void createUserDuplicateUsernameOrEmail() {
        String username = "myUsername";
        String password = "myPassword";
        String name = "myName";
        String email = "myEmail@gmail.com";
        doThrow(InvalidInputException.class).when(mockUserService).verifyNewUser(username, email);
        assertThrows(NullPointerException.class, () -> userMutation.createUser(email, username, password, name));
    }

    @Test
    void createUserSuccess() {
        String username = "myUsername";
        String password = "myPassword";
        String name = "myName";
        String email = "myEmail@gmail.com";
        when(mockUserService.createUser(email, username, password, name)).thenReturn(
                User.builder()
                        .id(1)
                        .username(username)
                        .email(email)
                        .emailSearch(email.toLowerCase())
                        .name(name)
                        .passwordHash("TestHash")
                        .build()
        );

        User u = userMutation.createUser(email, username, password, name);
        assertThat(u.getUsername(), is(username));
        assertThat(u.getName(), is(name));
        assertThat(u.getEmail(), is(email));
        assertThat(u.getPasswordHash(), is("TestHash"));
    }

    @Test
    void createUserDatabaseUnavailable() {
        String username = "myUsername";
        String password = "myPassword";
        String name = "myName";
        String email = "myEmail@gmail.com";
        doThrow(DataAccessResourceFailureException.class).when(mockUserService).verifyNewUser(any(String.class), any(String.class));
        assertThrows(BaseGraphQLException.class, () -> userMutation.createUser(email, username, password, name));
    }
}
