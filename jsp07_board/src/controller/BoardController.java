package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import dao.BoardDAO;
import dao.BoardDAOImpl;
import dto.Board;
import dto.Page;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private BoardService bservice = new BoardServiceImpl();
	private BoardDAO bdao = new BoardDAOImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String path = request.getContextPath();
		String uri = request.getRequestURI();
		System.out.println(uri);
		if(uri.contains("list")) {
			String findkey = request.getParameter("findkey");
			String findvalue = request.getParameter("findvalue");
			String curpage_s = request.getParameter("curpage");
			int curpage = 1; // 디폴트 페이지
			if(curpage_s!=null) {
				curpage = Integer.parseInt(curpage_s); //입력받은 현재페이지
			}
			if(findkey==null) findkey="email";
			if(findvalue==null) findvalue="";
			Page page = new Page();
			page.setFindkey(findkey);
			page.setFindvalue(findvalue);
			page.setCurpage(curpage);
			List<Board> blist = bservice.selectList(page);
			request.setAttribute("blist", blist);
			request.setAttribute("page", page);
			request.getRequestDispatcher("/views/board/list.jsp").forward(request, response);
		}else if(uri.contains("add")) {
			String saveDirectory = getServletContext().getInitParameter("savedir");
			int size = 1024*1024*10;
			// 객체를 생성과 동시에 파일을 saveDirectory에 저장
			MultipartRequest multi  = new MultipartRequest(request, saveDirectory, size,"utf-8", new DefaultFileRenamePolicy());
			// db에 insert할 정보들
			String email = multi.getParameter("email");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");

			Board board = new Board(); //객체생성
			board.setEmail(email); 
			board.setSubject(subject);
			board.setContent(content);
			board.setIp(request.getRemoteAddr()); //요청ip주소
			System.out.println(board);
			//신규 파일이름 처리
			List<String> filenames = new ArrayList<>();
			//파일의 이름의 모음
			Enumeration<String> files = multi.getFileNames(); 
			while(files.hasMoreElements()) { //다음자료가있으면 반복
				String name = files.nextElement(); 
				String filename = multi.getFilesystemName(name); //실제로 저장된 파일이름
				if(filename!=null) filenames.add(filename);
			}
			String msg = bservice.insert(board, filenames);
			msg = URLEncoder.encode(msg, "utf-8");
			response.sendRedirect(path+"/board/list?msg="+msg);
		}else if(uri.contains("detail")) {
			int bnum =  Integer.parseInt(request.getParameter("bnum"));
			// 조회수 업
			String cntUp = request.getParameter("cntUp"); //(리스트에서만 cntUp 값을 보냄)
			System.out.println("cntUP : " + cntUp);
			if(cntUp != null) {
				bservice.cntUp(bnum);
			}
			//게시물조회 + 파일조회 + 댓글조회
			 Map<String,Object> map = bservice.selectOne(bnum);
			 request.setAttribute("map", map);
			 request.getRequestDispatcher("/views/board/detail.jsp").forward(request, response);
		}else if(uri.contains("remove")) {
			int bnum = Integer.parseInt(request.getParameter("bnum")); 
			String msg = bservice.delete(bnum);
			msg = URLEncoder.encode(msg, "utf-8");
			response.sendRedirect(path+"/board/list?msg="+msg);
		}else if(uri.contains("modiForm")) {
			int bnum = Integer.parseInt(request.getParameter("bnum")); 
			Map<String,Object> map = bservice.selectOne(bnum);
			 request.setAttribute("map", map);
			 request.getRequestDispatcher("/views/board/modify.jsp").forward(request, response);
		}else if(uri.contains("modify")) {
			String saveDirectory = getServletContext().getInitParameter("savedir");
			int size = 1024*1024*10; //10m
			MultipartRequest multi = new MultipartRequest(request, saveDirectory, size, "utf-8", new DefaultFileRenamePolicy());
			// db에 update 정보읽기
			int bnum = Integer.parseInt(multi.getParameter("bnum")); 
			String email = multi.getParameter("email");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			Board board = new Board();
			board.setBnum(bnum);
			board.setEmail(email);
			board.setSubject(subject);
			board.setContent(content);
			board.setIp(request.getRemoteAddr());
			System.out.println("수정할 board : "+ board);
			
			String[] filedels = multi.getParameterValues("filedel");
			System.out.println("기존파일 삭제리스트 : " + Arrays.toString(filedels));
			
			//신규 파일이름 처리
			List<String> filenames = new ArrayList<>();
			//파일의 이름의 모음
			Enumeration<String> files = multi.getFileNames(); 
			while(files.hasMoreElements()) { //다음자료가있으면 반복
				String name = files.nextElement(); 
				String filename = multi.getFilesystemName(name); //실제로 저장된 파일이름
				if(filename!=null) filenames.add(filename);
			}
			System.out.println("신규파일 추가 리스트 : " + filenames);
			String msg = bservice.update(board, filedels, filenames);
			msg = URLEncoder.encode(msg,"utf-8");
			response.sendRedirect(path+"/board/detail?bnum="+bnum+"&msg="+msg);
		}
	
		
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
