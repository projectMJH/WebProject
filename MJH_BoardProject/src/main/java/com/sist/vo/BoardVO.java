package com.sist.vo; 

import java.util.Date;
import lombok.Data; 
@Data
public class BoardVO {
	int no,hit;
	String name,subject,content,pwd,dbday;
	Date regdate;
}
