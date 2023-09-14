package com.nerdz.nerdzapp.repository;

import com.nerdz.nerdzapp.model.user.Channel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Long> {
}
