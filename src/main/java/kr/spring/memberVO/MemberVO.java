package kr.spring.memberVO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {
	private int mem_num;
	private String mem_email;
	private int mem_auth;
	private String mem_passwd;
	private Date mem_reg_date;
	private String mem_nickname;
}
