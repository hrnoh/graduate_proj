<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8");%>
<%@include file="include/header.jsp" %>
<script>
function remoteOpen(dno) {
	message = {};
	message['type'] = 'remoteOpen';
	message['data'] = dno;
	sock.send(JSON.stringify(message));
}
</script>
		<nav id="sidemenu">
		<center>
			<h2 style="color:white;">Menu</h2>
			<hr />
			<input type="button" id="addEmployee" class="menuBtn" value="사원 등록" /> 
			<input type="button" id="addDoorlock" class="menuBtn" value="도어락 등록" /> 
			<input type="button" id="logSearch" class="menuBtn" value="출입 이력 조회" /> 
			<input type="button" class="menuBtn" value="원격 개방" />
		</center>
		</nav>
		<section id="content">
			<article id="log">
				<p style="color:white;"><b>&nbspLog</b></p>
				<div id="logBox"></div>
			</article>
			<article id="doorlockList">
				<p style="color:white;"><b>&nbspDoorlock List</b></p>
				<div id="doorlockBox"></div>
			</article>
		</section>
<%@include file="include/doorlockAddDlg.jsp" %>
<%@include file="include/logSearchDlg.jsp" %>
<%@include file="include/footer.jsp" %>