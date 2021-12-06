
package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.Member;
import service.MemberService;
import service.MemberServiceImpl;

@WebServlet("/member/*")

public class MemberController extends HttpServlet {
	MemberService mservice = new MemberServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		System.out.println(uri);
		if(uri.contains("add")) {
//			String saveDirectory = "C:/savedir"; //파일을 저장할 경로(서버)
			//web.xml에서 저장 경로 읽기
			String saveDirectory = getServletContext().getInitParameter("savedir");
			int size = 1024*1024*10; //10mb 업로드 파일사이즈 제한
			//  MultipartRequest 객체를 이용해서 데이터를 가져옴
			// new DefaultFileRenamePolicy() : 같은이름의 파일이 들어오면 자동 리네임
			MultipartRequest multi = new MultipartRequest(request, saveDirectory,size,"utf-8",new DefaultFileRenamePolicy());
			String email = multi.getParameter("email");
			String userpw = multi.getParameter("userpw");
			String zipcode = multi.getParameter("zipcode");
			String addr = multi.getParameter("addr");
			String addrdetail = multi.getParameter("addrdetail");
			String filename = multi.getFilesystemName("file"); //실제저장된 파일이름
			if(filename==null) filename="";
			Member member = new Member(email,userpw,zipcode,addr,addrdetail,filename);
			String msg = mservice.insert(member);
			msg = URLEncoder.encode(msg,"utf-8");
			response.sendRedirect(path+"/views/home.jsp?msg="+msg);
		}else if(uri.contains("login")) {
			String email = request.getParameter("email");
			String userpw = request.getParameter("userpw");
			Map<String,Object> map = mservice.login(email,userpw);
			String msg = (String)map.get("msg"); //로그인 메세지
			String rcode = (String)map.get("rcode"); //결과코드값
			msg = URLEncoder.encode(msg,"utf-8");
			if(rcode.equals("0")) { //로그인성공
				//세션에 이메일넣기(화면에 띄어주려고)-------------------------------------------
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
				session.setMaxInactiveInterval(60*60*6);
				// 쿠키생성 email------------------------------------------------------------------
				String emailsave = request.getParameter("emailsave");
				Cookie cookie_email = new Cookie("email", email);
				cookie_email.setPath(path);
				if(emailsave!=null) {
					cookie_email.setMaxAge(60*60*10);
				}else {	
					cookie_email.setMaxAge(0); //쿠기삭제
				}
				response.addCookie(cookie_email);
				response.sendRedirect(path+"/views/home.jsp?msg="+msg);
			}else { //로그인실패(이메일x or 비밀번호불일치)
				response.sendRedirect(path+"/views/member/login.jsp?msg="+msg);
			}
		}else if(uri.contains("logout")) {
			HttpSession session	= request.getSession();
			session.invalidate(); //세션을 끝낸다(변수삭제)
			String msg ="로그아웃 되었습니다";
			msg = URLEncoder.encode(msg,"utf-8");
			response.sendRedirect(path+"/views/home.jsp?msg="+msg);
		}else if(uri.contains("myInfo")) {
			HttpSession session = request.getSession();
			String email = (String)session.getAttribute("email");
			Member member = mservice.selectOne(email);
			//forward 방식으로 myInfo.jsp이동
			request.setAttribute("member", member);
			request.getRequestDispatcher("/views/member/myinfo.jsp").forward(request, response);
		}else if(uri.contains("modify")) {
			//web.xml에서 저장 경로 읽기
			String saveDirectory = getServletContext().getInitParameter("savedir");
			int size = 1024*1024*10;
			MultipartRequest multi = new MultipartRequest(request, saveDirectory, size, "utf-8", new DefaultFileRenamePolicy());
			String email = multi.getParameter("email");
			String userpw = multi.getParameter("userpw"); //기존비밀번호(확인전)
			String changepw = multi.getParameter("changepw"); //변경할비밀번호
			String zipcode = multi.getParameter("zipcode");
			String addr = multi.getParameter("addr");
			String addrdetail = multi.getParameter("addrdetail");
			String filename = multi.getParameter("filename"); // 기존파일이름
			String newfilename = multi.getFilesystemName("file"); //변경할파일이름
			String filedel = multi.getParameter("filedel"); // 파일삭제확인

			if(newfilename!=null) filename= newfilename; // 파일을 변경할 경우
			else if(filedel!=null) filename= ""; //삭제체크가 되어있다면  
			Member member = new Member(email,userpw,zipcode,addr,addrdetail,filename);

			String msg = mservice.update(member, changepw);

			msg = URLEncoder.encode(msg, "utf-8");
			response.sendRedirect(path+"/member/myInfo?msg="+msg);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}










