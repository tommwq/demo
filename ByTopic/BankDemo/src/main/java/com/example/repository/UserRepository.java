package com.example.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT id,username,password FROM user WHERE username=?1", nativeQuery = true)
    Optional<User> lookupUser(String name);
}
