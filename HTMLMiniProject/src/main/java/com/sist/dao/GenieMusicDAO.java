package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class GenieMusicDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	private static GenieMusicDAO dao;
	public GenieMusicDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	
	public static GenieMusicDAO newInstance()
	{
		if(dao==null)
			dao=new GenieMusicDAO();
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
	public List<GenieMusicVO> musicListData(int page)
	{
		List<GenieMusicVO> list=new ArrayList<GenieMusicVO>();
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
				GenieMusicVO vo=new GenieMusicVO();
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
	public GenieMusicVO musicDetailData(int mno)
	{
		GenieMusicVO vo=new GenieMusicVO();
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
	public GenieMusicVO musicCookieData(int mno)
	{
		GenieMusicVO vo=new GenieMusicVO();
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
	public List<GenieMusicVO> musicTypeListData(int page, int cno)
	{
		List<GenieMusicVO> list=new ArrayList<GenieMusicVO>();
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
				GenieMusicVO vo=new GenieMusicVO();
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
	
	// 음악 검색
	public List<GenieMusicVO> musicFind(int page,String col,String fd)
	{
		List<GenieMusicVO> list=new ArrayList<GenieMusicVO>();
		try
		{
			getConnection();
			String sql="SELECT mno,title,poster,num "
					+"FROM (SELECT mno,title,poster,rownum as num "
					+"FROM (SELECT /*+ INDEX_ASC(ginie_music gm_mno_pk)*/ mno,title,poster "
					+"FROM genie_music "
					+"WHERE "+col+" LIKE '%'||?||'%')) "
					+"WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(page*rowSize)-(rowSize-1);
			int end=(page*rowSize);
			ps.setString(1, fd);
			ps.setInt(2, start);
			ps.setInt(3, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				GenieMusicVO vo=new GenieMusicVO();
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
	// 음악 검색 총 페이지수
	public int musicFindTotalPage(String col,String fd)
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(count(*)/12.0) "
					+"FROM genie_music "
					+"WHERE "+col+" LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
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
	// 음악 인기 검색 top 10
	public List<GenieMusicVO> musicHitTop10()
	{
		List<GenieMusicVO> list = new ArrayList<GenieMusicVO>();
		try
		{
			getConnection();
			String sql="SELECT mno,title,poster,hit,rownum "
					+"FROM (SELECT mno,title,poster,hit "
					+"FROM genie_music ORDER BY hit DESC) "
					+"WHERE rownum <= 10";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				GenieMusicVO vo=new GenieMusicVO();
				vo.setMno(rs.getInt("mno"));
				vo.setTitle(rs.getString("title"));
				vo.setPoster(rs.getString("poster"));
				vo.setHit(rs.getInt("hit"));
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

}
