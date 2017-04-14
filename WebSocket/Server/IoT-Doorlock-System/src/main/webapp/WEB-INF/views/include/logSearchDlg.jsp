<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
	#logSearchDlg-back {
		display: none;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, .3);
		z-index: 10;
	}
	
	#logSearchDlg {
		display: none;
		position: fixed;
		left: calc(50% - 400px);
		top: calc(50% - 250px);
		width: 800px;
		height: 400px;
		background: #fff;
		z-index: 11;
		padding: 10px;
	}
	
	#emp_log_box {
		overFlow: scroll;
		height: 300px;
		max-height: 300px;
		border-radius: 10px;
		padding: 5px;
		margin: 5px;
		border: 1px solid black;
	}
</style>
<script type="text/javascript">
$(function(){
	$("#logSearch").click(function () {
		$("#logSearchDlg-back,#logSearchDlg").show();
	});
	$("#logSearchDlg-back,#logSearchDlg-close").click(function() {
		$("#emp_log_box").text("");
		$("#emp_name").val("");
		$("#logSearchDlg-back,#logSearchDlg").hide();
	});
	$("#send_emp_name").click(function() {
		message = {};
		message['type'] = "log_search";
		message['data'] = $("#emp_name").val();
		sock.send(JSON.stringify(message));
	});
	$("#emp_name").keypress(function(key) {
		if(key.which == 13) {
			$("#send_emp_name").click();
		}
	});
});
</script>

<div id="logSearchDlg">
	<center>
		<b>이름 : </b>
		<input id="emp_name" type="text" placeholder="사원 이름을 입력하세요."/>
		<button id="send_emp_name">검색</button>
	</center>
	<div id="emp_log_box"></div>
	<div class="align_right">
		<button id="logSearchDlg-close">창 닫기</button>
	</div>
</div>
<div id="logSearchDlg-back"></div>