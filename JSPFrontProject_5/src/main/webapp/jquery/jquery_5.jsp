<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jquery_5.jsp</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
    margin-top: 50px;
}
.row{
    margin: 0px auto;
    width: 500px;
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){   // $(document).ready(function(){})
	/*
	   $( )
	    --- selector(태그선택자)
	    --- 브라우저 객체
	        window, document, location
	        ------ 위치
	   $(this)
	     ---- 자신의 객체	        
	    
	*/
/* 	$('img').css('width','200px')
	$('img').css('height','250px')
 */	
    $('img').css({
    	'width':'250px',	
    	'height':'250px'	
    })
    
    // if-else 개념 (is hover, not hover)
    $('img').hover(function(){
    	$(this).css('cursor','pointer')
    	$(this).css('opacity','0.2')   // 0.0 ~ 1.0
    },function(){
    	$(this).css('opacity','1.0')
    })
})
</script>
</head>
<body>
    <img src="m1.jpg">
    <img src="m2.jpg">
</body>
</html>