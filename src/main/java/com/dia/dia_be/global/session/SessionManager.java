package com.dia.dia_be.global.session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SessionManager implements HttpSessionListener {
	private static final ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

	// 세션 생성 및 등록
	public void sessionCreated(HttpSession session) {
		sessions.put(session.getId(), session);
	}

	// 세션 제거 (로그아웃이나 만료 시 호출)
	public void sessionDestroyed(HttpSession session) {
		sessions.remove(session.getId());
	}

	public static HttpSession getSession(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String sessionToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("JSESSIONID".equals(cookie.getName())) {
					sessionToken = cookie.getValue();
					break;  // 쿠키를 찾으면 루프 종료
				}
			}
		}

		// 세션 토큰이 없으면 세션을 찾을 수 없으므로 에러 처리
		if (sessionToken == null) {
			return null;
		}

		return sessions.get(sessionToken);  // JSESSIONID로 세션 조회
	}
}

