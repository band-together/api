package com.band.api.resolvers;

import com.band.api.domain.User;
import com.band.api.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    private final UserRepository userRepository;

    @Autowired
    public Query (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Takes ID of user, returns user with that ID if it exists in repository.
     * @param id
     * @return found User
     */
    public User getUser(Integer id){
        User u;
        try {
            u = userRepository.findById(id).get();
        }
        catch (Exception e){
            //TODO Handle the exception
            //Possible exceptions, user not found, connection to database down, etc.
            return User.builder().name(e.getMessage()).build();
        }
        return u;
    }
}
