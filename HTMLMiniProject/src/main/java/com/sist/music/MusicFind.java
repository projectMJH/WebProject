package com.sist.music;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import com.sist.dao.*;
import com.sist.vo.*;

@WebServlet("/MusicFind")
public class MusicFind extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 브라우저로 전송 => HTML을 전송한다
		// HTML,XML,JSON => response(HTML, Cookie전송이 가능)
		response.setContentType("text/html;charset=UTF-8");
		//2. 브라우저 연결
		PrintWriter out=response.getWriter();
		
		// 사용자가 보내준 값을 받는다
		// => DAO에서 결과값을 받는다
		String strPage=request.getParameter("page");
		if(strPage==null)
			strPage="1";
		int curpage=Integer.parseInt(strPage);
		String col=request.getParameter("col");
		if(col==null)
			col="title";
		String fd=request.getParameter("fd");
		if(fd==null)
			fd="";
		GenieMusicDAO dao=GenieMusicDAO.newInstance();
		List<GenieMusicVO> list=dao.musicFind(curpage,col,fd);
		int totalpage=dao.musicFindTotalPage(col,fd);
		
		// 블럭별 페이지
		final int BLOCK=10;
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage)
			endPage=totalpage;

		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<link rel=stylesheet href=css/music.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		
		out.println("<div class=\"row text-center\" style=\"margin-top:20px;\">");
		out.println("<form method=post action=MainServlet?mode=24>");
		out.println("<select name=col class=input-sm style=\"height:30px;background-color=#D3D3D3;font-size:14px;font-weight:bold\">");
		out.println("<option value=title>타이틀</option>");
		out.println("<option value=singer>가수</option>");
		out.println("<option value=album>앨범</option>");
		out.println("</select>");
		out.println("<input type=text name=fd size=15 class=input-sm>");
		out.println("<button class=\"btn-sm btn-danger\">검색</button>");
		out.println("</form>");
		out.println("</div>");
		
		out.println("<div class=row style=\"margin-top: 20px\">");
		for(GenieMusicVO vo:list)
		{
			out.println("<div class=\"col-md-3\">");
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"MusicBeforeDetail?mno="+vo.getMno()+"\">");
			out.println("<img src="+vo.getPoster()+" style=\"width:230px;height:150px\">");
			out.println("<div class=\"caption\">");
			out.println("<p>"+vo.getTitle()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>");

		out.println("<div class=\"row text-center\">");
		out.println("<ul class=\"pagination\">");
		// startPage=1, 11, 21
		if(startPage>1)
		{
			out.println("<li><a href=\"MainServlet?mode=24&page="+(startPage-1)+"&col="+col+"&fd="+fd+"\">&lt;</a></li>");
		}
		for(int i=startPage;i<=endPage;i++)
		{
			if(curpage==i)
			{
				out.println("<li class=active><a href=\"MainServlet?mode=24&page="+i+"&col="+col+"&fd="+fd+"\">"+i+"</a></li>");
			}
			else
			{
				out.println("<li><a href=\"MainServlet?mode=24&page="+i+"&col="+col+"&fd="+fd+"\">"+i+"</a></li>");
			}
			
		}
		if(endPage<totalpage)
		{
			out.println("<li><a href=\"MainServlet?mode=24&page="+(endPage+1)+"&col="+col+"&fd="+fd+"\">&gt;</a></li>");
		}
		out.println("</ul>");
		out.println("</div>");

		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
