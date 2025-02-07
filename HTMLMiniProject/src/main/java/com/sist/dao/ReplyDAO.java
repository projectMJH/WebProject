package com.sist.dao;

/*
 * 		Servlet / JSP
 * 		-------	  ---
 * 				  | HTML화면 (View)
 * 		| 보안이 뛰어나다 => 배포 (.class)
 * 		  => 로직, 보안 => 연결용, 라이브러리 => HTML을 출력하지 않는다
 * 							  ======= 스프링
 * 		| HTML 구현부분이 어렵다 (out.println("... \"... \"... "))
 * 		| HTML 을 약간 수정시 마다 => 컴파일 / 실행
 *		-----------------------------------------------------
 *		JSP
 *		=> 자바 + HTML : 구분이 어렵다
 *		   ----------
 *		   자바 	--- (Model)
 *				  | ------- 서블릿(Controller) => MVC (*****)
 *		   HTML --- (View)
 *		   ** Spring 은 MVC 구조만 사용이 가능
 *		   ** URL => 확장자 => .jsp (Model 1) => 재사용이 없다
 *			  ----------- 사용자 .do / .naver (스프링으로 구현)
 * 		
 * 		=> 1. SQL 문장이 없다 (JPA)
 * 		=> 2. HTML에 제어문이 가능 (JSP가 필요없다)
 * 			  Vue/React/ThymeLeaf
 * 		=> Front / Back => MSA
 */
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class ReplyDAO {
	private Connection conn;
	private PreparedStatement ps;
	
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static ReplyDAO dao;
	
	public ReplyDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {}
	}
	// 2. 싱글턴 => static으로 설정(static 메모리 공간이 한개)
	public static ReplyDAO newInstance()
	{
		try
		{
			if(dao==null)
				dao=new ReplyDAO();
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
	
	// 기능
	// DML => SELECT / UPDATE /DELETE / INSERT
	// 글쓰기
	public void replayInsert(ReplyVO vo)
	{
		try
		{
			getConnection();
			String sql="INSERT INTO reply VALUES("
					+"reply_rno_seq.nextval,?,?,?,?,SYSDATE)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getFno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
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
	// 목록보기
	public List<ReplyVO> replyListData(int fno)
	{
		List<ReplyVO> list=new ArrayList<ReplyVO>();
		try
		{
			getConnection();
			String sql="SELECT rno,fno,id,name,msg,"
					+ "TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS') dbday "
					+ "FROM reply "
					+ "WHERE fno="+fno
					+ "ORDER BY rno DESC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ReplyVO vo=new ReplyVO();
				vo.setRno(rs.getInt("rno"));
				vo.setFno(rs.getInt("fno"));
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setMsg(rs.getString("msg"));
				vo.setDbday(rs.getString("dbday"));
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
	//댓글 삭제
	public void replyDeleteData(int rno, String id)
//	public void replyDeleteData(int rno)
	{
		try {
			getConnection();
			String sql="DELETE FROM reply "
					+ "WHERE rno="+rno
					+ "AND id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
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
}
