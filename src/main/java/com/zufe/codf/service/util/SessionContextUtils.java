package com.zufe.codf.service.util;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
/*
 * @author 应涛
 * @date 2021/8/10
 * @function：SessionContext用于获取session 根据sessionId获取session
 * */

public class SessionContextUtils {
    private static SessionContextUtils instance;
    private HashMap<String, HttpSession> sessionMap;

    private SessionContextUtils() {
        sessionMap = new HashMap<String,HttpSession>();
    }

    public static SessionContextUtils getInstance() {
        if (instance == null) {
            instance = new SessionContextUtils();
        }
        return instance;
    }

    public synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }

    public synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String sessionID) {
        if (sessionID == null) {
            return null;
        }
        return sessionMap.get(sessionID);
    }
}
