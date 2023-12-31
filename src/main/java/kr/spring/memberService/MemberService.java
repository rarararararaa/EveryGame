package kr.spring.memberService;

import java.util.Map;

import kr.spring.memberVO.MemberVO;

public interface MemberService {
	
	public int getMemnum();
	
	public void insertMember(Map<String, Object> map) throws Exception;
	
	public int selectIsEmpty(String email);
	
	public boolean matchPasswd(Map<String, String> map);
	
	public String hashTest(String passwd);
	
	public MemberVO selectMemInfo(String email);
}
