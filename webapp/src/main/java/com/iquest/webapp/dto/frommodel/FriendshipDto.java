package com.iquest.webapp.dto.frommodel;

public class FriendshipDto {

    private UserDto requester;
    private UserDto friend;

    public UserDto getRequester() {
        return requester;
    }

    public void setRequester(UserDto requester) {
        this.requester = requester;
    }

    public UserDto getFriend() {
        return friend;
    }

    public void setFriend(UserDto friend) {
        this.friend = friend;
    }
}
