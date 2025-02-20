package com.sist.model;

import java.io.File;

/*
 * 		String page=request.getParameter("page")
 * 		if (page==null)		=====> page 값이 null 인 경우 오류발생 
 * 			page="1";
 * 		int curpage=Integer.parseInt(page);
 * 		500
 */
public class MainClass {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path="C:\\Users\\sist_108\\git\\WebProject\\JSPMVCLastProject\\src\\main\\java";
		String pack="com.sist.model";
		path=path+"\\"+pack.replace(".", "\\");
		try
		{
			File dir=new File(path);
			File[] files=dir.listFiles();
			for(File file :files)
			{
				//System.out.println(file.getName());
				String name=file.getName();
				String ext=name.substring(name.lastIndexOf(".")+1);
				if(ext.equals("java"))
				{
					String clsName=name.substring(0,name.lastIndexOf("."));
					String packname=pack+"."+clsName;
					System.out.println(packname);
				}
			}
			
		}catch(Exception ex) {}
	}

}
