<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
        1. HTML 태그 읽기
            document: 문서저장 공간
            document.getElementById("ID")
            document.getElementByTagName("태그")
            document.getElementByClassName("class명")
            ***document.querySelector("CSS")
                                     -------
                                     ID => #아이디명
                                     Class => .클래스명
                                     태그명 => 태그명
                                     선택자 => 이벤트 처리가 가능
       2. let 변수명=[] : 배열
            => List list=new ArrayList()
          let 변수명={} : 객체
                   ---- JSON (*******)
            => MovieVO
          사용법 (*********)
          let sawon={sabun:1,name:"홍길동",sex:"남자",dept:"개발부"}
          
          class Sawon
          {
            int sabun;
            String name,sex,dept;
          }                      
          
          Sawon sa=new Sawon()
          sa.sabun=1
          sa.name="홍길동";
          sa.sex="남자";
          sa.dept="개발부";          
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
    margin-top: 50px;
}
.row{
    margin: 0px auto;
    width: 900px;
}
</style>
<script type="text/javascript">
// [] JSONArray
// {} JSONObject == JSON {변수:값}
// let a={"a":1} => a는 객체 => a.a
const sawons=[
	{sabun:1,name:"홍길동",dept:"개발부",job:"대리",pay:3500},
	{sabun:2,name:"심청이",dept:"기획실",job:"과장",pay:4500},
    {sabun:3,name:"박문수",dept:"자재부",job:"부장",pay:5500},
    {sabun:4,name:"강감찬",dept:"인사부",job:"사원",pay:3200},
    {sabun:5,name:"춘향이",dept:"총무부",job:"사원",pay:3300}
]
window.onload=function(){
	let html=""
	sawons.forEach((sawon)=>{
		html+='<li>'+(sawon.sabun+" "+sawon.name+" "+sawon.dept)+'</li>'
	}) 
	document.querySelector('ul').innerHTML=html
	// 어떤 태그에 첨부 => 객체모델
	// document.querySelector('ul')
    //                        $('ul') => <c:out />
    // 함수 => 이벤트 처리
    // ---------------
}
</script>
</head>
<body>
    <ul>
    
    </ul>
</body>
</html>