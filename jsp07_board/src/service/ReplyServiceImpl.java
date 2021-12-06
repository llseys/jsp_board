package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BoardDAO;
import dao.BoardDAOImpl;
import dto.Board;

public class ReplyServiceImpl implements ReplyService{
	BoardDAO bdao = new BoardDAOImpl();
	
	@Override
	public String insert(Board board) {
		Map<String, Object> map = new HashMap<>();
		
		//댓글의 글순서와 글레벨 변경
		
		//1)부모의 글순서(restep)+1
		board.setRestep(board.getRestep()+1);
		//2)부모의 글레벨(relevel)+1
		board.setRelevel(board.getRelevel()+1);
		System.out.println("service : " + board);
		
		//3)기존댓글의 순서를 +1 변경
		bdao.restepUp(board);
		int cnt = bdao.insert(board);
		System.out.println("service : "+cnt+"건 댓글추가");
		
		if(cnt>0)
			return "댓글 추가성공";
		else 
			return "댓글 추가실패";

	}

	@Override
	public Board selectOne(int bnum) {
		Board board = bdao.selectOne(bnum);
		return board;
	}

	@Override
	public String update(Board board) {
		int cnt = bdao.update(board);
		if(cnt>0)
			return "수정완료";
		else
			return "수정실패";
	}


	
	
	
}
