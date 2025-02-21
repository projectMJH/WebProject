package com.sist.dao;
import java.io.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class CreateSqlSessionFactory {
	private static SqlSessionFactory ssf;
	static
	{
		try {
			Reader reader=Resources.getResourceAsReader("Config.xml");
			ssf=new SqlSessionFactoryBuilder().build(reader);
		}catch(Exception ex)
		{
            StackTraceElement el = ex.getStackTrace()[0]; // 예외가 발생한 첫 번째 위치
			System.out.println("== class "+el.getClassName()+"("+el.getMethodName()+") ===================");
			System.out.println(ex.getMessage());
		}
	}
	public static SqlSessionFactory getSsf() {
		return ssf;
	}
}
