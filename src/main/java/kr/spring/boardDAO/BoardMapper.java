package kr.spring.boardDAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BoardMapper {
	
	@Select("SELECT eg_board_seq.nextval FROM DUAL")
	public int seleteBoardNum();
	
	public void insertBoard(Map<String, Object> map);
	
	@Select("SELECT * FROM eg_board")
	public List<Map<String, Object>> selBoardList();
}