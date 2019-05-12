package com.band.api.services;

import com.band.api.domain.User;
import com.band.api.exceptions.InvalidInputException;
import com.band.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.encoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Creates the user and inserts it into the database
     *
     * @param email
     * @param username
     * @param password
     * @param name
     * @return created user if save is successful
     * @throws org.springframework.dao.DataAccessResourceFailureException if database not available
     */
    public User createUser(String email, String username, String password, String name) {
        User user = User.builder()
                .emailSearch(email.toLowerCase())
                .email(email)
                .username(username)
                .passwordHash(encoder.encode(password))
                .name(name)
                .build();
        userRepository.save(user);
        return user;
    }

    /**
     * Verifies that there is no user in the database before with the identical username or email before creating the user
     *
     * @param username
     * @param email
     * @throws InvalidInputException when the given username or email already exist in the database
     */
    public void verifyNewUser(String username, String email) {
        InvalidInputException ex = null;

        if (userRepository.findTopByEmailSearch(email) != null) {
            //Email already exists
            ex = new InvalidInputException("User already exists");
            ex.addExtension("Email", email);
        }

        if (userRepository.findTopByUsername(username) != null) {
            //Username already exists
            if (ex == null) {
                ex = new InvalidInputException("User already exists");
            }
            ex.addExtension("Username", username);
        }
        if (ex != null) {
            throw ex;
        }
    }

    public User login(String username, String password) {
        User u = userRepository.findTopByUsername(username);

        if (u == null || !encoder.matches(password, u.getPasswordHash())) {
            throw new InvalidInputException("Invalid Credentials");
        }
        u.setToken(jwtService.createToken(u));
        return u;
    }
}
