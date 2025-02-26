<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
        제어문
            조건문
                => 단일 조건문
                    if(조건)
                    {
                        조건 true일때 처리
                    }
                => 선택 조건문
                    if(조건)
                    {
                        조건 true일때 처리
                    }
                    else
                    {
                        조건 false일 때
                    }
                => 다중 조건문
            선택문
            반복문
                while / do~while / for
                
                // 반복 횟수가 없는 경우 => 자바스크립트에서는 사용빈도가 거의 없다
                // 무한 스크롤
                for
                웹 프로그램
                
                *** 조건문 / for => jquery / Vue / React
                = 1. 일반 for
                     for(초기값; 조건식; 증가식)
                     {
                        반복 수행문장 ===> 자바와 동일
                     }
                     for(let i=0;i<10;i++)
                         ------- let/var => const 는 사용할 수 없다
                                            -----
                                            | 상수 => 변경이 불가능
                                            | 자바에서 데이터를 받는 경우
                  2. for-each : 배열 / JSON
                    = for in
                      for(변수 in 배열)
                          ----------
                          배열의 인덱스번호 읽기 (0부터)
                          => 배열 여러개를 동시에 출력할 때
                    = for of
                      for (변수 of 배열)
                           --- 배열의 값을 1개씩 읽어올 때
                    = forEach
                      배열.forEach(function(변수) {})
                                           --- 배열의 값을 1개씩 읽어온다
                    ***= map 
                        배열.map(function(변수) {})
                                        --- 배열의 값을 1개씩 읽어온다
                        배열.map((변수)=> {})
                                    ---- function을 제거 (화살표 함수=> 람다식)                
               
            반복제어문
                break / continue
                ----- 반복문을 중단 / 특정 부분을 제외
        =====
        함수
        =====
        태그제어
        =====
        이벤트
        =====
        객체지향 프로그램 : {} => JSON
        ========================= JavaScript 문법
        => 라이브러리
            1) Jquery => Ajax : 1차
            2) VueJS => axios : JSON
            3) 서버 / 클라이언트 분리
                     |vscode   
                | 이클립스
        
 --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
/*window.onload=function(){
	
}*/
// 많이 사용된다 (function 대신에 =>)
window.onload=()=>{
	const names=["홍길동","심청이","박문수","이순신","춘향이"];
	const sexs=["남자","여자","남자","남자","여자"];
	
	document.write("<h3>일반 for</h3>")
	for(let i=0;i<names.length;i++)
		{
		  document.write(names[i]+"<br>")
		}
	document.write("<hr>")
	document.write("<h3>for-in(인덱스)</h3>")
	for(let index in names)
		{
		  document.write(names[index]+"("+sexs[index]+")<br>")
		}
    document.write("<hr>")
    document.write("<h3>for-of(for-each)</h3>")
    // => 배열에서 데이터를 한개씩 읽어오는 for
    for(let name of names)
    	{
    	   document.write(name+"<br>")
    	}
    document.write("<hr>")
    document.write("<h3>forEach함수1</h3>")
    names.forEach(function(name){
    	document.write(name+"<br>")
    })
    document.write("<hr>")
    document.write("<h3>forEach함수2</h3>")
    names.forEach((name)=>{
    	document.write(name+"<br>")
    })
    document.write("<hr>")
    document.write("<h3>map함수1</h3>")
    names.map(function(name){
    	document.write(name+"<br>")
    })
    document.write("<hr>")
    document.write("<h3>map함수2</h3>")
    names.map((name)=>{
    	document.write(name+"<br>")
    })
    // 개발 경우수가 많다 => 공통
}

</script>
</head>
<body>

</body>
</html>