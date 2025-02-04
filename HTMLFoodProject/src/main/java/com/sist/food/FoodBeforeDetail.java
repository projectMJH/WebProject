package com.sist.food;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FoodBeforeDetail")
public class FoodBeforeDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fno=request.getParameter("fno");
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
		{
			for(int i=0;i<cookies.length;i++)
			{
				if(cookies[i].getName().equals("food_"+fno))
				{
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					response.addCookie(cookies[i]);
				}
			}
		}
		Cookie cookie=new Cookie("food_"+fno, fno);
		cookie.setPath("/");	// 저장 위치 지정
		cookie.setMaxAge(60*60*24); //쿠기 보관 기간 (초단위) => 24시간 보관
		response.addCookie(cookie);
		// 쿠기 => 브라우저에 저장(클라이언트에 저장)
		// 보안에 취약 / 저장 => 문자열만 저장이 가능
		// => 최신 방문 / 로그인 여부
		// => 서버에 저장 (세션)
		// => Map 방식 (키, 값) => 키는 중복이 불가능
		// 상세보기로 이동 => 서버에서 화면 이동 : response.sendRedirect()
		
		response.sendRedirect("FoodDetail?fno="+fno);
		//		 ------------- GET 방식
		/*
		 * 	사이트
		 *  ----
		 *  	목록 = 인라인 뷰(페이징)
		 *  	상세보기 (찜하기, 좋아요, 예약, 결제) => 댓글
		 *  	커뮤니티 : 게시판, 묻고 답하기, 자료실, 실시간 채팅
		 *  	회원가입, 검색
		 *  
		 *  	=> 관리자 페이지 / 마이 페이지
		 */
	}

}
