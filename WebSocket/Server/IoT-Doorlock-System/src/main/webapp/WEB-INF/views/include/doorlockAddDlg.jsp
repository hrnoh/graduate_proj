<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
	#doorlockAddDlg-back {
		display: none;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, .3);
		z-index: 10;
	}
	
	#doorlockAddDlg {
		display: none;
		position: fixed;
		left: calc(50% - 200px);
		top: calc(50% - 200px);
		width: 400px;
		height: 300px;
		background: #fff;
		z-index: 11;
		padding: 10px;
	}
	
	
	
</style>
<script type="text/javascript">
$(function(){
	$("#addDoorlock").click(function () {
		$("#doorlockAddDlg-back,#doorlockAddDlg").show();
	});
	$("#doorlockAddDlg-back,#doorlockAddDlg-close").click(function() {
		$("#doorlockAddDlg-back,#doorlockAddDlg").hide();
	});
	$("#doorlock_register").click(function() {
		// 입력 값 검사
		if($("#location").val() == "") {
			alert("location 값을 입력하세요!");
			return;
		}
		else if($("#mac").val() == "") {
			alert("mac 값을 입력하세요!");
			return;
		}
		else if($("#level").val() == "") {
			alert("level 값을 입력하세요!")
			return;
		}
		
		doorlockVO = {};
		doorlockVO['location'] = $("#location").val();
		doorlockVO['mac'] = $("#mac").val();
		doorlockVO['level'] = $("#level").val();
		
		message = {};
		message['type'] = "doorlock_add";
		message['data'] = JSON.stringify(doorlockVO);
		
		sock.send(JSON.stringify(message));
		$("#doorlockAddDlg-close").click();
	});
});
</script>

<div id="doorlockAddDlg">
	<center>
		<h3>도어락 등록</h3><hr/>
		<b>위치 : </b><input id="location" type="text" size="25" placeholder="도어락이 설치 될 장소를 입력하세요."/><br/><br/>
		<b>Mac : </b><input id="mac" type="text" size="25" placeholder="도어락의 Mac Adress를 입력하세요."/><br/><br/>
		<b>레벨 : </b><input id="level" type="text" size="25" placeholder="도어락의 보안레벨을 입력하세요.(1~3)"/><br/><br/>
	</center>
	<div class="align_right">
		<button id="doorlock_register">등록</button>
		<button id="doorlockAddDlg-close">창 닫기</button>
	</div>
</div>
<div id="doorlockAddDlg-back"></div>