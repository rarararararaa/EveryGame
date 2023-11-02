package kr.spring.memberDAO;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface MemberMapper {
	
	@Select("SELECT eg_seq.nextval FROM DUAL")
	public int getMemnum();
	
	public void insertMember(Map<String, Object> map);
	
}
