package com.band.api;

import com.band.api.domain.User;
import com.band.api.exceptions.InvalidInputException;
import com.band.api.repository.UserRepository;
import com.band.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserService userService;
    UserRepository mockUserRepository;
    PasswordEncoder mockEncoder;
    String username = "myUsername";
    String password = "myPassword";
    String name = "myName";
    String email = "myEmail@gmail.com";

    @BeforeEach
    void setup() {
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(mockUserRepository, mockEncoder);
        when(mockEncoder.encode(any(String.class))).thenReturn("TestHash");
    }

    @Test
    void createUserSaveToRepository() {
        when(mockUserRepository.save(any(User.class))).thenReturn(any(User.class));
        User u = userService.createUser(email, username, password, name);
        assertThat(u.getUsername(), is(username));
        assertThat(u.getName(), is(name));
        assertThat(u.getEmail(), is(email));
        assertThat(u.getPasswordHash(), is("TestHash"));
    }

    @Test
    void createUserDatabaseUnavailable() {
        doThrow(DataAccessResourceFailureException.class).when(mockUserRepository).save(any(User.class));
        //when(mockUserRepository.save(any(User.class))).thenThrow(DataAccessResourceFailureException.class);
        assertThrows(DataAccessResourceFailureException.class,
                () -> userService.createUser(email, username, password, name));

    }

    @Test
    void verifyNewUserDuplicateEmail() {
        when(mockUserRepository.findTopByEmailSearch(any(String.class))).thenReturn(User.builder().build());
        when(mockUserRepository.findTopByUsername(any(String.class))).thenReturn(null);
        assertThrows(InvalidInputException.class, () -> userService.verifyNewUser(username, email));
    }

    @Test
    void verifyNewUserDuplicateUsername() {
        when(mockUserRepository.findTopByUsername(any(String.class))).thenReturn(User.builder().build());
        when(mockUserRepository.findTopByEmailSearch(any(String.class))).thenReturn(null);
        assertThrows(InvalidInputException.class, () -> userService.verifyNewUser(username, email));
    }

    @Test
    void verifyNewUserSuccess() {
        when(mockUserRepository.findTopByUsername(any(String.class))).thenReturn(null);
        when(mockUserRepository.findTopByEmailSearch(any(String.class))).thenReturn(null);
        userService.verifyNewUser(username, email);
    }
}
