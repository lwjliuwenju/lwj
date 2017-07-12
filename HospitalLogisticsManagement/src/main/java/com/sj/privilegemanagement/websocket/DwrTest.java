package com.sj.privilegemanagement.websocket;

import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

public class DwrTest {
	public String test(String message) {
		System.out.println("get Message:" + message);
		sendMessageAuto("20", message);
		return "hello: " + message;
	}

	public void onPageLoad() {
		ScriptSession scriptSession = WebContextFactory.get()
				.getScriptSession();
		HttpSession session = WebContextFactory.get().getSession();
		Object userId = session.getAttribute("userId");
		scriptSession.setAttribute("userId", userId);
		DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();
		try {
			dwrScriptSessionManagerUtil.init();
		} catch (ServletException e) {
			e.printStackTrace();

		}

	}

	public static void sendMessageAuto(String userid, String message) {

		final String userId = userid;

		final String autoMessage = message;

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if (session.getAttribute("userId") == null)
					return false;
				else{
					return (session.getAttribute("userId")).toString().equals(userId);
				}

			}

		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			public void run() {
				script.appendCall("showMessage", autoMessage);
				Collection<ScriptSession> sessions = Browser
				.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
	//	刷新页面
	public static void refresh(String userid) {

		final String userId = userid;

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if (session.getAttribute("userId") == null)
					return false;
				else{
					System.out.println(userId + ";" + session.getAttribute("userId"));
					System.out.println(session.getAttribute("userId").toString().equals(userId));
					return (session.getAttribute("userId")).toString().equals(userId);
				}

			}

		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			public void run() {
				script.appendCall("refresh");
				Collection<ScriptSession> sessions = Browser
				.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}