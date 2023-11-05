package kr.spring.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.spring.memberService.MemberService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class ReisgerController {
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/check")
	public String emailcheck(@RequestBody Map<String, String> map) {
		log.debug("<<이메일>>"+map.get("email"));
		return "test";
	}
	
	@PostMapping("/register")
	public String register(@RequestBody Map<String, Object> map) throws Exception {
		log.debug("<<이메일>>"+map.get("email"));
		log.debug("<<비밀번호>>"+map.get("passwd"));
		memberService.insertMember(map);
		return "회원가입 완료";
	}
	@RequestMapping("/test")
	public String hashtest(@RequestBody String passwd) {
		
		log.debug("암호화:"+memberService.hashTest(passwd));
		return "암호화 완료";
	}
	
	
}
