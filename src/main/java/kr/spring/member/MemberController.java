package kr.spring.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
	public boolean emailcheck(@RequestBody String email) {
		int check = memberService.selectIsEmpty(email);
		log.debug("<<이메일-check>>"+check);
		if(check > 0) {
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
	public boolean login(@RequestBody Map<String, String> map, HttpServletRequest request){
		boolean result = memberService.matchPasswd(map);
		
		if(result) {//true일시
			MemberVO member = memberService.selectMemInfo(map.get("email").toString());
			//log.debug("회원 정보: "+member);
			HttpSession session = request.getSession();//request.getSession(true)와 동일
			session.setAttribute(SessionConst.LOGIN_MEMBER, member);
		}
		return result;
	}
	
	@RequestMapping("/test")
	public String hashtest(@RequestBody String passwd) {
		log.debug("암호화:"+memberService.hashTest(passwd));
		return "암호화 완료";
	}
	
	
}
