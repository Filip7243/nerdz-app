package com.nerdz.nerdzapp.model.user;

import com.nerdz.nerdzapp.model.comment.Comment;
import com.nerdz.nerdzapp.model.video.Playlist;
import com.nerdz.nerdzapp.model.video.Video;

public interface UserEngagement {

    void subscribeChannel(Channel channel);
    void createPlaylist(Playlist playlist);
    void uploadVideo(Video video);
}
