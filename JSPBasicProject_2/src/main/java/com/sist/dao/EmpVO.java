package com.sist.dao;

import java.util.Date;

import lombok.Data;
@Data
public class EmpVO {
	private int empno,mgr,deptno,sal,comm;
	private String ename,job;
	private Date hiredate;
	
	// 변수 => 읽기(getter) / 쓰기(setter)
}
