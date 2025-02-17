package com.sist.music;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.sist.dao.ReplyDAO;
import com.sist.dao.ReplyMusicDAO;
import com.sist.vo.ReplyVO;

@WebServlet("/ReplyMusicInsert")
public class ReplyMusicInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mno=request.getParameter("mno");
		String rno=request.getParameter("rno");
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		ReplyMusicDAO dao=ReplyMusicDAO.newInstance();
		dao.replyDeleteData(Integer.parseInt(rno), id);
		
		response.sendRedirect("MainServlet?mode=22&mno="+mno);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 호출 프로그램(MusicDetail)으로 부터 mno,msg 받기
		String mno=request.getParameter("mno");
		String msg=request.getParameter("msg");
		
		// session에서 id,name 읽기
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		// vo 만들기
		ReplyVO vo=new ReplyVO();
		vo.setMno(Integer.parseInt(mno));
		vo.setMsg(msg);
		vo.setId(id);
		vo.setName(name);

		// db insert 요청
		ReplyMusicDAO dao=ReplyMusicDAO.newInstance();
		dao.replayInsert(vo);
		
		// 화면 출력 => FoodDetail
		
		response.sendRedirect("MainServlet?mode=22&mno="+mno);

	}

}
