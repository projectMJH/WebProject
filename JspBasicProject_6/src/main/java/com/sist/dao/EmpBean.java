package com.sist.dao;
import java.util.*;

import lombok.Data;
@Data
public class EmpBean {
	private int empno,sal,deptno;
	private String ename,job,dbday;
	private Date hiredate;
}
