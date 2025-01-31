package com.sist.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.sist.dao.*;
import com.sist.vo.*;
/*
 * 
http://localhost:8080/HTML_BoardProject/board/BoardInsert?name=%ED%99%8D%EA%B8%B8%EB%8F%99&subject=%EC%A0%9C%EB%AA%A9%EC%9D%80+%EB%AA%B0%EB%9D%BC&content=%ED%95%AD%EB%AA%A9%EC%9D%84+%EC%9E%85%EB%A0%A5%ED%96%88%EC%96%B4%EC%9A%94.+&pwd=1234
 * 
 */
@WebServlet("/BoardInsert")
public class BoardInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// public String insert(BoardVO vo)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// request : 사용자가 보내준 데이터를 가지고 있다. 
		// response : 사용자 브라우저 정보 => 화면 변경
		request.setCharacterEncoding("UTF-8"); // 한글이 전송된 경우
		// => 2byte형식 전송값을 받는다 (디코딩)
		String name=request.getParameter("name");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		String pwd=request.getParameter("pwd");
		
		System.out.println("이름:"+name);
		System.out.println("제목:"+subject);
		System.out.println("내용:"+content);
		System.out.println("비번:"+pwd);
		
		BoardVO vo=new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
		BoardDAO dao=BoardDAO.newInstance();
		dao.boardInsert(vo);
		// JSP도 동일
		response.sendRedirect("BoardList"); //화면이동
		// 흐름 => 요청 => 어떤 파일로 이동?
	}

}
