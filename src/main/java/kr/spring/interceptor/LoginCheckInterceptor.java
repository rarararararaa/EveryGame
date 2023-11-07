package kr.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import kr.spring.SessionConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("<<LoginCheckInterceptor - 진입>>");
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
			//로그인 되지 않음
			log.debug("<<로그인하지 않은 사용자입니다.>>");
			return false;
		}
		//로그인 되어있을시
		return true;
	}
	
}
