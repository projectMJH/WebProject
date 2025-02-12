<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.io.*"%>
<%
Part filePart=request.getPart("upload");
String fileName=filePart.getSubmittedFileName();
//String uploadDir="C:\\Users\\sist_108\\git\\WebProject\\JSPBasicProject_4\\src\\main\\webapp\\image";
String uploadDir=application.getRealPath("/image");
File file=new File(uploadDir,fileName);
// 오라클 => 파일업로드 
// try ~ resource => 자동으로 input/output 닫긴다
  try(InputStream input=filePart.getInputStream();
      FileOutputStream output=new FileOutputStream(file)
     )
 {
     byte[] buffer=new byte[1024];
     int i=0;
     while((i=input.read(buffer,0,1024))!=-1)
     {
         output.write(buffer, 0, i);
     }
 }

 response.sendRedirect("output.jsp?img="+fileName);

%>