package com.band.api.resolvers;

import com.band.api.domain.User;
import com.band.api.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private UserRepository userRepository;

    public Query (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUser(Integer id){
        User u ;
        try {
            u = userRepository.findById(id).get();
        }
        catch (Exception e){
            return User.builder().id(100).username("MyName").email("Failed@gmail.com").passwordHash("0123456789ABCDEF").name(e.getMessage()).build();
        }
        return u;
    }
}
