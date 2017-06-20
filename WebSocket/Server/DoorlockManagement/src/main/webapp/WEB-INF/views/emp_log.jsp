<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>사원 정보</title>
<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


<!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
<!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet" href="/resources/css/style.css" />
</head>
<body>
	<div class="container">
		<!-- 제목 -->
		<div class="row">
			<div class="col-xs-12">
				<div class="page-header">
					<h2>상세 정보</h2>
				</div>
			</div>
		</div>

		<!-- 도어락 정보 -->
		<div class="row">
			<div class="col-xs-12">
				<form class="form-horizontal">
					<div class="form-group">
						<label class="col-xs-2 control-label">이름</label>
						<div class="col-xs-10">
							<p class="form-control-static">${employeeVO.name }</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-2 control-label">부서</label>
						<div class="col-xs-10">
							<p class="form-control-static">${employeeVO.deptName }</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-2 control-label">직급</label>
						<div class="col-xs-10">
							<p class="form-control-static">${employeeVO.position }</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-2 control-label">전화번호</label>
						<div class="col-xs-10">
							<p class="form-control-static">${employeeVO.phoneNum }</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-2 control-label">level</label>
						<div class="col-xs-10">
							<p class="form-control-static">${employeeVO.level }</p>
						</div>
					</div>
				</form>
			</div>
		</div>

		<hr>

		<!-- 로그 -->
		<div class="row">
			<div class="col-xs-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<strong>출입 이력</strong>
					</div>
					<div class="panel-body">
						<table class="table table-fixed">
							<thead class="col-xs-12">
								<tr class="col-xs-12">
									<th class="col-xs-3">장소</th>
									<th class="col-xs-6 text-center">시간</th>
									<th class="col-xs-3 text-center">결과</th>
								</tr>
							</thead>
							<tbody class="col-xs-12">
								<c:forEach var="log" items="${list }">
									<tr class="col-xs-12">
										<td class="col-xs-3">${log.location }</td>
										<td class="col-xs-6 text-center">${log.time }</td>
										<td class="col-xs-3 text-center">${log.result }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>