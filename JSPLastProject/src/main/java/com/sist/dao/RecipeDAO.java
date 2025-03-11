package com.sist.dao;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.vo.*;
import com.sist.commons.*;
public class RecipeDAO {
	private static SqlSessionFactory ssf;
	static
	{
		ssf=CreateSqlSessionFactory.getSsf();
	}
	public static ChefVO recipeTodayChef()
	{
		ChefVO vo=null;
		SqlSession session=null;
		try
		{
			session=ssf.openSession();
			vo=session.selectOne("recipeTodayChef");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return vo;
	}
	public static List<RecipeVO> recipeData7()
	{
		List<RecipeVO> list=null;
		SqlSession session=null;
		try
		{
			session=ssf.openSession();
			list=session.selectList("recipeData7");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return list;
	}
	public static List<RecipeVO> recipeListData(Map map)
	{
		List<RecipeVO> list=null;
		SqlSession session=null;
		try
		{
			session=ssf.openSession();
			list=session.selectList("recipeListData",map);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return list;
	}
	public static int recipeTotalPage()
	{
		SqlSession session=ssf.openSession();
		int total=session.selectOne("recipeTotalPage");
		session.close();
		return total;
	}
	public static List<ChefVO> recipeChefListData(Map map)
	{
		List<ChefVO> list=null;
		SqlSession session=null;
		try
		{
			session=ssf.openSession();
			list=session.selectList("recipeChefListData",map);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return list;
	}
	public static int recipeChefTotalPage()
	{
		int total=0;
		SqlSession session=null;
		try
		{
			session=ssf.openSession();
			total=session.selectOne("recipeChefTotalPage");
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return total;
	}
	/*
	 * 		1. MyBaits
	 * 			DML => select , update, delete, insert, sql
	 * 					  |
	 * 				   resultMap : JOIN / SUBQUERY
	 * 			동적 쿼리
	 * 				=> <trim>	: 추가 / 제거
	 * 				=> <bind>	: 변수형 = 문장이 긴 경우
	 * 				   <bind name="likes" value="'%'||#{ss}||'%'">
	 * 					#{likes}
	 * 				=> <foreach> : in 연산자 데이터 여러개 (checkbox)
	 * 					<foreach collection="arr" item="no">
	 * 										==== Map key
	 * 					for(int no:arr)
	 * 					=> 배열 / 컬렉션
	 * 						=> 반드시 Map에 채워서 설정	
	 * 				=> <where>	
	 * 					<where>
	 * 						<if test="조건">AND id=#{id}</if>
	 * 						<if test="조건">AND pwd=#{pwd}</if>
	 * 					</where>
	 * 				=> <if>	
	 * 					=> 단일 조건문
	 * 				=> <choose>	: 다중 조건문
	 * 					<when test=""></when>
	 * 					<when test=""></when>
	 * 					<otherwise></otherwise>
	 * 				   </choose>
	 * 				=> 인정 : sql / css
	 * 
	 * 				Model / DAO / VO	=> Back
	 * 				JSP / React / Vue / Jquery(Ajax) => Front
	 * 				XML => DBA
	 * 
	 */
	public static List<RecipeVO> recipeFindData(Map map)
	{
		SqlSession session=ssf.openSession();
		List<RecipeVO> list=session.selectList("recipeFindData",map);
		session.close();
		return list;
	}
}
