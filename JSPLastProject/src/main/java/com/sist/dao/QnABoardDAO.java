package com.sist.dao;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.vo.*;
import com.sist.commons.*;
public class QnABoardDAO {
	private static SqlSessionFactory ssf;
	static
	{
		ssf=CreateSqlSessionFactory.getSsf();
	}
	public static List<QnABoardVO> qnaListData(Map map)
	{
		SqlSession session=ssf.openSession();
		List<QnABoardVO> list=session.selectList("qnaListData",map);
		session.close();
		return list;
	}
	public static int qnaRowCount()
	{
		SqlSession session=ssf.openSession();
		int total=session.selectOne("qnaRowCount");
		session.close();
		return total;		
	}
	public static void qnaInsert(QnABoardVO vo)
	{
		SqlSession session=ssf.openSession(true);
		session.insert("qnaInsert",vo);
		session.close();
	}
	// => 회원 수정 => id(비활성화) / pwd => addMonth()
	public static QnABoardVO qnaUpdateData(int no)
	{
		SqlSession session=ssf.openSession();
		QnABoardVO vo=session.selectOne("qnaDetailData",no);
		session.close();
		return vo;
	}
	public static List<QnABoardVO> qnaAdminListData(Map map)
	{
		SqlSession session=ssf.openSession();
		List<QnABoardVO> list=session.selectList("qnaAdminListData",map);
		session.close();
		return list;
	}
	public static int qnaAdminRowCount()
	{
		SqlSession session=ssf.openSession();
		int total=session.selectOne("qnaAdminRowCount");
		session.close();
		return total;		
	}
	public static QnABoardVO qnaAdminDetailData(int group_id)
	{
		SqlSession session=ssf.openSession();
		QnABoardVO vo=session.selectOne("qnaAdminDetailData",group_id);
		session.close();
		return vo;
	}
	public static void qnaAdminInsert(QnABoardVO vo)
	{
		// 트랜잭션 => 일괄처리 => 금융권
		SqlSession session=null;
		try
		{
			session=ssf.openSession();
			session.update("qnaAdminOKChange",vo.getGroup_id());
			session.insert("qnaAdminInsert",vo);
			session.commit();	// 동시에 저장
		}catch(Exception ex)
		{
			ex.printStackTrace();
			session.rollback();		// 동시에 취소
		}
		finally
		{
			if(session!=null)
				session.close();
		}
	}
	public static QnABoardVO qnaDetailData(int no)
	{
		SqlSession session=ssf.openSession(true);
		session.update("qnaHitIncrement",no);
		
		QnABoardVO vo=session.selectOne("qnaDetailData",no);
		session.close();
		return vo;
	}
	public static void qnaDelete(int group_id)
	{
		SqlSession session=ssf.openSession(true);
		session.insert("qnaDelete",group_id);
		session.close();
	}
	public static void qnaUpdate(QnABoardVO vo)
	{
		SqlSession session=ssf.openSession(true);
		session.update("qnaUpdate",vo);
		session.close();
	}
	
	public static void qnaAdminDelete(int group_id)
	{
		SqlSession session=ssf.openSession(true);
		session.update("qnaAdminDeleteChange",group_id);
		session.delete("qnaAdminDelete",group_id);
		session.close();
	}
}
