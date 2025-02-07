package com.sist.food;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;

@WebServlet("/FoodFind")
/*
 * 	doGet()
 *  {
 *  	화면 출력
 *  }
 *  doPost()
 *  {
 *  	검석어를 받는다 => 데이터 연동
 *  }
 */
public class FoodFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// GET / POST를 동시에 처리
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		String strPage=request.getParameter("page");
		if(strPage==null)
		{
			strPage="1";
		}
		int curpage=Integer.parseInt(strPage);
		String col=request.getParameter("col");
		if(col==null)
		{
			col="이름";
		}
		String fd=request.getParameter("fd");
		if(fd==null)
		{
			fd="_";
		}
	}

}
