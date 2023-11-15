package kr.spring.boardService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.boardDAO.BoardMapper;
import kr.spring.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public void insertBoard(Map<String, Object> map) {
		map.put("board_num", boardMapper.seleteBoardNum());
		map.put("title", StringUtil.useBrNoHtml(map.get("title").toString()));
		if(map.get("content") != null)
			map.put("content", StringUtil.useBrNoHtml(map.get("content").toString()));
		log.debug("<<최종 BoardInfo>>"+map);
		boardMapper.insertBoard(map);
	}

	@Override
	public List<Map<String, Object>> selBoardList() {
		return boardMapper.selBoardList();
	}

}
