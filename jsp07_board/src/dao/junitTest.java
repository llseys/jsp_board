 package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import dto.Board;
import dto.BoardFile;

class junitTest {
	BoardDAO bdao = new BoardDAOImpl();
	BoardFileDAO bfdao = new BoardFileDAOImpl();
	@Test
	void insertTest() {
		Board board = new Board();
		board.setEmail("cc@naver.com");
		board.setSubject("제목");
		board.setContent("내용");
		board.setIp("111.222.333");
		int cnt = bdao.insert(board);
		System.out.println(cnt+"건 추가");	
	}

	@Test
	void updateTest() {
		Board board = new Board();
		board.setBnum(1);
		board.setEmail("bb@naver.com");
		board.setSubject("제목11");
		board.setContent("내용11");
		board.setIp("111.222.333");
		int cnt = bdao.update(board);
		System.out.println(cnt+"건 수정");	
	}
	
	@Test
	void deleteTest() {
		int cnt = bdao.delete(2);
		System.out.println(cnt+"건 삭제");	
	}
	
	@Test
	void selectOneTest() {
		Board board = bdao.selectOne(5);
		System.out.println(board);	
		// board가 null과 같지 않으면
		assertNotNull(board);
	}
	
	@Test
	void selectListTest() {

	}
	
	@Test
	void bfInertTest() {
		BoardFile bfile = new BoardFile();
		bfile.setBnum(1);
		bfile.setFilename("a");
		
		int cnt = bfdao.insert(bfile);
		System.out.println(cnt+"건추가");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
