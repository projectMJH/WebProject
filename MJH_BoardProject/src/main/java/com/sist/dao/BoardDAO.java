package com.sist.dao;
import java.util.*;

import com.sist.vo.*;

import oracle.jdbc.OracleTypes;

import java.sql.*;
public class BoardDAO {
	private Connection conn;
	private CallableStatement cs;
	private PreparedStatement ps;
	
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static BoardDAO dao;
	
	public BoardDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	
	public static BoardDAO newInstance()
	{
		try
		{
			if(dao==null)
				dao=new BoardDAO();
		}catch(Exception ex){}

		return dao;
	}
	
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
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
	
	//1.목록 조회
	public List<BoardVO> boardListData(int page)
	{
		List<BoardVO> list = new ArrayList<BoardVO>();
		try
		{
			String sql="{CALL boardAllData(?,?,?)}";
			cs=conn.prepareCall(sql);
			// ?에 값을 채운다
			// in 변수는 동일
			int rowSize=10;
			int start=(page*rowSize)-(rowSize-1);
			int end=(page*rowSize);
			cs.setInt(1, start);
			cs.setInt(2, end);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			// 실행
			cs.executeQuery();
			ResultSet rs=(ResultSet)cs.getObject(3);
			while(rs.next())
			{
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				
//				vo.setEng(rs.getInt(4));
//				vo.setMath(rs.getInt(5));
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
	
	//1.1 total page number
	public int boardTotalPage()
	{
		int total=0;
		
		return total;
		
	}
	//2. 상세 조회
	public BoardVO boardDetailData(int no)
	{
		BoardVO vo=new BoardVO();
		return vo;
	}
	//3. 글쓰기
	//4. 수정하기
	//5. 삭제하기
	//6. 댓글달기


}
