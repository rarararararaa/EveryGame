package kr.spring.board;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {
	private int board_num;
	private int mem_num;
	private int board_game_type;//게임 종류
	private int board_contry_type;
	private int board_type;
	private String board_content;
	private Date board_reg_date;
	private String board_time;
	private int board_team_num;
	private String board_title;
	
}
