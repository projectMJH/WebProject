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

                </table>
                
                <form method=post action="../qna/qna_admin_insert_ok.do">
                <table class="table">
                    <tr>
                        <th class="text-center" width=20%>제목</th>
                        <td width=80%>
                            <input type="text" name="subject" id="subject" class="input-sm" required>
                            <input type=hidden name=group_id value="${vo.group_id }">
                        </td>
                    </tr>
                    <tr>
                        <th class="text-center" width=20%>내용</th>
                        <td width=80%>
                            <textarea cols="52" rows="10" name="content" id="content" required></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th class="text-center" width=20%>비밀번호</th>
                        <td width=80%>
                            <input type="password" name="pwd" id="pwd" class="input-sm" required>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="text-center">
                            <input type="submit" value="답변"class="btn-primary btn-sm">
                            <input type="button" value="취소"class="btn-danger btn-sm"
                             onclick="javascript:history.back()">
                        </td>
                    </tr>
                </table>
                </form>
                

</body>
</html>