package com.sist.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import com.sist.dao.*;
import com.sist.vo.*;
import java.util.*;

// tomcat => 9버전 까지 => javax.servlet.*
// tomcat => 10버전 이상 => jakarta.servlet.* (html v5.0)
/*
 * 	servlet : 동적페이지 / html : 정적페이지
 * 							  -------
 * 							  데이터변경이 안된다
 * 			  | 한개의 파일에서 데이터 변경이 가능
 * 			  | server + let : 서버에서 실행하는 가벼운 프로그램
 * 				단점 : 소스 변경 시마다 컴파일해서 출력
 * 				=> 컴파일 없이 실행 => JSP
 * 				장점 : 보안이 뛰어나다, 소스를 감출 수 있다
 * 					  JSP의 단점 : 보안 취약, 소스를 감출 수 없다
 * 				=> servlet => java => class 파일만 전송
 * 				=> JSP = 통으로 전송 (View)
 * 				=> servlet => spring은 라이브러리가 서블릿으로 되어 있다. 
 * 		실행 과정
 * 		------
 * 
 * 		class ServletClass extends HttpServlet
 * 		{
 * 			public void init()
 * 			{
 * 				초기화 담당 => 변수에 대한 초기화
 * 			}
 * 			public void destroy()
 * 			{
 * 				화면 변경 / 새로고침 => 메모리 회수
 * 				=> 장점 : 바로 메모리 해제
 * 			}
 * 			---------------------------------------
 * 			사용자 요청에 대한 처리
 * 			public void doGet()
 * 			{
 * 				// 사용자 요청 => GET
 * 				// => 단순한 검색 (page,검색) => 화면 ui
 * 				// DEFAULT
 * 				// <a> location.href=""
 * 				// sendRedirect()
 * 			}
 * 			public void doPost()
 * 			{
 * 				// 사용자 요청 => GET
 * 				// 처리용 => <form>, ajax, vuejs => 지정이 가능
 * 				// 
 * 			}
 * 			// => GET/POST를 동시에 처리 => MVC구조 (get/post)같은 처리
 * 
 * 			public void service()
 * 			{
 * 				초기화 담당 => 변수에 대한 초기화
 * 			}
 * 			---------------------------------------
 * 			=> _jspInit() => _jspDestroy(), _jspService()
 * 		}
 * 										servlet/jsp엔진
 * 		브라우저 (사용자 요청) ==> 웹서버	==> 웹컨테이너
 * 			=> 주소창			HTML/XML	 톰캣
 * 										  |
 * 										요청한 JSP/Servlet을 찾아서 컴파일
 * 										.class
 * 										  |
 * 										인터프리터를 한줄씩 번역
 * 										  |
 * 										메모리에 HTML을 저장
 * 										  |
 * 										브라우저에 출력
 */
// 서블릿을 찾는 URL주소 => 톰캣이 해당 파일을 찾아줌
@WebServlet("/BoardList")
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 변환 => HTML만 브라우저로 전송
		// 변환 (브라우저에서 실행)
		// 1. HTML 2. XML	3. JSON
		response.setContentType("text/html;charset=UTF-8");
		// ***html => text/html
		// xml => text/xml
		// ***json => text/plain => Ajax(JavaScript)
		// 사용자 전송 => 사용자가 브라우저 전송된 값
		// request => BufferedReader
		// 브라우저로 전송 => response => OutputStream
		// 어떤 브라우저에 보내는지 확인
		PrintWriter out=response.getWriter();
		// 출력
		// 1. 사용자로부터 요청한 페이지를 받는다
		// 웹 => String
		// /BoardList?page=2;
		// /BoardList 		=> page=null
		// /BoardList?page= => page=""
		// /BoardList? page = 2 => 에러 (공백이 있으면 안됨)
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		
		// 현재페이지
		int curpage=Integer.parseInt(page);
		// 현재페이지에 대한 데이터를 오라클로부터 가지고 온다
		BoardDAO dao=BoardDAO.newInstance();
		List<BoardVO> list=dao.boardListData(curpage);
		// 총페이지
		int totalpage=dao.boardTotalPage();
		
//		String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String today=sdf.format(date);
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=stylesheet href=css/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>게시판</h1>");
		out.println("<table class=table_content width=700>");
		out.println("<tr>");
		out.println("<td><a href=board/insert.html>새글</a></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<table class=table_content width=700>");
		out.println("<tr>");
		out.println("<th width=10%>번호</th>");
		out.println("<th width=45%>제목</th>");
		out.println("<th width=15%>이름</th>");
		out.println("<th width=20%>작성일</th>");
		out.println("<th width=10%>조회수</th>");
		out.println("</tr>");
		// 출력 위치
		for(BoardVO vo:list)
		{
			// html => 화면이동 <a>:화면이동 only, <form>:데이터전송+화면이동
			out.println("<tr>");
			out.println("<td width=10% align=center>"+vo.getNo()+"</td>");
			out.println("<td width=45%><a href=BoardDetail?no="+vo.getNo()+">"+vo.getSubject()+"</a>");
			out.println("&nbsp;");
			if(today.equals(vo.getDbday()))
			{
				out.println("<sup><img src=image/new.gif></sup>");
			}
			out.println("</td>");
			out.println("<td width=15% align=center>"+vo.getName()+"</td>");
			out.println("<td width=20% align=center>"+vo.getDbday()+"</td>");
			out.println("<td width=10% align=center>"+vo.getHit()+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("<table class=table_content width=700>");
		out.println("<tr>");
		out.println("<td align=left>");
		out.println("<select>");
		out.println("<option>이름</option>");
		out.println("<option>제목</option>");
		out.println("<option>내용</option>");
		out.println("</select>");
		out.println("<input type=text size=15>");
		out.println("<input type=button value=검색>");
		out.println("</td>");
		out.println("<td align=right>");
		out.println("<a href=BoardList?page="+(curpage>1?curpage-1:curpage)+">이전</a>");
		out.println(curpage+" page / "+totalpage+" pages");
		out.println("<a href=BoardList?page="+(curpage<totalpage?curpage+1:curpage)+">다음</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

}
