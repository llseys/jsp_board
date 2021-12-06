package dao;

import java.util.List;

import dto.Board;
import dto.BoardFile;

public interface BoardFileDAO {
	int insert(BoardFile bfile); 
	int update(BoardFile bfile);
	int delete(int fnum);
	int delete_bnum(int bnum); //bnum을 기준으로 삭제
	BoardFile selectOne(int fnum);
	List<BoardFile> selectList(int bnum);
}
