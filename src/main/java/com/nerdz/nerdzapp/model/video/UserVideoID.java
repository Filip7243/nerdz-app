package com.nerdz.nerdzapp.model.video;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserVideoID implements Serializable {

    @Column(name = "chanel_id")
    private Long channelId;
    @Column(name = "video_id")
    private Long videoId;

    public UserVideoID() {
    }

    public UserVideoID(Long channelId, Long videoId) {
        this.channelId = channelId;
        this.videoId = videoId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Long getVideoId() {
        return videoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVideoID that = (UserVideoID) o;
        return Objects.equals(channelId, that.channelId) && Objects.equals(videoId, that.videoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, videoId);
    }
}
