<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
window.onload=function(){
	display()
}
function display(){
    alert("function display Call...")	
}
function display(){
	alert("let display=() call...")
}
function display(){
	alert("let display=function() call...")
}

</script>
</head>
<body>

</body>
</html>