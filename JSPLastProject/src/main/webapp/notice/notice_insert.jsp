<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
                <form method=post action="../admin/notice_insert_ok.do">
                <table class="table">
                    <tr>
                        <th class="text-center" width=20%>공지</th>
                        <td width=80%>
                            <select name="type" class="input-sm">
                                <option value="1">일반공지</option>
                                <option value="2">이벤트공지</option>
                                <option value="3">맛집공지</option>
                                <option value="4">여행공지</option>
                                <option value="5">레시피공지</option>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <th class="text-center" width=20%>제목</th>
                        <td width=80%>
                            <input type="text" name="subject" id="subject" 
                                required class="input-sm">
                        </td>
                    </tr>
                    <tr>
                        <th class="text-center" width=20%>내용</th>
                        <td width=80%>
                            <textarea cols="52" rows="10" name="content" id="content" required></textarea>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2" class="text-center">
                            <input type="submit" value="글쓰기"class="btn-primary btn-sm">
                            <input type="button" value="취소"class="btn-danger btn-sm"
                             onclick="javascript:history.back()">
                        </td>
                    </tr>
                </table>
                </form>
</body>
</html>