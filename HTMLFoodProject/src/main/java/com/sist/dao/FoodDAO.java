package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private final int ROWSIZE=12;
	
	private static FoodDAO dao;
	public FoodDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	// 2. 싱글턴 => static으로 설정(static 메모리 공간이 한개)
	public static FoodDAO newInstance()
	{
		try
		{
			if(dao==null)
				dao=new FoodDAO();
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
	public FoodVO foodDetailData(int fno)
	{
		FoodVO vo=new FoodVO();
		try
		{
			getConnection();
			String sql="UPDATE food_menupan SET "
					+"hit=hit+1 "
					+"WHERE fno="+fno;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql="SELECT fno,name,type,phone,address,score,theme,"
					+ "poster,images,time,parking,content,hit,price "
					+"FROM food_menupan "
					+"WHERE fno="+fno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			/*
			vo.setFno(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setType(rs.getString(3));
			vo.setPhone(rs.getString(4));
			vo.setAddress(rs.getString(5));
			vo.setScore(rs.getDouble(6));
			vo.setTheme(rs.getString(7));
			vo.setPoster("https://www.menupan.com"+rs.getString(8));
			vo.setImages(rs.getString(9));
			vo.setTime(rs.getString(10));
			vo.setParking(rs.getString(11));
			vo.setContent(rs.getString(12));
			vo.setHit(rs.getInt(13));
			vo.setPrice(rs.getString(14));
			*/
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
	public FoodVO foodCookieData(int fno)
	{
		FoodVO vo= new FoodVO();
		try
		{
			getConnection();
			String sql="SELECT fno,name,poster "
					+"FROM food_menupan "
					+"WHERE fno="+fno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt("fno"));
			vo.setName(rs.getString("name"));
			vo.setPoster("https://www.menupan.com"+rs.getString("poster"));
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
	// INSERT
	public void foodInsertData(FoodVO vo)
	{
		try
		{
			getConnection();
			String sql="INSERT INTO food_menupan(fno,name,type,phone,address,"
					+"score,theme,poster,images,time,parking,content,price) "
					+"VALUES(fm_fno_seq.nextval,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	// UPDATE
	public boolean foodUpdateData(FoodVO vo)
	{
		boolean bCheck=false;
		try
		{
			getConnection();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return bCheck;
	}
	// DELETE
	public boolean foodDeleteData(int fno)
	{
		boolean bCheck=false;
		try
		{
			getConnection();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
		return bCheck;
		
	}


}
