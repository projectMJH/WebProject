package com.sist.music;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.sist.vo.*;
import com.sist.dao.*;

@WebServlet("/MusicDetail")
public class MusicDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		String mno=request.getParameter("mno");
		GenieMusicDAO dao=GenieMusicDAO.newInstance();
		GenieMusicVO vo=dao.musicDetailData(Integer.parseInt(mno));
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<link rel=stylesheet href=css/music.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td width=30% class=text-center rowspan=4>");
		out.println("<img src="+vo.getPoster()+" style=\"width:270px;height:300px\">");
		out.println("</td>");
		out.println("<td colspan=2>");
		out.println("<h3 class=text-center>"+vo.getTitle()+"</h3>");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td width=20% style=\"color:gray\">아티스트</td>");
		out.println("<td width=50%>"+vo.getSinger()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td width=20% style=\"color:gray\">앨범</td>");
		out.println("<td width=50%>"+vo.getAlbum()+"</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td width=20% style=\"color:gray\">좋아요!</td>");
		out.println("<td width=50%>"+vo.getHit()+"</td>");
		out.println("</tr>");
		out.println("</table>");
		
		out.println("<table class=table>");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		out.println("<tr>");
		out.println("<td class=text-right>");
		if(id!=null)
		{
			out.println("<a href=# class=\"btn btn-xs btn-danger\">좋아요</a>");
			out.println("<a href=# class=\"btn btn-xs btn-success\">찜하기</a>");
			out.println("<a href=# class=\"btn btn-xs btn-info\">예약</a>");
		}	
		out.println("<a href=MainServlet?mode=21 class=\"btn btn-xs btn-primary\">목록</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		out.println("</div>");

		out.println("<div class=row style=\"margin-top:20px;\">");
		out.println("<div class=col-sm-8>");
		out.println("<h3>댓글<h3>");
		// 댓글 출력
		ReplyMusicDAO rdao=ReplyMusicDAO.newInstance();
		List<ReplyVO> list=rdao.replyListData(Integer.parseInt(mno));
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		for(ReplyVO rvo:list)
		{
			out.println("<table class=table>");
			out.println("<tr>");
			out.println("<td class=text-left>");
			out.println("◑"+rvo.getName()+"&nbsp;(");
			out.println(rvo.getDbday()+")");
			out.println("</td>");
			out.println("<td class=text-right>");
			if(rvo.getId().equals(id))
			{
				out.println("<span class=\"btn btn-xs btn-success update\" data-rno="+rvo.getRno()+">수정</span>");
				out.println("<a href=ReplyMusicInsert?mno="+rvo.getMno()+"&rno="+rvo.getRno()+" class=\"btn btn-xs btn-info\">삭제</a>");
			}
			out.println("</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td colspan=2>");
			out.println("<pre style=\"white-space:pre-wrap; background-color:white; border:none\">"+rvo.getMsg()+"</pre>");
			out.println("</td>");
			out.println("</tr>");
			out.println("<tr id=\"m"+rvo.getRno()+"\" class=ups style=\"display:none\">");
			out.println("<td>");
			out.println("<form method=post action=ReplyMusicUpdate>");
			out.println("<textarea rows=4 cols=45 name=msg style=\"float:left\" required>"+rvo.getMsg()+"</textarea>");
			out.println("<input type=hidden name=mno value="+mno+">");
			out.println("<input type=hidden name=rno value="+rvo.getRno()+">");
			out.println("<input type=submit value=댓글수정 class=\"btn-sm btn-primary\"style=\"float:left;width:80px;height:98px\">");
			out.println("</form>");
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
			
		}
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		//  댓글 쓰기
		if(id!=null)
		{
			out.println("<form method=post action=ReplyMusicInsert>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<td>");
			out.println("<textarea rows=4 cols=58 name=msg style=\"float:left\" required></textarea>");
			out.println("<input type=hidden name=mno value="+mno+">");
			out.println("<input type=submit value=댓글쓰기 class=\"btn-sm btn-primary\" style=\"float:left;width:80px;height=98px\">");
			out.println("</td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</form>");
		}
		out.println("</div>");
		
		out.println("<div class=col-sm-4>");
		out.println("<h3>인기음악<h3>");
		// 인기 음악 출력
		List<GenieMusicVO> gList=dao.musicHitTop10();
		out.println("<table class=\"table table-striped\" style=\"table-layout:fixed\">");
		out.println("<tr>");
		out.println("<th class=text-center style=\"width:15%\"></th>");
		out.println("<th class=text-center style=\"width:61%\">타이틀</th>");
		out.println("<th class=text-center style=\"width:24%\">조회수</th>");
		out.println("</tr>");
		for(GenieMusicVO gvo:gList)
		{
			out.println("<tr>");
			out.println("<td class=text-center><img src="+gvo.getPoster()+" width=30 height=30></td>");
			out.println("<td style=\"white-space:nowrap;overflow:hidden;text-overflow:ellipsis\">"+gvo.getTitle()+"</td>");
			out.println("<td class=text-center>"+gvo.getHit()+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</div>");
		out.println("</div>");
		
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
