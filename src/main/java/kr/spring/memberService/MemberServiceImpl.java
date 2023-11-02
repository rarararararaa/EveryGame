package kr.spring.memberService;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.memberDAO.MemberMapper;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class MemberServiceImpl implements MemberService{
	private static final int SALT_SIZE = 16;
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public int getMemnum() {
		return memberMapper.getMemnum();
	}

	@Override
	public void insertMember(Map<String, Object> map) throws Exception {
		map.put("mem_num", getMemnum());
		map.put("mem_salt",getSalt());
		map.put("passwdf", Hasing(map.get("passwd").toString().getBytes(), map.get("mem_salt").toString()));
		log.debug("map 내용"+map);
		memberMapper.insertMember(map);
	}
	//해시 함수
		private String Hasing(byte[] pw, String salt) throws Exception{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			//SHA-256 해시 함수를 사용
			for(int i=0;i<10000;i++) {
				String temp = Byte_to_String(pw) + salt;
				md.update(temp.getBytes());
				pw = md.digest();
			}
			return Byte_to_String(pw);
		}
		//SALT 값 생성
		private String getSalt() throws Exception{
			SecureRandom rnd = new SecureRandom();
			byte [] temp = new byte[SALT_SIZE];
			rnd.nextBytes(temp);
			return Byte_to_String(temp);
		}
		//byte값을 16진수로 변환
		private String Byte_to_String(byte[] temp) {
			StringBuilder sb = new StringBuilder();
			for(byte a : temp) {
				sb.append(String.format("%02x", a));
			}
			return sb.toString();
		}

}
