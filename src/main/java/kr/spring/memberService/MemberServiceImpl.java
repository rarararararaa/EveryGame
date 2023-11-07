package kr.spring.memberService;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.MemberVO;
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
		map.put("passwdf", Hashalgorithm.encode(map.get("passwd").toString()));
		log.debug("map 내용"+map);
		memberMapper.insertMember(map); 
	}
	  
	private static class Hashalgorithm{
		private static final SecureRandom random = new SecureRandom();
		private static final byte[] SECRET_KEY = StandardCharsets.UTF_8.encode("hashTestSecretkey123!").array();//Utf8.encode("hashTestSecretkey123!");
		private static final int ITERATION_COUNT = 65536;
		private static final int HASH_WIDTH = 256;
		private static final int SALT_BYTE_LENGTH = 16;
		
		public static String encode(CharSequence raqPassword) {
			byte[] salt = createSalt();//salt 생성
			byte[] encoded = encode(raqPassword, salt);
			
			return Base64.getEncoder().encodeToString(encoded);
		}
		
		private static byte[] encode(CharSequence rawPassword, byte [] salt) {
			try {
				//SercetKeyFactory 인스턴스를 이용해서 패스워드 기반 키를 생성
				PBEKeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(), concatenate(salt, SECRET_KEY), ITERATION_COUNT, HASH_WIDTH);
				//passwd, salt, 반복횟수, PBEKey를 생성하기 위해 파생되는 키의 길이
				//PBEKeySpec를 실제 키로 사용하기 위해 getInstanceSecret를 해줌
				SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");//PBKDF2WithHmacSHA256방식으로 암호화
				
				return concatenate(salt, skf.generateSecret(spec).getEncoded());
			} catch (GeneralSecurityException e) {
				throw new IllegalStateException("Coult Not Create Hash",e);
			}
		}
		
		//복호화
		public static boolean matches(CharSequence raqPassword, String encodedPassword) {
			byte[] digested = Base64.getDecoder().decode(encodedPassword);
			
			byte[] salt = new byte[SALT_BYTE_LENGTH];
			System.arraycopy(digested, 0, salt, 0, SALT_BYTE_LENGTH);
			return MessageDigest.isEqual(digested, encode(raqPassword, salt));
		}
		
		
		static byte[] createSalt() {//임의의 바이트 배열 생성
			byte [] salt = new byte[SALT_BYTE_LENGTH];
			random.nextBytes(salt);
			return salt;
		}
		
		public static byte[] concatenate(byte[]... arrays) {//... => 가변인자 즉, 여러개의 byte[] => passwd와 salt 합치기 가변인자는 항상 매개변수 마지막이여야 함
			int length = 0;
			for(byte[] array : arrays) {//여러개의 array를 합친 길이(length)
				length += array.length;
			}
			byte[] newArray = new byte [length];//해당 길이를 가진 새로운 배열 생성
			
			int destPos = 0;
			for(byte[] array : arrays) {
				//System.arraycopy(원본배열, 원본배열의 복사 시작 지점, 복사할 배열, 복사할 배열의 시작지점, 복사할 요소의 개수)
				System.arraycopy(array, 0, newArray, destPos, array.length);//배열 복사 메서드
				destPos += array.length;
			}
			
			return newArray;
		}
		
		/*private static Pbkdf2PasswordEncoder pbkdf2PasswordEncoder;//Spring Security를 사용했을 때
		//초기화 블럭 : 주로 클래스 변수를 초기화하는 코드를 둔다
		static {
			//pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(SECRET_KEY, SALT_BYTE_LENGTH, ITERATION_COUNT, HASH_WIDTH);
			//pbkdf2PasswordEncoder.setAlgorithm(SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
			//pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);//기본 false이고 이때는 Hex로 지원 true는 Base64
			
		}*/
	
		
	}
	@Override
	public boolean matchPasswd(Map<String, String> map) {
		//db에서 패스워드 검색
		String passwd = memberMapper.selectPasswd(map.get("email"));
		
		return Hashalgorithm.matches(map.get("passwd"),passwd);
	}
	
	
	@Override
	public String hashTest(String passwd) {
		return Hashalgorithm.encode(passwd);
		//return HashTest.pbkdf2PasswordEncoder.encode(passwd);
	}

	@Override
	public int selectIsEmpty(String email) {
		return memberMapper.selectIsEmpty(email);
	}

	@Override
	public MemberVO selectMemInfo(String email) {
		return memberMapper.selectMemInfo(email);
	}


}
