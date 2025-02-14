package com.sist.vo;
/*
 * 		JSP => 사용자 요청에 따른 데이터 출력
 * 			=> <% %> <%= %> 사용하지 않는다
 * 			=> page / taglib
 * 					  ------
 * 			=> 내장 객체
 * 			   ------- request / response / session
 * 			   ------- cookie
 * 			=> HTML / CSS / JavaScript
 * 		DAO => DBCP / MyBatis / JPA
 * 					  -------------
 * 		VO 
 * 		=> 자바로 사용
 */
// => 데이터를 모아서 한번에 브라우저로 전송 목적
// replace("\\",File.separator) => getResource(".")
import java.util.*;

import lombok.Data;
@Data
public class EmpVO {
	private int empno,sal;
	private String ename,job;
	private Date hiredate;

}
