package com.limelight.server;

import org.springframework.data.repository.CrudRepository;

/**
 * Interface representing UserRepository that executes most database commands.
 * This interface is auto-implemented by Spring into a Bean called userRepository.
 */
public interface UserRepository extends CrudRepository<User, String> {

}
