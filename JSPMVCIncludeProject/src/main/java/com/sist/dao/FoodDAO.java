package com.sist.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.*;
import java.io.*;
import com.sist.vo.*;

public class FoodDAO {
	private static SqlSessionFactory ssf;
	static
	{
		ssf=CreateSqlSessionFactory.getSsf();
	}
	/*
	 *
	  <select id="foodTop12" resultType="FoodVO" parameterType="String">
	    SELECT fno,poster,name,rownum
	    FROM (SELECT fno,poster,name 
	    FROM food_menupan ORDER BY ${column} DESC)
	    WHERE rownum&lt;=12
	  </select>
	 */
	public static List<FoodVO> foodTop12(String column)
	{
		/*
		 * SqlSession session=ssf.openSession(); // conn/ps 
		 * List<FoodVO> list=session.selectList("foodTop12",column); 
		 * session.close(); 
		 * // 반환 (DBCP) => 재사용 
		 * return list;
		 */	
		SqlSession session=null;
		List<FoodVO> list=null;
		try
		{
			session=ssf.openSession();
			list=session.selectList("foodTop12",column);
		}catch(Exception ex)
		{
            StackTraceElement el = ex.getStackTrace()[0]; // 예외가 발생한 첫 번째 위치
			System.out.println("== class "+el.getClassName()+"("+el.getMethodName()+") ===================");
			System.out.println(ex.getMessage());
		}
		finally
		{
			if(session!=null)
				session.close();
		}
		return list;
	}
	
	public static List<FoodVO> foodListData(Map map)
	{
		SqlSession session=null;
		List<FoodVO> list=null;
		try
		{
			session=ssf.openSession();
			list=session.selectList("foodListData",map);
		}catch(Exception ex)
		{
            StackTraceElement el = ex.getStackTrace()[0]; // 예외가 발생한 첫 번째 위치
			System.out.println("== class "+el.getClassName()+"("+el.getMethodName()+") ===================");
			System.out.println(ex.getMessage());
		}
		finally
		{
			if(session!=null)
				session.close();
		}
		return list;		
	}
	
	public static int foodTotalPage()
	{
		SqlSession session=ssf.openSession();
		int total=session.selectOne("foodTotalPage");
		session.close();
		return total;
	}
	
	public static FoodVO foodDetailData(int fno)
	{
		SqlSession session=ssf.openSession();
		session.update("hitIncrement",fno);
		session.commit();
		FoodVO vo=session.selectOne("foodDetailData",fno);
		session.close();
		return vo;
	}
	
	public static List<EmpVO> empListData()
	{
		SqlSession session=ssf.openSession();
		List<EmpVO> list=session.selectList("empListData");
		session.close();
		return list;		
	}
}
