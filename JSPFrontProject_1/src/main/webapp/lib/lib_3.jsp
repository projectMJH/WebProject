<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- input type에 대한 크기 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>lib_3.jsp</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
    margin-top: 50px;
}
.row{
    margin: 0px auto;
    width: 800px;
}
</style>
</head>
<body>
    <div class="container">
        <div class="row">
        이름: <input type=text size=20 class="input-sm"><br>
        성별: <select class="input-sm">
                <option>남자</option>
                <option>여자</option>
            </select>
        </div>
    </div>
</body>
</html>