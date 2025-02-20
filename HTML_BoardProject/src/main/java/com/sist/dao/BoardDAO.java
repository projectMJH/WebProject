package com.sist.dao;
// DBCP(web) / JDBC
import java.util.*;
import java.sql.*;

import com.sist.vo.*;
/*
 * 							자바
 * 							 |
 * 				-------------------------
 * 				| 브라우저(HTML)연동		 | 오라클 연동
 * 			브라우저 <====================> 오라클 (게시판 데이터)
 * 							연결
 *  		  |
 *  HTML/CSS/JavaScript : 화면 UI
 */
public class BoardDAO {

	private Connection conn;
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
		if(dao==null)
			dao=new BoardDAO();
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

	// 1. 목록보기
	public List<BoardVO> boardListData(int page)
	{
		List<BoardVO> list=new ArrayList<BoardVO>();
		// sql.* => 컴파일 예외처리 => CheckException
		try
		{
			getConnection();
			String sql="SELECT no,name,subject,TO_CHAR(regdate,'YYYY-MM-DD') as dbday,hit,num "
					+ "FROM (SELECT no,name,subject,regdate,hit,rownum num "
					+ "FROM (SELECT no,name,subject,regdate,hit "
					+ "FROM htmlboard ORDER BY no DESC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=10;
			int start=(page*rowSize)-(rowSize-1);
			int end=(page*rowSize);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
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
	
	//1-1 총페이지
	// int total=mybatis.selectOne("sql")
	// int total=jpa.count();
	public int boardTotalPage()
	{
		int total=0;
		try {
			getConnection();
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM htmlboard";
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
	
	// 2. 상세보기
	public BoardVO boardDetailData(int no)
	{
		BoardVO vo=new BoardVO();
		try
		{
			getConnection();
			String sql="UPDATE htmlBoard SET "
					+"hit=hit+1 "
					+"WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate(); // 조회수 증가
			
			sql="SELECT no,name,subject,content,"
					+ "TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS'),hit "
					+ "FROM htmlboard "
					+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(6));
			vo.setHit(rs.getInt(7));
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
	//3.글쓰기
	//	=> 웹 프로그램 (감) => 화면 이동 => 어떤 데이터가 필요한지...
	public void boardInsert(BoardVO vo)
	{
		try
		{
			getConnection();
			String sql="INSERT INTO htmlBoard(no,name,subject,content,pwd) "
					+"VALUES(hb_no_seq.nextval,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			//?에 값을 채워서 실행
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			// 실행
			ps.executeUpdate(); // commit이 포함
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			disConnection();
		}
	}
	//4.
	//5. 삭제 DELETE
	public boolean boardDelete(int no, String pwd)
	{
		/*
		 * 	오라클 => 사이트에 필요한 데이터 저장 => SQL문장
		 * 
		 *  자바 => 오라클 연동 / 브라우저 연동
		 *  	  결과값을 받아서 => 브라우저로 전송
		 * 		  사용자 요청을 받는 경우
		 * 		  => 스프링 : 자바 / 코틀린
		 * 		  => ASP : C#, 장고 : 파이썬 => 통계 그래프
		 *  HTML/CSS => 브라우저에 화면만 출력
		 */
		boolean bCheck=false;
		try
		{
			getConnection();
			//1. 비밀번호 체크
			String sql="SELECT pwd FROM htmlBoard "
					+"WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String dbPwd=rs.getString(1);
			rs.close();
			
			if(pwd.equals(dbPwd))
			{
				//2. 삭제
				bCheck=true;
				sql="DELETE FROM htmlBoard "
						+"WHERE no="+no;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
			}
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
	//6.수정하기
	public boolean boardUpdate(BoardVO vo)
	{
		boolean bCheck=false;
		try
		{
			getConnection();
			//1. 비밀번호 체크
			String sql="SELECT pwd FROM htmlBoard "
					+"WHERE no="+vo.getNo();
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String dbPwd=rs.getString(1);
			rs.close();
			
			if(vo.getPwd().equals(dbPwd))
			{
				bCheck=true;
				sql="UPDATE htmlBoard SET "
					+"name="+vo.getName()
					+",subject="+vo.getSubject()
					+",content="+vo.getContent()
					+"WHERE no="+vo.getNo();
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
			}	
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
	//7.삭제하기
	
}
