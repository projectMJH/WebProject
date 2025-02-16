package com.sist.music;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.sist.dao.*;

@WebServlet("/ReplyMusicUpdate")
public class ReplyMusicUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mno=request.getParameter("mno");
		String rno=request.getParameter("rno");
		String msg=request.getParameter("msg");
		
		ReplyDAO dao=ReplyDAO.newInstance();
		dao.replyUpdateData(Integer.parseInt(rno), msg);
		response.sendRedirect("MainServlet?mode=22&mno="+mno);
	}	

}
