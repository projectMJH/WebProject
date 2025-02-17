package com.sist.model;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;

import jakarta.servlet.http.HttpServletRequest;
/*		
 * 			 요청	
 * 		JSP ================== FoodModel <==> 데이터베이스 연동
 * 		 |							|
 * 		 | 						List<FoodVO>
 * 		 |							|
 * 		 <---------------------------
 * 					request(List<FoodVO>)
 * 						|
 * 					  ${} ==> MV 모델		
 * 
 * 		JSP ================== Controller =====================> Model <===> DB
 * 							  <==========	<====================
 * 		MVC구조 방식 => Spring (MVC)
 * 
 * 		게시판
 * 			BoardList / BoardInsert / BoardUpdate / BoardDelete
 * 			BoardFind		|				|				|
 * 						BoardInsertOK BoardUpdateOK	BoardDeleteOK
 * 			=> class Board
 * 			   {
 * 					--
 * 					--
 * 					--
 * 					--
 * 			   }	
 */
public class FoodModel {
	public void foodListData(HttpServletRequest request)
	{
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		
		Map map=new HashMap();
		int rowSize=12;
		int start=(rowSize*curpage)-(rowSize-1);
		int end=(rowSize*curpage);
		
		map.put("start", start);
		map.put("end", end);
		
		List<FoodVO> list=FoodDAO.foodListData(map);
		int totalpage=FoodDAO.foodTotalPage();
		
		final int BLOCK=10;
		/*
		 * 	1~10 => startPage=1
		 *  1~10 => endPage=10
		 *  
		 *  11~20 => startPage=11
		 *  11~20 => endPage=20
		 */
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage)
			endPage=totalpage;
		
		// JSP로 전송
		// => 화면 출력 => ${}
		request.setAttribute("list", list);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("curpage", curpage);			// request에 값을 추가 => setAttribute()
		request.setAttribute("startPage", startPage);		// Spring => model.addAttribute()
		request.setAttribute("endPage", endPage);		
	}
	
	public void foodDetailData(HttpServletRequest request)
	{
		String fno=request.getParameter("fno");
		FoodVO vo=FoodDAO.foodDetailData(Integer.parseInt(fno));
		
		request.setAttribute("vo", vo);
	}
	

}
