package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import dto.BoardFile;

class junitTest2 {
	BoardFileDAO bfdao = new BoardFileDAOImpl();
	@Test
	void testInsert() {
		BoardFile bfile = new BoardFile();
		bfile.setBnum(1);
		bfile.setFilename("aa");
		int cnt = bfdao.insert(bfile);
		System.out.println(cnt+ "건 추가");
	}

	@Test
	void testUpdate() {
		BoardFile bfile = new BoardFile();
		bfile.setFnum(4);
		bfile.setFilename("수정.png");
		int cnt = bfdao.update(bfile);
		System.out.println(cnt+ "건 수정");
	}

	@Test
	void testDelete() {
		int cnt = bfdao.delete(4);
		System.out.println(cnt + "건 삭제");
	}

	@Test
	void testSelectOne() {
		BoardFile bfile = bfdao.selectOne(3);
		System.out.println(bfile);
	}

	@Test
	void testSelectList() {
		List<BoardFile> bflist =  bfdao.selectList(1);
		for(BoardFile bfile : bflist) {
			System.out.println(bfile);
			System.out.println("---------------------------------------------");
		}
	}

}
