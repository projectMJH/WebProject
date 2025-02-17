package com.sist.vo;

import lombok.Data;

/*
 NO            NOT NULL NUMBER
 GOODS_NAME   NOT NULL VARCHAR2(1000)
 GOODS_SUB    VARCHAR2(1000)
 GOODS_PRICE  NOT NULL VARCHAR2(50)
 GOODS_DISCOUNT    NUMBER
 GOODS_FIRST_PRICE  VARCHAR2(20)
 GOODS_DELIVERY   NOT NULL VARCHAR2(20)
 GOODS_POSTER     VARCHAR2(260)
 HIT          NUMBER
 * 
 */
// 1. MyBatis => 컬럼명이 동일하지 않는 경우에는 반드시 별칭
// 2. 컬럼명이 일치하지 않는 경우에는 반드시 설정
/*
 * 	private int no,gd,hit;
 * 	SELECT goods_discount as gd
 * 
 *  <result property="gd" column="goods_discount">
 *  	=> JOIN / SUBQUERY
 *  => 함수이용하면 반드시 별칭
 *  	TO_CHAR() as dbday
 *  => 자동으로 값을 채워준다
 *  
 *  SELECT a as d,b,c FROM ...
 *  setD() setB() setC()
 * 
 */
@Data
public class GoodsVO {
	private int no,goods_discount,hit;
	private String goods_name,goods_sub,goods_price,
			goods_first_price,goods_delivery,goods_poster;
}
