package com.iquest.model.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friendship")
public class Friendship {
    @EmbeddedId
    private FriendshipId id = new FriendshipId();

    @ManyToOne
    @MapsId("requesterId")
    private User requester;

    @ManyToOne
    @MapsId("friendId")
    private User friend;

    public FriendshipId getId() {
        return id;
    }

    public void setId(FriendshipId id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friendship that = (Friendship) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Embeddable
    public static class FriendshipId implements Serializable {
        private Integer requesterId;
        private Integer friendId;

        public Integer getRequesterId() {
            return requesterId;
        }

        public void setRequesterId(Integer requesterId) {
            this.requesterId = requesterId;
        }

        public Integer getFriendId() {
            return friendId;
        }

        public void setFriendId(Integer friendId) {
            this.friendId = friendId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FriendshipId that = (FriendshipId) o;

            if (requesterId != null ? !requesterId.equals(that.requesterId) : that.requesterId != null) return false;
            return friendId != null ? friendId.equals(that.friendId) : that.friendId == null;

        }

        @Override
        public int hashCode() {
            int result = requesterId != null ? requesterId.hashCode() : 0;
            result = 31 * result + (friendId != null ? friendId.hashCode() : 0);
            return result;
        }
    }
}
