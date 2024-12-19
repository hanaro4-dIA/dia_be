package com.dia.dia_be.controller.pb;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import com.dia.dia_be.domain.PbSessionConst;
import com.dia.dia_be.dto.pb.loginDTO.LoginDTO;

@WebFilter(urlPatterns = "/pb/**")  // 필터를 적용할 경로를 설정합니다.
public class AutoLoginFilter extends OncePerRequestFilter {

	private static final String LOGIN_API_URL = "/pb/login"; // 로그인 API의 URL

	@Override
	protected void doFilterInternal(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// 세션이 없거나 로그인 세션이 없으면 자동으로 로그인 처리
		if (session == null || session.getAttribute(PbSessionConst.LOGIN_PB) == null) {
			// 로그인 API로 요청을 보내 로그인 처리
			// 예: 로그인 API 호출 코드 추가
			// 예시로 POST 요청을 /pb/login에 보내 로그인 세션을 설정할 수 있습니다.
			// 만약 로그인 API를 직접 호출하려면 RestTemplate이나 WebClient를 사용할 수 있습니다.

			// 세션에 로그인된 상태로 설정
			session = request.getSession(true);  // 세션이 없으면 새로 생성
			session.setAttribute(PbSessionConst.LOGIN_PB, new LoginDTO(1L));  // 예시로 pbId 1을 설정
		}

		// 필터 체인에 요청을 전달
		filterChain.doFilter(request, response);
	}
}
