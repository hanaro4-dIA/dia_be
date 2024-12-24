package com.dia.dia_be.global.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
	private static final ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

	// 세션 생성 및 쿠키 설정
	public void createSession(HttpSession session, HttpServletResponse response) {
		sessions.put(session.getId(), session);

		// 쿠키 설정 (도메인 명시)
		Cookie cookie = new Cookie("JSESSIONID", session.getId());
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(7 * 24 * 60 * 60);  // 7일 유지
		cookie.setAttribute("SameSite", "None");  // 크로스 도메인 요청 허용
		response.addCookie(cookie);
	}

	// 세션 제거 (로그아웃이나 만료 시 호출)
	public void destroySession(HttpSession session) {
		sessions.remove(session.getId());
	}

	// 쿠키에서 세션 조회
	public static HttpSession getSession(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String sessionToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("JSESSIONID".equals(cookie.getName())) {
					sessionToken = cookie.getValue();
					break;
				}
			}
		}

		if (sessionToken == null || !sessions.containsKey(sessionToken)) {
			throw new RuntimeException("세션이 존재하지 않거나 만료되었습니다.");
		}

		return sessions.get(sessionToken);
	}
}
