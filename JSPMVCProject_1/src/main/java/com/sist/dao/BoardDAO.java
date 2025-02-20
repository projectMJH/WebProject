package com.sist.dao;
import java.util.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.*;

import com.sist.vo.*;

public class BoardDAO {
	private static SqlSessionFactory ssf;
	//초기화 => XML에 등록된 데이터를 전송 => ssf
	static
	{ 
		try
		{
			// 1. XML 읽기
			Reader reader=Resources.getResourceAsReader("Config.xml");
			// 2. XML 파싱
			ssf=new SqlSessionFactoryBuilder().build(reader);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	/*
	 * 	1. 목록
    <select id="boardListData" resultType="BoardVO" parameterType="hashmap">
        SELECT no,subjec,name,regdate,hit,num
        FROM (SELECT no,subjec,name,regdate,hit,rownum as num
        FROM (SELECT no,subjec,name,regdate,hit
        FROM board ORDER BY no ASC)))
        WHERE num BETWEEN #{start} AND #{end}
    </select>
	 * 
	 * 	2. 총페이지
    <select id="boardTotalPage" resultType="int">
        SELECT CEIL(COUNT(*)/10.0) FROM board
    </select>
	 * 
	 */
	
	public static List<BoardVO> boardListData(Map map)
	{
		// conn, ps
		SqlSession session=ssf.openSession();
		List<BoardVO> list=session.selectList("boardListData",map);
		// 목록 => 여러개  selectList
		// 상세보기 => 한개 selectOne
		session.close();
		
		return list;
	}
	public static int boardTotalPage()
	{
		// conn, ps
		SqlSession session=ssf.openSession();
		int total=session.selectOne("boardTotalPage");
		// 목록 => 여러개  selectList
		// 상세보기 => 한개 selectOne
		session.close();
		
		return total;
	}
	
	public static BoardVO boardDetailData(int no)
	{
		// conn, ps
		SqlSession session=ssf.openSession();
		// 조회수 증가
		session.update("hitIncrement",no);
		session.commit();
		// 데이터 가지고 오기
		BoardVO vo=session.selectOne("boardDetailData",no);
		// 반환
		session.close();
		
		return vo;
	}

	/*
    <insert id="boardInsert" parameterType="BoardVO">
        <!-- 마이바티스 자동 증가번호 제작
            order => BEFORE: 먼저 수행
                     AFTER : 나중에 수행 
                     
         -->
        <selectKey keyProperty="no" resultType="int" order="BEFORE">
            SELECT NVL(MAX(no)+1,1) as no FROM board
        </selectKey>
        INSERT INTO board VALUES(
            #{no},#{name},#{subject},#{content},
            #{pwd},SYSDATE,0
        )
    </insert>
	 * 
	 */
	public static void boardInsert(BoardVO vo)
	{
		SqlSession session=ssf.openSession(); // autocommit (true)
		session.insert("boardInsert",vo);
		// commit이 없다
		session.commit();	// update, insert, delete
		session.close();
	}
}
