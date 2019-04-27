package com.band.api;

import com.band.api.domain.User;
import com.band.api.repository.UserRepository;
import com.band.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserService userService;
    UserRepository mockUserRepository;
    PasswordEncoder mockEncoder;

    @BeforeEach
    void setup() {
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(mockUserRepository, mockEncoder);
        when(mockEncoder.encode(Mockito.any(CharSequence.class))).thenReturn("TestHash");
    }

    @Test
    void createUserSaveToRepository() {
        String username = "myUsername";
        String password = "myPassword";
        String name = "myName";
        String email = "myEmail@gmail.com";

        doNothing().when(mockUserRepository.save());
        User u = userService.createUser(email, username, password, name);
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
        when(mockUserRepository.save(any(User.class))).thenThrow(DataAccessResourceFailureException.class);
        assertThrows(DataAccessResourceFailureException.class, () -> userService.createUser(email, username, password, name));

    }

    @Test
    void verifyNewUserDuplicateEmail() {

    }

    @Test
    void verifyNewUserDuplicateUsername() {

    }

    @Test
    void verifyNewUserSuccess() {

    }
}
