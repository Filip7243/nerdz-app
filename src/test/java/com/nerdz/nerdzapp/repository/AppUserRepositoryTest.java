package com.nerdz.nerdzapp.repository;

import com.nerdz.nerdzapp.model.user.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository userRepo;


    @Test
    public void itShouldSaveAndFindUserByUsername() {
        AppUser user = new AppUser("test", "test");
        userRepo.save(user);

        Optional<AppUser> foundUser = userRepo.findUserByEmail(user.getUsername());

        assertThat(foundUser).isPresent();
    }

    @Test
    public void itShouldNotSaveAndFindUserByUsername() {
        Optional<AppUser> foundUser = userRepo.findUserByEmail("fake-username");

        assertThat(foundUser).isEmpty();
    }


}
