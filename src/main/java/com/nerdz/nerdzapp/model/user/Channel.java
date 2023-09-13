package com.nerdz.nerdzapp.model.user;

import com.nerdz.nerdzapp.model.comment.Comment;
import com.nerdz.nerdzapp.model.video.Playlist;
import com.nerdz.nerdzapp.model.video.Video;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity(name = "channel")
@Table(name = "channel")
public class Channel implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;

    @OneToOne(fetch = LAZY, optional = false, orphanRemoval = true)
    private AppUser owner;
    @OneToMany
    @JoinTable(
            name = "channel_subscriptions",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"),
            uniqueConstraints = @UniqueConstraint(name = "unique_constraint", columnNames = {"channel_id", "subscriber_id"})
    )
    private Set<Channel> subscribers;
    @OneToMany(mappedBy = "channel", orphanRemoval = true)
    private List<Video> videos;
    @OneToMany(mappedBy = "channel", orphanRemoval = true)
    private List<Playlist> playlists;
    @OneToMany(mappedBy = "commenter", orphanRemoval = true)
    private List<Comment> comments;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Channel() {
    }

    // when user is creating, channel is created automatically, there is no user without channel
    public Channel(String name, AppUser owner) {
        this.name = name;
        this.owner = owner;

        this.subscribers = new HashSet<>();
        this.videos = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.comments = new LinkedList<>();
    }

    public void subscribeChannel(Channel channel) {
        channel.addSubscriber(this);
    }

    private void addSubscriber(Channel channel) {
        if (channel != null) subscribers.add(channel);
    }

    public void uploadVideo(Video video) {
        if (video != null) {
            videos.add(video);
            video.setChannel(this);
        }
    }

    public void createPlaylist(Playlist playlist) {
        if (playlist != null) {
            playlists.add(playlist);
            playlist.setChannel(this);
        }
    }

    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
            comment.setCommenter(this);
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

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        if (owner == null) throw new IllegalArgumentException("Owner cannot be null!");
        else this.owner = owner;
    }

    public Set<Channel> getSubscribers() {
        return Set.copyOf(subscribers);
    }

    public List<Video> getVideos() {
        return List.copyOf(videos);
    }

    public List<Playlist> getPlaylists() {
        return List.copyOf(playlists);
    }

    public List<Comment> getComments() {
        return List.copyOf(comments);
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
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id) && Objects.equals(name, channel.name) && Objects.equals(owner, channel.owner) &&
                Objects.equals(subscribers, channel.subscribers) && Objects.equals(videos, channel.videos) && Objects.equals(playlists, channel.playlists) &&
                Objects.equals(createdAt, channel.createdAt) && Objects.equals(comments, channel.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, subscribers, videos, playlists, comments, createdAt);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", createdAt=" + createdAt +
                '}';
    }
}
