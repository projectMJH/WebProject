package com.sist.main;

public class ChangeServlet {
	// 출력하지 않는 서블릿은 포함하지 않는다
	// FoodBeforeDetail
	private static String[] pages= {
		"",
		"FoodList",
		"FoodDetail",
		"FoodTypeFind",
		"FoodFind",
		"","","","","",		//5~9
		"","","","","",		//10~14
		"","","","","",		//15~19
		"",					//20
		"MusicList",
		"MusicDetail",
		"MusicTypeFind",
		"MusicFind"
	};
	
	public static String pageChange(int mode) {
		return pages[mode];
	}

}
