package com.nerdz.nerdzapp.model.comment;

import com.nerdz.nerdzapp.model.user.Channel;
import com.nerdz.nerdzapp.model.video.LikeType;
import jakarta.persistence.*;

import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Entity(name = "CommentLike")
@Table(name = "comment_like")
public class CommentLike {

    @EmbeddedId
    private UserCommentID id;

    @ManyToOne
    @MapsId("userId")
    private Channel user;
    @ManyToOne
    @MapsId("commentId")
    private Comment comment;
    @Enumerated(STRING)
    private LikeType type;

    public CommentLike() {
    }

    public CommentLike(Channel user, Comment comment, LikeType type) {
        this.user = user;
        this.comment = comment;
        this.type = type;

        this.id = new UserCommentID(user.getId(), comment.getId());
    }

    public UserCommentID getId() {
        return id;
    }

    public Channel getUser() {
        return user;
    }

    public Comment getComment() {
        return comment;
    }

    public LikeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentLike that = (CommentLike) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(comment, that.comment) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, comment, type);
    }
}
