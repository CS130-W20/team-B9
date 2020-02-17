package com.limelight.server;

import org.springframework.data.repository.CrudRepository;

import com.limelight.server.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository

public interface UserRepository extends CrudRepository<User, String> {

}
