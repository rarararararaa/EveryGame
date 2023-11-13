package kr.spring.member;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.spring.SessionConst;
import kr.spring.memberService.MemberService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/check")
	public boolean emailcheck(@RequestBody String info) {
		CharSequence email = info.replace("\"", "");
		int check = memberService.selectIsEmpty(email.toString());
		log.debug("<<이메일-check>>"+check);
		if(check > 0) {//중복
			return false; 
		}
		return true;
	}
	
	@PostMapping("/register")
	public String register(@RequestBody Map<String, Object> map) throws Exception {
		memberService.insertMember(map);
		return "회원가입 완료";
	}
	
	@PostMapping("/login")
	public boolean login(@RequestBody Map<String, String> map, HttpSession session
			, HttpServletResponse response) throws UnsupportedEncodingException{
		boolean result = memberService.matchPasswd(map);
		
		if(result) {//true일시
			MemberVO member = memberService.selectMemInfo(map.get("email").toString());
			//log.debug("회원 정보: "+member);
			session.setAttribute(SessionConst.LOGIN_MEMBER, member);
			session.setMaxInactiveInterval(360000);
			response.addHeader("memberInfo", URLEncoder.encode(member.getMem_nickname(), "utf-8"));
			log.debug("<<세션 저장>>:"+session.getAttribute(SessionConst.LOGIN_MEMBER));	
		}
		return result;
	}
	
	@RequestMapping("/logout")
	public void logout(HttpSession session) {
		session.invalidate();//세션의 모든 속성 삭제
		log.debug("<<로그아웃>> - 세션삭제");
	}
	
	@GetMapping("/loginCheck")
	public boolean loginChechk() {
		return true;
	}
	
	@RequestMapping("/test")
	public String hashtest(@RequestBody String passwd) {
		log.debug("암호화:"+memberService.hashTest(passwd));
		return "암호화 완료";
	}
	
	@RequestMapping("/session")
	public void sessionCheck(HttpSession session) {
		log.debug("<<세션 저장>>:"+session.getAttribute(SessionConst.LOGIN_MEMBER));
	}
	
}
