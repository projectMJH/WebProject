package com.sist.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.net.*;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> clsList=new ArrayList<String>();

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try
		{
			// RealPath
			URL url=this.getClass().getClassLoader().getResource(".");
			// WEB-INF/classes
			// 파일로 변경
			File file=new File(url.toURI());
			//System.out.println(file.getPath());
			String path=file.getPath();
			path=path.replace("\\", File.separator);
			// => window: \\ , mac,linux : /
			// => AWS => 우분투
			path=path.substring(0,path.lastIndexOf(File.separator));
			// WEB-INF/application.xml
			path=path+File.separator+"application.xml";
			
			// 패키지 명칭
			// XML 파싱기 생성
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newDefaultInstance();
			// XML 파싱기
			DocumentBuilder db=dbf.newDocumentBuilder();
			// XML 파싱 시작 => 태그나 속성에 있는 데이터 추출
			Document doc=db.parse(new File(path));
			// 최상위 태그 => 데이터베이스 (테이블 역할)
			Element beans=doc.getDocumentElement();
			// 같은 이름의 태그를 묶어서 관리
			NodeList list=beans.getElementsByTagName("context:component-scan");
			
			String pack="";
			for(int i=0; i<list.getLength();i++)
			{
				Element elem= (Element)list.item(i);
				pack=elem.getAttribute("basePackage");
			}
			//System.out.println(pack);
			clsList=com.sist.controller.FileReader.componentScan(file.getPath(), pack);
		}catch(Exception ex) 
		{
			System.out.println("== class DispatcherServlet(init) ===================");
			System.out.println(ex.getMessage());
		}
	}
	/*
	 * 		class B
	 * 		A a=new A();
	 */
	// 사용자 요청시에 Model연결 => 결과값을 JSP 전송
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 1. 사용자 요청 정보를 받는다
		// /JSPMVCLastProject/main/main.do
		// ------------------
		//    ContextPath()
		String uri=request.getRequestURI();
		uri=uri.substring(request.getContextPath().length()+1);
		//System.out.println(uri);
		
		try
		{
			for(String cls:clsList)
			{
				Class clsName=Class.forName(cls);
				// 클래스의 모든 정보 읽기
				// 클래스 위에 @Controller 가 있는지 확인 (Model class 만으로 객체 생성)
				if(clsName.isAnnotationPresent(Controller.class)==false)
					continue;
				Object obj=clsName.getDeclaredConstructor().newInstance();
				// 해당 메서드를 찾기 시작
				Method[] methods=clsName.getDeclaredMethods();
				for(Method m:methods)
				{
					RequestMapping rm=m.getAnnotation(RequestMapping.class);
					if(rm.value().equals(uri))
					{
						// food_list()
						String jsp=(String)m.invoke(obj, request,response);
						if(jsp==null) // void
						{
							return; // ajax => Model / 자바스크립트 통신
						}
						else if(jsp.startsWith("redirect:"))
						{
							// sendRedirect => _ok
							jsp=jsp.substring(jsp.indexOf(":")+1);
							response.sendRedirect(jsp);
						}
						else
						{
							// forward => request를 JSP로 전송
							RequestDispatcher rd=request.getRequestDispatcher(jsp);
							rd.forward(request, response);
						}
						return;
					}
				}
			}
			
		}catch(Exception ex)
		{
			System.out.println("== class DispatcherServlet(service) ===================");
			System.out.println(ex.getMessage());
		}
	}

}
