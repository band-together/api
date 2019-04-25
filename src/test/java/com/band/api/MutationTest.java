package com.band.api;

import com.band.api.Exceptions.CustomException;
import com.band.api.domain.User;
import com.band.api.repository.UserRepository;
import com.band.api.resolvers.Mutation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class MutationTest {
    static UserRepository mockUserRepository;
    static PasswordEncoder mockPasswordEncoder;
    static Mutation mutation;

    @Test
    @BeforeAll
    static void setup(){
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockPasswordEncoder = Mockito.mock(PasswordEncoder.class);
        when(mockPasswordEncoder.encode(any(String.class))).thenReturn("testEncode");
        mutation = new Mutation(mockUserRepository, mockPasswordEncoder);
    }

    @Test
    void createUserSavesToRepository() {
        String username = "myUsername", password = "myPassword", name = "myName", email = "myEmail@gmail.com";
        mutation.createUser(email, username, password, name);
        // Capture the argument of the save function
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(mockUserRepository).save(captor.capture());

        User user = captor.getValue();
        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmailDisplay());
        assertEquals("testEncode", user.getPasswordHash());
    }

    @Test
    void createUserDuplicateUsername(){
        String username = "myUsername", password = "myPassword", name = "myName", email = "myEmail@gmail.com";
        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(User.builder().build());
        assertThrows(CustomException.class,()-> mutation.createUser(email, username, password, name));
    }

    @Test
    void createUserDuplicateEmail(){
        String username = "myUsername", password = "myPassword", name = "myName", email = "myEmail@gmail.com";
        when(mockUserRepository.findByEmailSearch(any(String.class))).thenReturn(User.builder().build());
        assertThrows(CustomException.class,()-> mutation.createUser(email, username, password, name));
    }

    @Test
    void createUserDatabaseUnavailable(){
        String username = "myUsername", password = "myPassword", name = "myName", email = "myEmail@gmail.com";
        when(mockUserRepository.save(any(User.class))).thenThrow(DataAccessResourceFailureException.class);
        assertThrows(CustomException.class, () -> mutation.createUser(email, username, password, name));
    }
}
