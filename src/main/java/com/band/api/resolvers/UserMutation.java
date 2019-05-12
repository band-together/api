package com.band.api.resolvers;

import com.band.api.domain.User;
import com.band.api.exceptions.BaseGraphQLException;
import com.band.api.exceptions.InvalidInputException;
import com.band.api.services.UserService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;

/**
 * Handles all graphql mutation requests involving the User
 */
@Slf4j
@Component
public class UserMutation implements GraphQLMutationResolver {
    private final UserService userService;

    @Autowired
    public UserMutation(UserService userService) {
        this.userService = userService;
    }

    /**
     * Takes the following fields, returns created user if save to repository is successful.
     *
     * @param email
     * @param username
     * @param password
     * @param name
     * @return the created User
     */
    public User createUser(String email, String username, String password, String name) {
        try {
            userService.verifyNewUser(username, email);
            return userService.createUser(email, username, password, name);
        } catch (DataAccessResourceFailureException e) {
            //Database Unavailable
            log.debug(e.toString());
            BaseGraphQLException ex = new BaseGraphQLException("Database Unavailable");
            ex.addExtension("Database", "Unavailable");
            throw ex;
        } catch (InvalidInputException e) {
            throw e;
        }
    }

    public User login(String username, String password) {
        try {
            User u = userService.login(username, password);
            return u;
        } catch (DataAccessResourceFailureException e) {
            //Database Unavailable
            log.debug(e.toString());
            BaseGraphQLException ex = new BaseGraphQLException("Database Unavailable");
            ex.addExtension("Database", "Unavailable");
            throw ex;
        }
    }
}
