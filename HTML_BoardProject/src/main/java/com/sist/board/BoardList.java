package com.sist.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.sist.dao.*;
import com.sist.vo.*;
import java.util.*;

// tomcat => 9버전 까지 => javax.servlet.*
// tomcat => 10버전 이상 => jakarta.servlet.* (html v5.0)
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
			out.println("<tr>");
			out.println("<td width=10% align=center>"+vo.getNo()+"</td>");
			out.println("<td width=45%>"+vo.getSubject()+"</td>");
			out.println("<td width=15% align=center>"+vo.getName()+"</td>");
			out.println("<td width=20% align=center>"+vo.getDbday()+"</td>");
			out.println("<td width=10% align=center>"+vo.getHit()+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
	}

}
