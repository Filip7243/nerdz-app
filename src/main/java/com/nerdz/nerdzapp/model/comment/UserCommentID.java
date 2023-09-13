package com.nerdz.nerdzapp.model.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserCommentID implements Serializable {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "comment_id")
    private Long commentId;

    public UserCommentID() {
    }

    public UserCommentID(Long userId, Long commentId) {
        this.userId = userId;
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCommentID that = (UserCommentID) o;
        return Objects.equals(userId, that.userId) && Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, commentId);
    }
}
