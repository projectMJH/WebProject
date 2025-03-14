package com.sist.model;
import java.util.*;

import javax.sql.DataSource;

import com.sist.vo.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
/*
 * 		MVC
 * 		=> jsp : 링크 (요청)
 * 		=> 요청 내용 받기
 * 		   ---------- request.getParameter()
 * 		=> 요청 처리 => DAO연동
 * 		=> JSP로 결과값 전송
 * 
 * 		<%@ page ....%>
 * 		<%
 * 			자바 => 출력할 데이터
 * 		%>
 * 		
 * 		Spring : MVC
 * 
 * 		=> 오라클
 * 		=> 브라우저 전송
 */
@Controller
public class RecipeModel {
	// if(uri.equals("recipe/recipe_list.do"))
	@RequestMapping("recipe/recipe_list.do")	// if추가
	public String recipe_list(HttpServletRequest request,HttpServletResponse response)
	{
		// 처리 => 주문서 , 처리후 => 어떤 테이블
		// request addAttribute() => return
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		
		int curpage=Integer.parseInt(page);
		final int ROWSIZE=12;
		Map map=new HashMap();
		map.put("start", curpage*ROWSIZE-(ROWSIZE-1));
		map.put("end", curpage*ROWSIZE);
		
		List<RecipeVO> list=RecipeDAO.recipeListData(map);
		int totalpage=RecipeDAO.recipeTotalPage();
		final int BLOCK=10; 
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage > totalpage)
			endPage=totalpage;
		
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		request.setAttribute("main_jsp", "../recipe/recipe_list.jsp");
		return "../main/main.jsp";		
	}
	@RequestMapping("recipe/chef_list.do")
	public String chef_list(HttpServletRequest request,HttpServletResponse response)
	{
		//request.getParameter();
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		
		int curpage=Integer.parseInt(page);
		final int ROWSIZE=12;
		Map map=new HashMap();
		map.put("start", curpage*ROWSIZE-(ROWSIZE-1));
		map.put("end", curpage*ROWSIZE);
		
		List<ChefVO> list=RecipeDAO.recipeChefListData(map);
		int totalpage=RecipeDAO.recipeChefTotalPage();
		
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		
		request.setAttribute("main_jsp", "../recipe/chef_list.jsp");
		return "../main/main.jsp";
	}
	@RequestMapping("recipe/recipe_find.do")
	public String recipe_find(HttpServletRequest request,HttpServletResponse response)
	{
		String[] findArr=request.getParameterValues("fs");
		if(findArr==null)
			findArr=new String[] {"T"};
		String ss=request.getParameter("ss");
		if(ss==null)
			ss="만개";
		
		Map map=new HashMap();
		map.put("findArr", findArr);
		map.put("ss", ss);
		List<RecipeVO> list=RecipeDAO.recipeFindData(map);
		request.setAttribute("list", list);
		request.setAttribute("main_jsp", "../recipe/recipe_find.jsp");
		return "../main/main.jsp";
	}
	/*
	 * 	1. return "../main/main.jsp" 화면 출력(메뉴 클릭시)
	 *  2. return "../food/food.jsp" ajax 나 viewjs 이용시
	 *  3. return "redirect:../main/main.do" insert,update 수행 후
	 *  
	 *  1.딸기 10개는 깨끗이 씻어서 준비하고 양상추, 치커리는 먹기좋게 손으로 뜯고 채썬 양배추도 준비합니다.^https://recipe1.ezmember.co.kr/cache/recipe/2015/09/30/3d6d5a7fd0ddf7cb3049531c93d0857b1.jpg
		2.그리고 샐러드볼에 모든 재료들을 담고 딸기는 꼭지를 떼어 반으로 썰어주고 통조림 옥수수콘을 뿌려줍니다.^https://recipe1.ezmember.co.kr/cache/recipe/2015/09/30/23032f42ea63a9d7a06cb3ced39e2fd01.jpg
		3.허니 요거트 드레싱을 분량대로 만들어껴얹어서 드심 되어요.^https://recipe1.ezmember.co.kr/cache/recipe/2015/09/30/259b1353bc80be4ce95a7bb345ea0bba1.jpg

	 */
	@RequestMapping("recipe/recipe_detail.do")
	public String recipe_detail(HttpServletRequest request,HttpServletResponse response)
	{
		String no=request.getParameter("no");
		RecipeDetailVO vo=RecipeDAO.recipeDetailData(Integer.parseInt(no));
		List<String> mList=new ArrayList<String>();
		List<String> iList=new ArrayList<String>();
		String[] datas=vo.getFoodmake().split("\n");
		for(String make:datas)
		{
			StringTokenizer st=new StringTokenizer(make, "^");
			mList.add(st.nextToken());
			iList.add(st.nextToken());
		}
		/*
		 * 		<c:forEach var="make" items=${mList }">
		 */
		request.setAttribute("vo", vo);
		request.setAttribute("mList", mList);
		request.setAttribute("iList", iList);
		
		///////////////////////////////////////////////// 댓글
		ReplyVO rvo=new ReplyVO();
		rvo.setRno(Integer.parseInt(no));
		rvo.setType(2);
		List<ReplyVO> list=ReplyDAO.replyListData(rvo);
		int count=ReplyDAO.replyCount(rvo);
		request.setAttribute("rList", list);
		request.setAttribute("count", count);
		/////////////////////////////////////////////////
		
		request.setAttribute("main_jsp", "../recipe/recipe_detail.jsp");
		return "../main/main.jsp";
	}
	@RequestMapping("recipe/chef_make.do")
	public String recipe_chef_make(HttpServletRequest request,HttpServletResponse response)
	{
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		
		final int ROWSIZE=12;
		Map map=new HashMap();
		map.put("start", curpage*ROWSIZE-(ROWSIZE-1));
		map.put("end", curpage*ROWSIZE);

		String no=request.getParameter("no");
		map.put("no", no);
		
		List<RecipeVO> list=RecipeDAO.recipeChefMakeData(map);
		int totalpage=RecipeDAO.recipeChefMakeTotalPage(Integer.parseInt(no));
		final int BLOCK=10; 
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage > totalpage)
			endPage=totalpage;
		
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);	
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("no", no);
		request.setAttribute("chef", list.get(0).getChef());
		
		request.setAttribute("main_jsp", "../recipe/chef_make.jsp");
		return "../main/main.jsp";
	}
	
}
