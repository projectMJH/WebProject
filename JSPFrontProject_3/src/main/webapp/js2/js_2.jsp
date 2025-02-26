<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
    margin-top: 50px;
}
.row{
    margin: 0px auto;
    width: 350px;
}
</style>
<script type="text/javascript">
let index=1
let prev=()=>{
	index--
	if(index<1)
		index=7
	let img=document.querySelector("img")
	let path="m"+index+".jpg"
	img.src=path   // <img src=
}
let next=()=>{
    index++
    if(index>7)
        index=1
    let img=document.querySelector("img")
    let path="m"+index+".jpg"
    img.src=path   // <img src=
}
let auto=()=>{
	setInterval(()=>{
		next()
	},1000)    // setInterval(함수(명령),단위시간(0.001초)) => 실시간 검색 시 사용.
}
window.onload=function(){
	
}
</script>
</head>
<body>
    <div class="container">
        <div class="row">
            <table class="table">
                <tr>
                <td class="text-center">
                    <img src="m1.jpg" style="width: 320px; height: 350px">
                </td>
                </tr>
                <tr>
                <td class="text-center">
                    <input type=button class="btn-sm btn-info" value="이전" onclick="prev()">
                    <input type=button class="btn-sm btn-danger" value="다음" onclick="next()">
                    <input type=button class="btn-sm btn-primary" value="자동" onclick="auto()">
                </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>