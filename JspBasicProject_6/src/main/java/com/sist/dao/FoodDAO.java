package com.sist.dao;
import java.util.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
/*
 * 		JSP
 * 		----
 * 			=> 화면 출력
 * 			<%@ page contentType="" import=""%>
 * 									---------
 * 			=> 자바 / HTML 구분
 * 				<%  %>
 * 				<%= %> 
 * 			=> 내장객체
 * 				=> request/response/application/session
 * 				=> cookie사용법
 * 			=> jsp액션태그
 * 				<jsp:include>
 * 			=> JDBC / DBCP
 * 			=> JSTL/EL
 * 				<c:forEach>, <c:forTokens>, <c:if>
 * 				<c:set>
 * 				<fmt:formatData> <fmt:formatNumber>
 * 				TO_CHAR(regdate,'')
 * 			---------------------------------------------
 * 			MVC
 */


public class FoodDAO {

	private Connection conn;
	private PreparedStatement ps;
	private final int ROWSIZE=12;
	
	public void getConnection()
	{
		try
		{
			Context init=new InitialContext();
			Context c=(Context) init.lookup("java://comp/env");
			DataSource ds=(DataSource)c.lookup("jdbc/oracle");
			conn=ds.getConnection();
		}catch(Exception ex) {}
	}
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// 기능
	// => 목록 출력 => Cookie
	// 목록 조회
	public List<FoodVO> foodListData(int page)
	{
		List<FoodVO> list=new ArrayList<FoodVO>();
		try
		{
			getConnection();
			String sql="SELECT fno,name,poster,num "
					+"FROM (SELECT fno,name,poster,rownum as num "
					+"FROM (SELECT /*+ INDEX_ASC(food_menupan fm_fno_pk)*/ fno,name,poster "
					+"FROM food_menupan)) "
					+"WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int start=ROWSIZE*page - (ROWSIZE-1);
			int end=ROWSIZE*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setPoster("https://www.menupan.com"+rs.getString(3));
				list.add(vo);
			}
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	// total page number
	public int foodTotalPage()
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(count(*)/"+(double)ROWSIZE+") "
					+"FROM food_menupan";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return total;		
	}
	// 상세 조회
	// => 상세보기 => 지도 API
	public FoodVO foodDetailData(int fno,int mode)
	{
		FoodVO vo=new FoodVO();
		try
		{
			getConnection();
			String sql="";
			if(mode==1)
			{
				sql="UPDATE food_menupan SET "
						+"hit=hit+1 "
						+"WHERE fno="+fno;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
			}
			sql="SELECT fno,name,type,phone,address,score,theme,"
					+ "poster,images,time,parking,content,hit,price "
					+"FROM food_menupan "
					+"WHERE fno="+fno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt("fno"));
			vo.setName(rs.getString("name"));
			vo.setType(rs.getString("type"));
			vo.setPhone(rs.getString("phone"));
			vo.setAddress(rs.getString("address"));
			vo.setTheme(rs.getString("theme"));
			vo.setPoster("https://www.menupan.com"+rs.getString("poster"));
			vo.setImages(rs.getString("images"));
			vo.setTime(rs.getString("time"));
			vo.setParking(rs.getString("parking"));
			vo.setContent(rs.getString("content"));
			vo.setPrice(rs.getString("price"));
			vo.setScore(rs.getDouble("score"));
			vo.setHit(rs.getInt("hit"));
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return vo;
	}

}
