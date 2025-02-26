<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
        논리연산자
        && , ||
            -- 병렬연산자 => 두개의 조건중에 1개이상 true
            -- 
        형식) 조건 &&(||) 조건
            ----       ----
              |         |
              -----------
                   |
                  결과값
        ===============================================    
 --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
window.onload=function(){
	let i=(6<7) && (6===7)
	console.log("i="+i)
	let j=(6<7) || (6===7)
	console.log("j="+j)
	
	// 대입연산자 = , +=, -=
	/*
	   let k=10;
	   k+=10 =========== k=k+10
	   k-=10 =========== k=k-10
	*/
	let n=10/0
	console.log("n="+n)
	
	let g=(6%2===0)?"짝수":"홀수"
    console.log("g="+g)
			
}
</script>
</head>
<body>

</body>
</html>