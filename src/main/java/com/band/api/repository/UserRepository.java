package com.band.api.repository;

import com.band.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findTopByUsername(String username);

    User findTopByEmailSearch(String email);


}
