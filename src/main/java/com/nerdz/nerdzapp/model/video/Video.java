package com.nerdz.nerdzapp.model.video;

import com.nerdz.nerdzapp.model.comment.Comment;
import com.nerdz.nerdzapp.model.user.Channel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity(name = "video")
@Table(name = "video")
public class Video implements Serializable, Comparable<Video> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long views;
    private String videoUrl; // video will be uploaded from remote server like aws or smthing like that
    private VideoStatus videoStatus;

    @OneToOne(fetch = LAZY, optional = false, orphanRemoval = true)
    private Thumbnail thumbnail;
    @ManyToOne(fetch = LAZY)
    private Channel channel;
    @ManyToMany
    @JoinTable(
            name = "videos_categories",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(name = "unique_categories", columnNames = {"video_id", "category_id"})
    )
    private Set<Category> categories;
    @OneToMany(mappedBy = "video")
    private List<Comment> comments;
    @ManyToMany(mappedBy = "videos")
    private Set<Playlist> playlists;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Video() {
    }

    public Video(String title, String description, Thumbnail thumbnail, String videoUrl) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.videoUrl = videoUrl;
        this.videoStatus = VideoStatus.PRIVATE;

        this.views = 0L;
        this.categories = new HashSet<>();
        this.comments = new LinkedList<>();
        this.playlists = new HashSet<>();
    }

    void addToPlaylists(Playlist playlist) {
        if (playlist != null) {
            playlists.add(playlist);
        }
    }

    public void addCategory(Category category) {
        if (category != null) {
            categories.add(category);
            category.addVideo(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.isEmpty() || title.isBlank()) throw new IllegalArgumentException("Title is required!");
        else this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.isEmpty() || description.isBlank()) throw new IllegalArgumentException("Description is required!");
        else this.description = description;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        if (views < 0L) {
            this.views = 0L;
            throw new IllegalArgumentException("Views cannot be negative!");
        }

        this.views = views;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        if (thumbnail != null) {
            this.thumbnail = thumbnail;
            thumbnail.setVideo(this);
        } else throw new IllegalArgumentException("Bad thumbnail!");
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        if (videoUrl.isBlank() || videoUrl.isEmpty()) throw new IllegalArgumentException("Title is required!");
        else this.videoUrl = videoUrl;
    }

    public VideoStatus getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(VideoStatus videoStatus) {
        this.videoStatus = videoStatus;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        if (channel != null) this.channel = channel;
        else throw new IllegalArgumentException("Bad channel!");
    }

    public Set<Category> getCategories() {
        return Set.copyOf(categories);
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
    public int compareTo(Video o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(id, video.id) && Objects.equals(title, video.title) && Objects.equals(description, video.description) &&
                Objects.equals(views, video.views) && Objects.equals(videoUrl, video.videoUrl) && videoStatus == video.videoStatus &&
                Objects.equals(thumbnail, video.thumbnail) && Objects.equals(channel, video.channel) && Objects.equals(categories, video.categories) &&
                Objects.equals(comments, video.comments) && Objects.equals(createdAt, video.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, views, videoUrl, videoStatus, thumbnail, channel, categories, comments, createdAt);
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", videoUrl='" + videoUrl + '\'' +
                ", videoStatus=" + videoStatus +
                ", thumbnail=" + thumbnail +
                ", channel=" + channel +
                ", categories=" + categories +
                ", comments=" + comments +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
