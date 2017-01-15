package com.iquest.webapp.controllers;

import com.iquest.webapp.sessionmanagement.Session;
import com.iquest.webapp.sessionmanagement.SessionMap;

public abstract class AbstractController {

    protected Session getUserSession(String email) {
        Session session = SessionMap.getInstance().get(email);
        if (session == null) {
            session = new Session();
        }
        return session;
    }
}
