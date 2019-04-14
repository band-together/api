package com.band.api.resolvers;

import com.band.api.domain.User;
import com.band.api.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private UserRepository userRepository;

    public Mutation(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User createUser(String email, String username, String password, String name){

        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordHash = bc.encode(password);
        User user = User.builder()
                .email(email)
                .username(username)
                .passwordHash(passwordHash)
                .name(name)
                .build();

        userRepository.save(user);
        return user;
//        return User.builder().build();

    }
}
