package com.band.api.resolvers;

import com.band.api.Exceptions.CustomException;
import com.band.api.domain.User;
import com.band.api.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Mutation implements GraphQLMutationResolver {


    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public Mutation(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;

    }

    /**
     * Takes the following fields, returns created user from repository, if successful.
     * @param email
     * @param username
     * @param password
     * @param name
     * @return the created User
     */
    public User createUser(String email, String username, String password, String name) throws CustomException{
        User user = null;
        CustomException ex = null;

        try {
            verifyUsernameEmail(username,email);
            String passwordHash = encoder.encode(password);
            user = User.builder()
                    .emailSearch(email.toLowerCase())
                    .emailDisplay(email)
                    .username(username)
                    .passwordHash(passwordHash)
                    .name(name)
                    .build();
            userRepository.save(user);
        }
        catch (DataAccessResourceFailureException e){
            //Database Unavailable
            ex = new CustomException("Database Unavailable");
        }
        catch (CustomException e){
            ex = e;
        }
        catch (Exception e){
            ex = new CustomException(e.getMessage());
        }
        if(ex != null){
            log.debug(ex.toString());
            throw ex;
        }
        return user;
    }
    private void verifyUsernameEmail(String username, String email){
        CustomException ex = null;

        if (userRepository.findByEmailSearch(email) != null) {
            //Email already exists
            ex = new CustomException("User already exists");
            ex.addExtension("Email", email);
        }

        if (userRepository.findByUsername(username) != null) {
            //Username already exists
            if(ex == null) ex = new CustomException("User already exists");
            ex.addExtension("Username", username);
        }
        if(ex != null) {
            throw ex;
        }
    }
}



