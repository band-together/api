package com.band.api.resolvers;

import com.band.api.domain.User;
import com.band.api.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class Mutation implements GraphQLMutationResolver {


    private final UserRepository userRepository;

    @Autowired
    public Mutation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Takes the following fields, returns created user from repository, if successful.
     * @param email
     * @param username
     * @param password
     * @param name
     * @return the created User
     */
    public User createUser(String email, String username, String password, String name){

        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordHash = bc.encode(password);
        User user = User.builder()
                    .email(email)
                    .username(username)
                    .passwordHash(passwordHash)
                    .name(name)
                    .build();
        try {
            userRepository.save(user);
        }
        catch (Exception e){
            //TODO return some error
            //Possible exceptions: Duplicate username or email.
            return new User();
        }
        return user;
    }
}
