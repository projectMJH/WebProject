package com.sist.food;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.sist.dao.*;
import com.sist.vo.*;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ReplyInsert")
public class ReplyInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fno=request.getParameter("fno");
		String rno=request.getParameter("rno");

		// session에서 id,name 읽기
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		ReplyDAO dao=ReplyDAO.newInstance();
//		dao.replyDeleteData(Integer.parseInt(rno));
		dao.replyDeleteData(Integer.parseInt(rno), id);

		response.sendRedirect("MainServlet?mode=2&fno="+fno);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 사용자가 보내준 데이터 받기
		// <input name="fno"
		// ?fno=10
		// 호출 프로그램(FoodDetail)으로 부터 fno,msg 받기
		String fno=request.getParameter("fno");
		String msg=request.getParameter("msg");
		
		// session에서 id,name 읽기
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");

		// vo 만들기
		ReplyVO vo=new ReplyVO();
		vo.setFno(Integer.parseInt(fno));
		vo.setMsg(msg);
		vo.setId(id);
		vo.setName(name);
		
		// db insert 요청
		ReplyDAO dao=ReplyDAO.newInstance();
		dao.replayInsert(vo);
		
		// 화면 출력 => FoodDetail
		
		response.sendRedirect("MainServlet?mode=2&fno="+fno);
		//								   ------ ---------
		//										  | FoodDetail
		//								   | MainServlet
	}

}
