package kr.spring.memberDAO;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface MemberMapper {
	
	@Select("SELECT eg_seq.nextval FROM DUAL")
	public int getMemnum();
	
	public void insertMember(Map<String, Object> map);
	
	@Select("SELECT COUNT(*) FROM eg_member WHERE mem_email =#{email}")
	public int selectIsEmpty(String email);
	
	@Select("SELECT mem_passwd FROM eg_member WHERE mem_email =#{email}")
	public String selectPasswd(String email);
}
