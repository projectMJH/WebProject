package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private final int ROWSIZE=12;
	private final int TABLESIZE=12;
	
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
			
			sql="SELECT name,type,phone,address,score,theme,"
					+ "poster,images,time,parking,content,hit,price "
					+"FROM food_menupan "
					+"WHERE fno="+fno;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
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
	// 음식 종류별 검색
	public List<FoodVO> foodTypeFind(int page, String type)
	{
		List<FoodVO> list = new ArrayList<FoodVO>();
		try
		{
			getConnection();
			String sql="";
			int start=ROWSIZE*page - (ROWSIZE-1);
			int end=ROWSIZE*page;
			if(!type.equals("기타"))
			{
				sql="SELECT fno,name,poster,num "
					+"FROM (SELECT fno,name,poster,rownum as num "
					+"FROM (SELECT fno,name,poster "
					+"FROM food_menupan "
					+"WHERE type LIKE '%'||'"+type+"'||'%')) "
					+"WHERE num BETWEEN ? AND ?";
			}
			else
			{
				sql="SELECT fno,name,poster,num "
						+"FROM (SELECT fno,name,poster,rownum as num "
						+"FROM (SELECT fno,name,poster "
						+"FROM food_menupan "
						+"WHERE NOT REGEXP_LIKE(type, '한식|양식|중식|일식|카페'))) "
						+"WHERE num BETWEEN ? AND ?";
			}
			ps=conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt("fno"));
				vo.setName(rs.getString("name"));
				vo.setPoster("https://www.menupan.com"+rs.getString("poster"));
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
	// 음식 종류별 총 페이지 수
	public int foodTypeTotalPage(String type)
	{
		int total=0;
		try
		{
			getConnection();
			String sql="";
			if(!type.equals("기타"))
			{
				sql="SELECT CEIL(count(*)/"+(double)ROWSIZE+") "
					+"FROM food_menupan "
					+"WHERE type LIKE '%'||'"+type+"'||'%'";
			}
			else
			{
				sql="SELECT CEIL(count(*)/"+(double)ROWSIZE+") "
					+"FROM food_menupan "
					+"WHERE NOT REGEXP_LIKE(type, '한식|양식|중식|일식|카페')";
				// 다중 검색
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
	// ?page= &col= &fd=
	public List<FoodVO> foodFind(int page, String col, String fd)
	{
		List<FoodVO> list = new ArrayList<FoodVO>();
		try
		{
			getConnection();
			String sql="SELECT fno,name,poster,address,type,num "
					+"FROM (SELECT fno,name,poster,address,type,rownum as num "
					+"FROM (SELECT fno,name,poster,address,type "
					+"FROM food_menupan "
					+"WHERE "+col+" LIKE '%'||?||'%')) "
					+"WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);

			int start=TABLESIZE*page - (TABLESIZE-1);
			int end=TABLESIZE*page;
			ps.setString(1, fd);
			ps.setInt(2, start);
			ps.setInt(3, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt("fno"));
				vo.setName(rs.getString("name"));
				vo.setPoster("https://www.menupan.com"+rs.getString("poster"));
				vo.setAddress(rs.getString("address"));
				vo.setType(rs.getString("type"));
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
	// 검색별 총 페이지 수
	public int foodFindTotalPage(String col,String fd)
	{
		int total=0;
		try
		{
			getConnection();
			String sql="SELECT CEIL(count(*)/"+(double)TABLESIZE+") "
					+"FROM food_menupan "
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
	// 로그인 처리
	public MemberVO memberLogin(String id, String pwd)
	{
		MemberVO vo=new MemberVO();
		try
		{
			getConnection();
			String sql="SELECT COUNT(*) FROM member "
					+"WHERE id=?";
			ps=conn.prepareStatement(sql);
			// "no="+no
			// "id='"+id+"'" => ps.setString(1,id)
			ps.setString(1,id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();

			if(count==0)
			{
				vo.setMsg("NOID");
			}
			else
			{
				sql="SELECT name,sex,pwd FROM member "
						+"WHERE id=?";	
				ps=conn.prepareStatement(sql);
				ps.setString(1,id);
				rs=ps.executeQuery();
				rs.next();
				vo.setId(id);
				vo.setName(rs.getString("name"));
				vo.setSex(rs.getString("sex"));
				String db_pwd=rs.getString("pwd");
				rs.close();
				if(db_pwd.equals(pwd))
				{
					vo.setMsg("OK");
				}
				else
				{
					vo.setMsg("NOPWD");
				}
			}
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
	// 인기 검색 top 10 
	public List<FoodVO> foodHitTop10()
	{
		List<FoodVO> list = new ArrayList<FoodVO>();
		try
		{
			getConnection();
			String sql="SELECT fno,name,poster,hit,rownum "
					+ "FROM (SELECT fno,name,poster,hit "
					+ "FROM food_menupan ORDER BY hit DESC) "
					+ "WHERE rownum <= 10";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt("fno"));
				vo.setName(rs.getString("name"));
				vo.setPoster("https://www.menupan.com"+rs.getString("poster"));
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
