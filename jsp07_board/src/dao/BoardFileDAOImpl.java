package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dto.Board;
import dto.BoardFile;

public class BoardFileDAOImpl implements BoardFileDAO{

	@Override
	public int insert(BoardFile bfile) {
		try(SqlSession session =  MBConn.getSession()){
			return session.insert("BoardFileMapper.insert", bfile);
		}
	}

	@Override
	public int update(BoardFile bfile) {
		try(SqlSession session =  MBConn.getSession()){
			return session.update("BoardFileMapper.update", bfile);
		}
	}

	@Override //fnum 기준으로 삭제(딱 한건만)
	public int delete(int fnum) {
		try(SqlSession session =  MBConn.getSession()){
			return session.delete("BoardFileMapper.delete", fnum);
		}
	}
	
	@Override //bnum 기준으로 삭제(여러건가능)
	public int delete_bnum(int bnum) {
		try(SqlSession session =  MBConn.getSession()){
			return session.delete("BoardFileMapper.delete_bnum", bnum);
		}
	}

	@Override
	public BoardFile selectOne(int fnum) {
		try(SqlSession session =  MBConn.getSession()){
			return session.selectOne("BoardFileMapper.selectOne", fnum);
		}
	}

	@Override
	public List<BoardFile> selectList(int bnum) {
		try(SqlSession session =  MBConn.getSession()){
			return session.selectList("BoardFileMapper.selectList", bnum);
		}
	}





}
