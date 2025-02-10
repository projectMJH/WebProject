package com.sist.dao;

import java.sql.*;
import java.util.*;
import com.sist.vo.*;

public class ReplyMusicDAO {
	private Connection conn;
	private PreparedStatement ps;
	
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static ReplyMusicDAO dao;
	
	public ReplyMusicDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {}
	}
	// 2. 싱글턴 => static으로 설정(static 메모리 공간이 한개)
	public static ReplyMusicDAO newInstance()
	{
		try
		{
			if(dao==null)
				dao=new ReplyMusicDAO();
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
			String sql="INSERT INTO replymusic VALUES("
					+"rm_rno_seq.nextval,?,?,?,?,SYSDATE)";
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
					+ "FROM replymusic "
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
			String sql="DELETE FROM replymusic "
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
	//댓글 수정
	public void replyUpdateData(int rno, String msg)
	{
		try
		{
			getConnection();
			String sql="UPDATE replymusic SET "
					+ "msg=? "
					+ "WHERE rno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, msg);
			ps.setInt(2, rno);
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
