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
}
