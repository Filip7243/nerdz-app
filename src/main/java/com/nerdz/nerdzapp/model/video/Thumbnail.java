package com.nerdz.nerdzapp.model.video;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity(name = "thumbnail")
@Table(name = "thumbnail")
public class Thumbnail implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Lob
    private byte[] image;
    @OneToOne(mappedBy = "thumbnail", optional = false)
    private Video video;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Thumbnail() {
    }

    public Thumbnail(byte[] image, Video video) {
        this.image = image;
        this.video = video;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return Arrays.copyOf(image, image.length);
    }

    public void setImage(byte[] image) {
        if (image.length > 0) this.image = image;
        else throw new IllegalArgumentException("Image is required!");
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        if (this.video != null) this.video = video;
        else throw new IllegalArgumentException("Video cannot be null!");
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
        Thumbnail thumbnail = (Thumbnail) o;
        return Objects.equals(id, thumbnail.id) && Arrays.equals(image, thumbnail.image) &&
                Objects.equals(video, thumbnail.video) && Objects.equals(createdAt, thumbnail.createdAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, video, createdAt);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "Thumbnail{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
