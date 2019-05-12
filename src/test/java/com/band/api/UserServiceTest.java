package com.band.api;

import com.auth0.jwt.JWT;
import com.band.api.domain.User;
import com.band.api.exceptions.BaseGraphQLException;
import com.band.api.exceptions.InvalidInputException;
import com.band.api.repository.UserRepository;
import com.band.api.services.JWTService;
import com.band.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserService userService;
    UserRepository mockUserRepository;
    PasswordEncoder mockEncoder;
    JWTService mockJwtService;
    String username = "myUsername";
    String password = "myPassword";
    String name = "myName";
    String email = "myEmail@gmail.com";

    @BeforeEach
    void setup() {
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockEncoder = Mockito.mock(PasswordEncoder.class);
        mockJwtService = Mockito.mock(JWTService.class);
        userService = new UserService(mockUserRepository, mockEncoder, mockJwtService);
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
        when(mockUserRepository.save(any(User.class))).thenThrow(DataAccessResourceFailureException.class);
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

    @Test
    void loginSuccess(){
        when(mockUserRepository.findTopByUsername(username)).thenReturn(User.builder().build());
        when(mockEncoder.matches(anyString(), any())).thenReturn(true);
        when(mockJwtService.createToken(any(User.class))).thenReturn("MyToken");
        User u = userService.login(username, password);
        assertThat(u, samePropertyValuesAs(User.builder().token("MyToken").build()));
    }

    @Test
    void loginInvalidUsernameOrPassword() {
        when(mockUserRepository.findTopByUsername(username)).thenReturn(null);
        assertThrows(InvalidInputException.class, () -> userService.login(username, password));
    }

    @Test
    void loginDatabaseUnavailable(){
        when(mockUserRepository.findTopByUsername(username)).thenThrow(DataAccessResourceFailureException.class);
        assertThrows(DataAccessResourceFailureException.class, () -> userService.login(username, password));
    }
}
