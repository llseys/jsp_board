package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Board;
import service.BoardService;
import service.BoardServiceImpl;
import service.ReplyService;
import service.ReplyServiceImpl;

@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	private ReplyService rservice = new ReplyServiceImpl();
	private BoardService bservice = new BoardServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		System.out.println(uri);
		if(uri.contains("add")) {
			//댓글등록
			String email = request.getParameter("email");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			int ref = Integer.parseInt(request.getParameter("ref"));
			int restep = Integer.parseInt(request.getParameter("restep"));
			int relevel = Integer.parseInt(request.getParameter("relevel"));
			
			Board board = new Board();
			board.setEmail(email);
			board.setSubject(subject);
			board.setContent(content);
			board.setIp(request.getRemoteAddr());
			//부모의 정보 
			board.setRef(ref);
			board.setRestep(restep);
			board.setRelevel(relevel);
			System.out.println(board);
			String msg = rservice.insert(board);
			response.sendRedirect(path+"/board/detail?bnum="+ref); //detail에선 bnum 필요 (ref와 bnum같음)
			
		}else if(uri.contains("remove")) {
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			int rnum = Integer.parseInt(request.getParameter("rnum"));	
			String msg = bservice.delete(rnum);
			response.sendRedirect(path+"/board/detail?bnum="+bnum); //detail에선 bnum 필요 (ref와 bnum같음)		
		}else if(uri.contains("modiform")) {
			int bnum = Integer.parseInt(request.getParameter("bnum")); 
			Board board = rservice.selectOne(bnum);
			System.out.println("controller:"+board);
			request.setAttribute("board", board);
			request.getRequestDispatcher("/views/board/replyModify.jsp").forward(request, response);
		}else if(uri.contains("modify")) {
			int ref = Integer.parseInt(request.getParameter("ref")); //원본번호
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			String email = request.getParameter("email");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			Board board = new Board();
			board.setBnum(bnum);
			board.setEmail(email);
			board.setSubject(subject);
			board.setContent(content);
			board.setIp(request.getRemoteAddr());
			System.out.println("controller : " + board);
			String msg = rservice.update(board);
			System.out.println(msg);

			response.sendRedirect(path+"/board/detail?bnum="+ref);
			
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
