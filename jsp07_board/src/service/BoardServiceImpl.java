package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.BoardDAO;
import dao.BoardDAOImpl;
import dao.BoardFileDAO;
import dao.BoardFileDAOImpl;
import dto.Board;
import dto.BoardFile;
import dto.Page;

public class BoardServiceImpl implements BoardService{
	BoardDAO bdao = new BoardDAOImpl();
	BoardFileDAO bfdao = new BoardFileDAOImpl();
	
	@Override
	public List<Board> selectList(Page page) {
		//페이징처리
		int curpage = page.getCurpage() ; //현재페이지(지정)
		int perpage = page.getPerpage(); //한 페이지당 게시물수(지정)
		int startnum = (curpage-1)*perpage +1; //시작번호 구하기 식
		int endnum = startnum + perpage -1; //끝번호
		
		//전체페이지수 구하기
		int totcnt = bdao.select_totcnt(page);
		int totpage = totcnt/perpage; //전체페이지수
		if(totcnt%perpage > 0) totpage ++; //나머지가 있다면 전체페이지수+1 
		System.out.println("totcnt : " + totcnt);
		System.out.println("totpage : " +totpage);
		
		//블럭처리
		int perblock = page.getPerblock(); //페이지 블럭의수
		int startpage = curpage - ((curpage-1)%perpage);
		int endpage = startpage+perblock-1;
		if(totpage < endpage) endpage = totpage; 
		

		page.setStartnum(startnum);
		page.setEndnum(endnum);
		page.setStartpage(startpage);
		page.setEndpage(endpage);
		page.setTotpage(totpage);
		System.out.println(page);
		
		
		return bdao.selectList(page); 
	}

	
	
	
	@Override
	public String insert(Board board, List<String> filenames) {
		//게시물등록(한건)
		int cnt = bdao.insert(board);
		System.out.println("board "+cnt+"건 추가");
		System.out.println("service : "+ board);
		//파일이름 배열 처리
		// board.getBnum() : boardMapper의 insert시 bnum 구해줌 
		for(String filename : filenames) {
			if(filename==null) continue; //
			BoardFile bfile = new BoardFile();
			bfile.setBnum(board.getBnum());
			bfile.setFilename(filename);
			bfdao.insert(bfile);
			System.out.println("file : " + bfile);	
		}
		if(cnt>0)
			return "추가성공";
		else
			return "추가실패";

	}

	@Override
	public Map<String, Object> selectOne(int bnum) {
		//1. 게시물 한건 조회
		Board board = bdao.selectOne(bnum); 
		//2. 게시물의 파일들 조회
		List<BoardFile> bflist = bfdao.selectList(bnum);
		//3. 댓글들 조회
		 List<Board> relist  =  bdao.select_reply(bnum); // ref 와 bnum 동일하기에
		
		Map<String, Object> map = new HashMap<>();
		map.put("board", board);
		map.put("bflist", bflist);
		map.put("relist", relist);
		
		return map;
	}

	@Override
	public String delete(int bnum) {
		//게시물파일들삭제 : fk때문에 자식 데이터먼저 삭제후 부모데이터 삭제
		bfdao.delete_bnum(bnum);
		//게시물삭제 
		int cnt = bdao.delete(bnum);
		if(cnt>0)
			return "삭제성공";
		else
			return "삭제실패";
		
	}

	@Override
	public String update(Board board, String[] filedels, List<String> filenames) {
		//게시물수정
		String msg = null;
		int cnt = bdao.update(board);
		if(cnt>0)
			msg="수정성공";
		else
			msg="수정실패";
			
		System.out.println("게시물 " + cnt + "건 수정");
		
		//파일들삭제
		if(filedels != null) {
			for(int i=0;i<filedels.length;i++) {
				int filedel = Integer.parseInt(filedels[i]);
				bfdao.delete(filedel);
			}
		}


		//파일들추가
		int bnum = board.getBnum();
		BoardFile bfile = new BoardFile();
		for(String filename : filenames) {
			if(filename==null) continue; //
			bfile.setBnum(bnum);
			bfile.setFilename(filename);
			bfdao.insert(bfile);	
		}

		return msg;
	}

	@Override
	public void cntUp(int bnum) {
		bdao.cntUp(bnum);
	}
	
}








