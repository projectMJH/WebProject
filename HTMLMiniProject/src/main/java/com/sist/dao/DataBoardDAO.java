package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;

public class DataBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static DataBoardDAO dao;
	
	public DataBoardDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {}
	}
	// 2. 싱글턴 => static으로 설정(static 메모리 공간이 한개)
	public static DataBoardDAO newInstance()
	{
		try
		{
			if(dao==null)
				dao=new DataBoardDAO();
		}catch(Exception ex) {}
		return dao;
	}
	
	// 오라클 연결
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	
	// 오라클 닫기
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
}	
