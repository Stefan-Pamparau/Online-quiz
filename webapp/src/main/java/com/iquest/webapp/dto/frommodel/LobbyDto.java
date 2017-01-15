package com.iquest.webapp.dto.frommodel;

import java.util.Date;

public class LobbyDto {

    private Integer id;
    private Date creationDate;
    private Integer secondsUntilStart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getSecondsUntilStart() {
        return secondsUntilStart;
    }

    public void setSecondsUntilStart(Integer secondsUntilStart) {
        this.secondsUntilStart = secondsUntilStart;
    }
}
