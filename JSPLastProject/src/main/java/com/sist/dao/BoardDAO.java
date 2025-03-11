package com.sist.dao;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.vo.*;
import com.sist.commons.*;
public class BoardDAO {
	private static SqlSessionFactory ssf;
	static
	{
		ssf=CreateSqlSessionFactory.getSsf();
	}
	public static List<BoardVO> boardListData (Map map)
	{
		SqlSession session=ssf.openSession();
		List<BoardVO> list=session.selectList("boardListData",map);
		session.close();
		return list;
	}
	public static int boardTotalPage()
	{
		SqlSession session=ssf.openSession();
		int total=session.selectOne("boardTotalPage");
		session.close();
		return total;		
	}
	public static BoardVO boardDetailData (int no)
	{
		// 트랜잭션
		SqlSession session=ssf.openSession();
		session.update("boardHitIncrement",no);
		session.commit();
		BoardVO vo=session.selectOne("boardDetailData",no);
		session.close();
		return vo;
	}
	public static void boardInsert (BoardVO vo)
	{
		SqlSession session=ssf.openSession(true);
		session.insert("boardInsert",vo);
		session.close();
	}
	public static BoardVO boardUpdateData(int no)
	{
		SqlSession session=ssf.openSession();
		BoardVO vo=session.selectOne("boardDetailData",no);
		session.close();
		return vo;
	}
	public static void boardUpdate(BoardVO vo)
	{
		SqlSession session=ssf.openSession(true);
		session.update("boardUpdate",vo); //insert/update/delete
		// ps.executeUpdate()
		session.close();
	}
	public static String boardGetPassword(int no)
	{
		SqlSession session=ssf.openSession();
		String pwd=session.selectOne("boardGetPassword",no);
		session.close();
		return pwd;
	}
	public static void boardDelete(int no)
	{
		SqlSession session=ssf.openSession(true);
		session.delete("boardDelete",no);
		// ps.executeUpdate()
		session.close();
	}
	
}
