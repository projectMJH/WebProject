<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
                <table class="table">
                    <tr>
                        <th width=20% class="text-center">공지</th>
                        <td width=30% class="text-center">${vo.types }</td>
                        <th width=20% class="text-center">작성일</th>
                        <td width=30% class="text-center">${vo.dbday }</td>
                    </tr>
                    <tr>
                        <th width=20% class="text-center">이름</th>
                        <td width=30% class="text-center">${vo.name }</td>
                        <th width=20% class="text-center">조회수</th>
                        <td width=30% class="text-center">${vo.hit }</td>
                    </tr>
                    <tr>
                        <th width=20% class="text-center">제목</th>
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
                        <td colspan="4" class="text-right">
                            <a href="../admin/notice_update.do?no=${vo.no }" class="btn btn-info btn-xs">수정</a>
                            <a href="../admin/notice_list.do" class="btn btn-warning btn-xs">목록</a>
                        </td>
                    </tr>
                </table>

</body>
</html>