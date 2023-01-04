package com.isang.api.repository;

import com.isang.api.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends CrudRepository<User,Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
}
