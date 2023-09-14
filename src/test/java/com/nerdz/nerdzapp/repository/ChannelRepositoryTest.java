package com.nerdz.nerdzapp.repository;

import com.nerdz.nerdzapp.model.user.AppUser;
import com.nerdz.nerdzapp.model.user.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepo;
    @Autowired
    private AppUserRepository userRepo;


    @Test
    public void itShouldCreateChannelWithUserAndFindEachOneInDB() {
        Channel channel = new Channel("my-email", "test", "test-channel");
        channelRepo.save(channel);

        userRepo.findUserByEmail(channel.getOwner().getUsername());
    }

}
