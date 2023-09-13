package com.nerdz.nerdzapp.model.comment;

import com.nerdz.nerdzapp.model.user.Channel;
import com.nerdz.nerdzapp.model.video.Video;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity(name = "comment")
@Table(name = "comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = LAZY)
    private Channel commenter;
    @ManyToOne(fetch = LAZY)
    private Video video;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "parent_id")
    private List<Comment> replies;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Comment() {
    }

    public Comment(String content, Video video) {
        this.content = content;
        this.video = video;

        this.replies = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content.isBlank() || content.isEmpty()) throw new IllegalArgumentException("Content is required!");
        else this.content = content;
    }

    public Channel getCommenter() {
        return commenter;
    }

    public void setCommenter(Channel commenter) {
        if (commenter == null) throw new IllegalArgumentException();
        else this.commenter = commenter;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        if (video != null) {
            this.video = video;
        }
    }

    public List<Comment> getReplies() {
        return List.copyOf(replies);
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
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(content, comment.content) && Objects.equals(commenter, comment.commenter) &&
                Objects.equals(video, comment.video) && Objects.equals(replies, comment.replies) && Objects.equals(createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, commenter, video, replies, createdAt);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", commenter=" + commenter +
                ", video=" + video +
                ", replies=" + replies +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
