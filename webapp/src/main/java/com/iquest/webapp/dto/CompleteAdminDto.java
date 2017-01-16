package com.iquest.webapp.dto;

import com.iquest.webapp.dto.frommodel.AdminDto;
import com.iquest.webapp.dto.frommodel.FriendshipDto;

import java.util.List;

public class CompleteAdminDto {

    private AdminDto adminDto;
    private List<FriendshipDto> friendshipDtos;

    public AdminDto getAdminDto() {
        return adminDto;
    }

    public void setAdminDto(AdminDto adminDto) {
        this.adminDto = adminDto;
    }

    public List<FriendshipDto> getFriendshipDtos() {
        return friendshipDtos;
    }

    public void setFriendshipDtos(List<FriendshipDto> friendshipDtos) {
        this.friendshipDtos = friendshipDtos;
    }
}
