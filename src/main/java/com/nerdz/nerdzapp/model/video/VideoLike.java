package com.nerdz.nerdzapp.model.video;

import com.nerdz.nerdzapp.model.user.Channel;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;

@Entity(name = "VideoLike")
@Table(name = "video_like")
public class VideoLike implements Serializable {

    @EmbeddedId
    private UserVideoID id;

    @ManyToOne(fetch = EAGER)
    @MapsId("videoId")
    private Video video;
    @ManyToOne(fetch = EAGER)
    @MapsId("channelId")
    private Channel channel;
    @Enumerated(STRING)
    private transient LikeType type;

    public VideoLike() {
    }

    public VideoLike(Video video, Channel channel, LikeType type) {
        this.video = video;
        this.channel = channel;
        this.type = type;

        this.id = new UserVideoID(channel.getId(), video.getId());
    }

    public UserVideoID getId() {
        return id;
    }

    public void setId(UserVideoID id) {
        this.id = id;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public LikeType getType() {
        return type;
    }

    public void setType(LikeType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoLike videoLike = (VideoLike) o;
        return Objects.equals(id, videoLike.id) && Objects.equals(video, videoLike.video) &&
                Objects.equals(channel, videoLike.channel) && Objects.equals(type, videoLike.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, video, channel, type);
    }

    @Override
    public String toString() {
        return "VideoLike{" +
                "id=" + id +
                ", video=" + video +
                ", channel=" + channel +
                ", type=" + type +
                '}';
    }
}
