package kr.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.spring.interceptor.LoginCheckInterceptor;
//교차되는 출처 자원들을 공유 Configuration - Bean 수동 등록(Component 상속) Component - Bean 자동등록
@Component
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedOrigins("http://localhost:3000")
        .allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
        .exposedHeaders("Authorization").exposedHeaders("memberInfo")
        .allowCredentials(true);//쿠키, 세션 정보 허용
		
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())//LoginCheckInterCeptor 등록
				.order(1)//적용할 필더 우선 순위
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api/check")
				.excludePathPatterns("/api/login")
				.excludePathPatterns("/api/session")
				.excludePathPatterns("/api/register")
				.excludePathPatterns("/api/BoardList");///api로 시작하는 1개 이상의 모든 경로에 추가
	}
	
	
}
