package com.sist.main;
import java.util.*;

import oracle.jdbc.OracleTypes;

import java.sql.*;

public class StudentDAO {
	private Connection conn;
	private CallableStatement cs;
	// Procedure 호출시에 사용
	
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static StudentDAO dao;
	
	public StudentDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	
	public static StudentDAO newInstance()
	{
		if(dao==null)
			dao=new StudentDAO();
		return dao;
	}
	
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex)
		{
			
		}
	}
	
	public void disConnection()
	{
		try
		{
			if(cs!=null) cs.close();
			if(conn!=null) conn.close();
		}catch(Exception ex)
		{
			
		}
	}
	// 목록 출력
	public List<StudentVO> studentAllData()
	{
		List<StudentVO> list=new ArrayList<StudentVO>();
		try
		{
			getConnection();
			String sql="{CALL studentAllData(?)}";
			cs=conn.prepareCall(sql);
			// OUT변수일때만 사용된다. 
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			// INTEGER(정수), VARCHAR, CURSOR
			// 실행요청
			cs.executeQuery();
			// CURSOR => ResultSet
			// MyBatis
			ResultSet rs=(ResultSet)cs.getObject(1);
			while(rs.next())
			{
				StudentVO vo=new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
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
	// 상세보기
	//
	public StudentVO studentDetailData(int hakbun)
	{
		StudentVO vo=new StudentVO();
		try
		{
			getConnection();
			String sql="{CALL studentDetailData(?,?)}";
			cs=conn.prepareCall(sql);
			// ?에 값을 채운다
			// in 변수는 동일
			cs.setInt(1, hakbun);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			// 실행
			cs.executeQuery();
			ResultSet rs=(ResultSet)cs.getObject(2);
			rs.next();
			vo.setHakbun(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setKor(rs.getInt(3));
			vo.setEng(rs.getInt(4));
			vo.setMath(rs.getInt(5));
			
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
