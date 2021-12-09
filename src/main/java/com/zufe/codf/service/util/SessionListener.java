package com.zufe.codf.service.util;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/*
 * @author 应涛
 * @date 2021/8/10
 * @function：监听session
 * */
@WebListener
public class SessionListener implements HttpSessionListener {

    private SessionContextUtils sessionContext= SessionContextUtils.getInstance();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        sessionContext.addSession(session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        sessionContext.delSession(session);
    }
}

