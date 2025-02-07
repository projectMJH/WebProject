package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class GinieMusicDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	private static GinieMusicDAO dao;
	public GinieMusicDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	
	public static GinieMusicDAO newInstance()
	{
		if(dao==null)
			dao=new GinieMusicDAO();
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
	// 목록 보기
	public List<GinieMusicVO> musicListData(int page)
	{
		List<GinieMusicVO> list=new ArrayList<GinieMusicVO>();
		try
		{
			getConnection();
			String sql="SELECT mno,title,poster,num "
					+"FROM (SELECT mno,title,poster,rownum as num "
					+"FROM (SELECT /*+ INDEX_ASC(ginie_music gm_mno_pk)*/ mno,title,poster "
					+"FROM genie_music)) "
					+"WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(page*rowSize)-(rowSize-1);
			int end=(page*rowSize);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				GinieMusicVO vo=new GinieMusicVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
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
	// 총페이지 구하기
	public int musicTotalPage()
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(count(*)/12.0) "
					+"FROM genie_music";
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
	// 상세보기
	public GinieMusicVO musicDetailData(int mno)
	{
		GinieMusicVO vo=new GinieMusicVO();
		try
		{
			getConnection();
			String sql="UPDATE genie_music SET "
					+"hit=hit+1 "
					+"WHERE mno="+mno;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql="SELECT title,singer,album,poster,hit "
				+"FROM genie_music "
				+"WHERE mno="+mno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setAlbum(rs.getString("album"));
			vo.setTitle(rs.getString("title"));
			vo.setSinger(rs.getString("singer"));
			vo.setPoster(rs.getString("poster"));
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
	// 최근 방문한 페이지
	public GinieMusicVO musicCookieData(int mno)
	{
		GinieMusicVO vo=new GinieMusicVO();
		try
		{
			getConnection();
			String sql="SELECT mno,title,poster "
					+"FROM genie_music "
					+"WHERE mno="+mno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setMno(rs.getInt("mno"));
			vo.setTitle(rs.getString("title"));
			vo.setPoster(rs.getString("poster"));
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
	// 음악 장르별 목록 보기
	public List<GinieMusicVO> musicTypeListData(int page, int cno)
	{
		List<GinieMusicVO> list=new ArrayList<GinieMusicVO>();
		try
		{
			getConnection();
			String sql="SELECT mno,title,poster,num "
					+"FROM (SELECT mno,title,poster,rownum as num "
					+"FROM (SELECT /*+ INDEX_ASC(ginie_music gm_mno_pk)*/ mno,title,poster "
					+"FROM genie_music ";
			if(cno==1)
			{
				sql+=("WHERE cno!="+cno+")) "
					 +"WHERE num BETWEEN ? AND ?");
			}
			else
			{
				sql+=("WHERE cno="+cno+")) "
					 +"WHERE num BETWEEN ? AND ?");
			}
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(page*rowSize)-(rowSize-1);
			int end=(page*rowSize);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				GinieMusicVO vo=new GinieMusicVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
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
	// 장르별 총페이지 구하기
	public int musicTypeTotalPage(int cno)
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(count(*)/12.0) "
					+"FROM genie_music ";
			if(cno==1)
			{
				sql+=("WHERE cno!="+cno);
			}
			else
			{
				sql+=("WHERE cno="+cno);
				
			}
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
	
	// 음악 장르별 검색

}
