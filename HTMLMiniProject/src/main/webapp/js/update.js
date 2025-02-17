$(function(){
	$('.update').click(function(){
		let rno = $(this).attr('data-rno');
		let targetRow = $('#m' + rno);

		if(targetRow.is(':visible')) {
			targetRow.hide();
			$(this).text("수정");
		} else {
			$('.ups').hide(); // 다른 열린 요소 닫기
			$('.update').text("수정"); // 다른 버튼 텍스트 초기화
			targetRow.show();
			$(this).text("취소");
		}
	});
});