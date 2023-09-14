package com.nerdz.nerdzapp.repository;

import com.nerdz.nerdzapp.model.user.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findUserByEmail(String email);
}
