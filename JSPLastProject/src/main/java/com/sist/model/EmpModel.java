package com.sist.model;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.EmpDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.vo.*;
@Controller
public class EmpModel {
	@RequestMapping("mybatis/emp_info.do")
	public String emp_info(HttpServletRequest request,HttpServletResponse response)
	{
		List<EmpVO> list=EmpDAO.empGetEnameData();
		request.setAttribute("list", list);
		return "../mybatis/emp_info.jsp";
	}
	@RequestMapping("mybatis/emp_result.do")
	public String emp_result(HttpServletRequest request,HttpServletResponse response)
	{
		String[] empnos=request.getParameterValues("empno");
		Map map=new HashMap();
		map.put("empnos", empnos);
		List<EmpVO> list=EmpDAO.empFindData(map);
		request.setAttribute("list", list);

		return "../mybatis/emp_result.jsp";
	}
}
