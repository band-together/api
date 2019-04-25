package com.band.api.repository;

import com.band.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { // extends CrudRepository<User, Integer>{

    User findByUsername(String username);
    User findByEmailSearch(String email);
}
