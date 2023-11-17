package kr.spring.board;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.spring.SessionConst;
import kr.spring.boardService.BoardService;
import kr.spring.member.MemberVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/WriteBoard")
	public String insertBoard(@RequestBody Map<String, Map<String, Object>> map,
			HttpSession session) {
		Map<String, Object> data = map.get("data");
		MemberVO member = (MemberVO) session.getAttribute(SessionConst.LOGIN_MEMBER);
		data.put("mem_num", member.getMem_num());
		boardService.insertBoard(data);
		
		return "success";
	}
	
	@GetMapping("/BoardList")
	public List<Map<String, Object>> selectBoardList(){
		return boardService.selBoardList();
	}
	
	@GetMapping("/BoardDetail")
	public Map<String, Object> detailBoard(@RequestParam int num){
		return boardService.selectBoard(num);
	}
	
	@PostMapping("/UpdateBoard")
	public boolean updateBoard(@RequestBody Map<String, Object> map) {
		log.debug("<<수정>>"+map);
		boardService.updateBoard(map);
		return true;
	}
}
