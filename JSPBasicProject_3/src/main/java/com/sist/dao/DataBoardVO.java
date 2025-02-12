package com.sist.dao;
import java.util.*;
import java.util.Date;

import lombok.Data;
// JSP => Bean => 데이터를 모아서 관리 / 전송
// Spring => VO
// Mybatis => DTO
// 자료실 한개에 대한 정보만 가지고 있다. 
/*
 * 		1. 목록
 * 			DataBoardVO 여러개 => List<DataBoardVO>
 * 		2. 
 * 
 */
@Data
public class DataBoardVO {
	private int no,hit,filesize;
	private String name,subject,content,pwd,filename,dbday;
	private Date regdate;
}
