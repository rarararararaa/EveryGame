package kr.spring.boardService;

import java.util.List;
import java.util.Map;

public interface BoardService {
	
	//게시글 작성
	public void insertBoard(Map<String, Object> map);
	
	//게시글 조회
	public List<Map<String, Object>> selBoardList();
}