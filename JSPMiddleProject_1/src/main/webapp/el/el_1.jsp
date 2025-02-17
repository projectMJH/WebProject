<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
        => 제어문 (JSTL) => JSP => MVC 사용시에는 자바를 사용하지 않는다
           ----------- 자바가 없는 것처럼 보인다 => 자바 제어문
                       ----------------- 태그로 만들어진 라이브러리
           *** = core : 제어문, 변수 선언, 화면 이동
                => import : uri="jakarta.tags.core"
                => 제어문 
                   조건문 => else 가 없다 => if문만 사용한다
                        => 단일 조건문만 사용이 가능
                            형식) 
                                <c:if test="${조건문(논리/비교}">
                                      ---- 조건문이 수행
                                  조건이 true면 수행하는 HTML을 사용
                                </c:if>
                   선택문: 다중 조건문 사용
                         <c:choose>
                            <c:When test=""> </c:when>                               
                            <c:When test=""> </c:when>                               
                            <c:When test=""> </c:when>                               
                            ...
                            <c:otherwise></c:otherwise> => else
                         </c:choose>
                   반복문  
                         for(int i=1;i<=10;i==)
                         <c:forEach var="i" begin="1" end="10" step="1">
                                                      -------- --------
                                                      <=10      1인 경우에는 생략이 가능
                            *** 단점 : 감소식은 사용할 수 없다
                                      ---
                                      => 자바에서 반대로 데이터값 추가                             
                         </c:forEach>       
                         => forEach
                         for(MovieVO vo:list)
                         <c:forEach var="vo" items="${list}">
                         </c:forEach>
                         
                         *** <c:forTokens> => StringTokenizer
                         *** JSP에서는 Java 코딩이 없다
                         <% %> <%= %> => 사용하지 않는다
                         
                => 변수 선언 
                   ------- <c:set var="id" value'="hong">
                            String id="hong" (X)
                            request.setAttribute("id","hong") (O)
                => 화면 이동 
                    reponse.sendRedirect("이동할 파일명")
                    <c:redirect url="파일명">

           *** = fmt : 날짜 변환, 숫자 변환 => uri="jakarta.tags.fmt"
                       ------  -------
                                | DecimalFormat => 천단위,
                       | SimpleDateFormat => yyyy-MM-dd
                       ---------------------------------- 
                       | 많이 사용하지 않는다 : 오라클에서 변경이 가능
                                          TO_CHAR
                       <fmt:formatDate>
                       <fmt:formatNumber>                   
           *** = fn : String 클래스의 메서드 호출 => uri="jakarta.tags.function"
                        length(), substring()....
                        ${fn:length(문자열)} => 자바에서 제어
   --------
   = xml : 파싱 => 자바
   = sql : DAO
   ------- MVC/MV 구조에서는 사용하지 않는다 (순수 JSP로 제작)                     
 --%> 
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
        core : 제어문을 지원 / 변수 선언 / 화면 이동
        prefix="c"      => <c:forEach>
        prefix="core"   => <core:forEach>
        --------------- 개발자가 선택
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>el_1.jsp</title>
</head>
<body>
    <%-- forEach : for문 : 반복문 --%>
    <h3>일반 자바 for문</h3>
    <%
        for(int i=1;i<=10;i++)
        {
    %>
            <%= i %>&nbsp;
    <%      
        }
    %>
    <h3>&lt;forEach 사용&gt;</h3>
    <c:forEach var="i" begin="1" end="10" step="1">
        ${i }&nbsp;
    </c:forEach>

</body>
</html>