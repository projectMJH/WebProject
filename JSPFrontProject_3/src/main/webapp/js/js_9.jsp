<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
        JavaScript : JQuery
                     ------
                     JDBC = MyBatis
                     
        document.querySelector('#btn').addEventListener(function(){}) => JavaScript
        $('#btn').click(function(){})                                 => Jquery
        
==> 345page 문서객체 모델
            --- document
    DOMScript (태그를 제어하는 프로그램)
    => 태그를 선택해서 처리
        1. 태그 한개를 가지고 올 경우
           태그 : Element
           속성 : Attribute
           값 <a  href="">aaa</a> => Text
              -- -----   ----
              태그 속성     값
           document.getElementById(아이디명) => id
           document.querySelector(CSS)   
        2. 태그 여러개를 가지고 오는 경우
           document.getElementByTagName()
           document.getElementByClassName()
           document.querySelectorAll()
           CSS처리 / 사용자 요청 처리 (*****)
 --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>