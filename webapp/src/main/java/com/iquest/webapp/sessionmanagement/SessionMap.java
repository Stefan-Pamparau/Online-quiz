package com.iquest.webapp.sessionmanagement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMap {

    private static SessionMap instance;

    private static Map<String, Session> sessionMap;

    private SessionMap() {
        sessionMap = new ConcurrentHashMap<>();
    }

    public synchronized SessionMap getInstance() {
        if (instance == null) {
            instance = new SessionMap();
        }

        return instance;
    }

    public static Session get(String key) {
        return sessionMap.get(key);
    }

    public static void put(String key, Session session) {
        sessionMap.put(key, session);
    }
}
