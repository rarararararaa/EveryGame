package kr.spring.memberService;

import java.util.Map;

public interface MemberService {
	
	public int getMemnum();
	
	public void insertMember(Map<String, Object> map) throws Exception;
	
	public String hashTest(String passwd);
}
