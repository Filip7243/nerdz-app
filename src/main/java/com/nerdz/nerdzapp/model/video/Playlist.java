package com.nerdz.nerdzapp.model.video;

import com.nerdz.nerdzapp.model.user.Channel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity(name = "playlist")
@Table(name = "playlist")
public class Playlist implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = LAZY)
    private Channel channel;
    @ManyToMany
    @JoinTable(
            name = "playlists_videoes",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id"),
            uniqueConstraints = @UniqueConstraint(name = "unique_videos_in_playlist", columnNames = {"playlist_id", "video_id"})
    )
    private Set<Video> videos;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Playlist() {
    }

    public Playlist(String name) {
        this.name = name;

        this.videos = new TreeSet<>(); // todo: implement comaprable in video
    }

    public void addVideo(Video video) {
        if (video != null) {
            videos.add(video);
            video.addToPlaylists(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty() || name.isBlank()) throw new IllegalArgumentException("Name is required!");
        else this.name = name;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        if (channel != null) {
            this.channel = channel;
        }
    }

    public Set<Video> getVideos() {
        return Set.copyOf(videos);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt.isBefore(this.updatedAt))
            throw new IllegalArgumentException("Updated date cannot be before current update date");
        else this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id) && Objects.equals(name, playlist.name) && Objects.equals(channel, playlist.channel) &&
                Objects.equals(videos, playlist.videos) && Objects.equals(createdAt, playlist.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, channel, videos, createdAt);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", channel=" + channel +
                ", videos=" + videos +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
