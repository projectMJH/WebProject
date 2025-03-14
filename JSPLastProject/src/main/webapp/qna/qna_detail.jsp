<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <!-- ****** Breadcumb Area Start ****** -->
    <div class="breadcumb-area" style="background-image: url(../img/bg-img/breadcumb.jpg);">
        <div class="container h-100">
            <div class="row h-100 align-items-center">
                <div class="col-12">
                    <div class="bradcumb-title text-center">
                        <h2>묻고 답하기</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="breadcumb-nav">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#"><i class="fa fa-home" aria-hidden="true"></i> Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">상세 보기</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
    <!-- ****** Breadcumb Area End ****** -->

    <!-- ****** Archive Area Start ****** -->
    <section class="archive-area section_padding_80">
      <div class="container">
        <div class="row" style="width:800px;">
          <table class="table">
            <tr>
                <th width=20% class="text-center table-danger">번호</th>
                <td width=30% class="text-center">${vo.no }</td>
                <th width=20% class="text-center table-danger">작성일</th>
                <td width=30% class="text-center">${vo.dbday }</td>
            </tr>
            <tr>
                <th width=20% class="text-center table-danger">이름</th>
                <td width=30% class="text-center">${vo.name }</td>
                <th width=20% class="text-center table-danger">조회수</th>
                <td width=30% class="text-center">${vo.hit }</td>
            </tr>
            <tr>
                <th width=20% class="text-center table-danger">제목</th>
                <td colspan="3" class="text-left">${vo.subject }</td>
            </tr>
            <tr>
                <td colspan="4" class="text-left" valign="top" height="200">
                    <pre style="white-space: pre-wrap; border: none; background-color: white;">
                        ${vo.content }
                    </pre>
                </td>
            </tr>
            <tr>
                <td colspan=4 class="text-right">
                  <c:if test="${sessionScope.id!=null and sessionScope.id==vo.id }">
                    <a href="../qna/qna_update.do?no=${vo.no }" class="btn btn-xs btn-info">수정</a>
                    <a href="../qna/qna_delete.do?group_id=${vo.group_id }" class="btn btn-xs btn-danger">삭제</a>
                  </c:if>
                    <a href="../qna/qna_list.do" class="btn btn-xs btn-primary">목록</a>
                </td>
            </tr>
        </table>
        </div>
      </div>
    </section>
  </body>
</html>