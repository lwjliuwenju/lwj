package com.sj.privilegemanagement.websocket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Container;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.DwrServlet;

public class DwrScriptSessionManagerUtil extends DwrServlet{

       private static final long serialVersionUID = -7504612622407420071L;
       public void init()
       throws ServletException {
              Container container = ServerContextFactory.get().getContainer();
              ScriptSessionManager manager = container
                            .getBean(ScriptSessionManager.class);
              ScriptSessionListener listener = new ScriptSessionListener() {
                     public void sessionCreated(ScriptSessionEvent ev) {
                            HttpSession session = WebContextFactory.get().getSession();
                            Object scriptSession = session.getAttribute("scriptSession");
                            if(scriptSession == null)
                            	session.setAttribute("scriptSession", ev.getSession());
                            else
                            	ev.getSession().setAttribute("userId", ((ScriptSession)scriptSession).getAttribute("userId"));

                     }
                     public void sessionDestroyed(ScriptSessionEvent ev) {
                            System.out.println("a ScriptSession is distroyed");
                     }
              };
              manager.addScriptSessionListener(listener);
       }
}