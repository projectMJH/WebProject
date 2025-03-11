<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
let bClick=false
$(function(){
	$('#del').click(function(){
		if(bClick===false)
		{
			bClick=true
			$(this).text("취소")
			$('#delTr').show()
		}	
		else
		{
			bClick=false
            $(this).text("삭제")
            $('#delTr').hide()
		}	
	})
	$('#pwd').keyup(function(){
		let no=$('#delTr').attr("data-no")
		let pwd=$('#pwd').val()
		$.ajax({
			type:'post',
			url:'../board/board_pwd_ajax.do',
			data:{"no":no,"pwd":pwd},
			success:function(result)
			{
			    let res=parseInt(result)
			    if(res===0)
			    {
			    	$('#delBtn').hide()
			    	$('#print').text("비밀번호가 다릅니다")
			    }
			    else
			    {
                    $('#delBtn').show()
                    $('#print').text("")
			    }	
			}
		})
	})
	$('#delBtn').click(function(){
		let no=$('#delTr').attr("data-no")
		let page=$('#delTr').attr("data-page")
		$.ajax({
			type:'post',
			url:'../board/board_delete_ajax.do',
			data:{"no":no},
			success:function(result)
			{
				if(result==='yes')
				{
					location.href='../board/board_list.do?page='+page
				}
				else
				{
					alert("게시물 삭제 실패하였습니다\n다시 시도하세요")
				}	
			}
		})
	})
})

</script>
</head>
<body>
    <!-- ****** Breadcumb Area Start ****** -->
    <div class="breadcumb-area" style="background-image: url(../img/bg-img/breadcumb.jpg);">
        <div class="container h-100">
            <div class="row h-100 align-items-center">
                <div class="col-12">
                    <div class="bradcumb-title text-center">
                        <h2>내용 보기</h2>
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
                            <li class="breadcrumb-item active" aria-current="page">내용보기</li>
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
                        <th width=20% class="text-center">번호</th>
                        <td width=30% class="text-center">${vo.no }</td>
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
                        <td colspan="3">${vo.subject }</td>
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
                            <a href="#" class="btn btn-outline-info btn-xs">수정</a>
                            <span class="btn btn-outline-success btn-xs" id="del">삭제</span>
                            <a href="../board/board_list.do?page=${page }" class="btn btn-outline-warning btn-xs">목록</a>
                        </td>
                    </tr>
                    <tr id="delTr" style="display: none;" 
                        data-no="${vo.no }" data-page="${page }">
                        <td colspan="4" class="text-right">
                         비밀번호:<input type=password size=15 class="form-control-sm" id="pwd">
                                <input type=button value="삭제" class="btn btn-outline-sm" 
                                       id="delBtn" style="display: none">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4" class="text-right">
                            <span style="color:red;" id="print"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </section>        
</body>
</html>