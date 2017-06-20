<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="include/header.jsp"%>

<script>
	$(document).ready(function() {
		$("#dSearch").on("click", function (evt) {
			$.ajax({   
	            type : "GET",
	            url : "dList",
	            dataType : "json",
	            error : function(){
	                alert('통신실패!!');
	            },
	            success : function(msg){
	            	var datas = "";
	            	
	            	for(var obj in msg) {
	            		datas += "<tr class='col-xs-12'>";
	            		datas += "<td class='col-xs-4'>" + msg[obj]['mac'] + "</td>";
	            		datas += "<td class='col-xs-3'>" + msg[obj]['location'] + "</td>";
	            		datas += "<td class='col-xs-3'>" + msg[obj]['level'] + "</td>";
	            		datas += "<td class='col-xs-2'><span class='glyphicon glyphicon-info-sign clickable' ";
	            		datas += "onclick=\"window.open('http://localhost:8080/web/dLog?mac=" + msg[obj]['mac'] + "', '상세정보', 'height=500, width=800, left=300, top=100');\">";
	            		datas += "</span></td>";
	            	}
	              	
	            	$("#dList").html(datas);
	            }
	             
	        });
		});
		
		$("#eSearch").on("click", function (evt) {
			$.ajax({   
	            type : "GET",
	            url : "eList",
	            dataType : "json",
	            error : function(){
	                alert('통신실패!!');
	            },
	            success : function(msg){
	            	var datas = "";
	            	
	            	for(var obj in msg) {
	            		datas += "<tr class='col-xs-12'>";
	            		datas += "<td class='col-xs-4'>" + msg[obj]['name'] + "</td>";
	            		datas += "<td class='col-xs-3'>" + msg[obj]['position'] + "</td>";
	            		datas += "<td class='col-xs-3'>" + msg[obj]['level'] + "</td>";
	            		datas += "<td class='col-xs-2'><span class='glyphicon glyphicon-info-sign clickable' ";
	            		datas += "onclick=\"window.open('http://localhost:8080/web/eLog?eno=" + msg[obj]['eno'] + "', '상세정보', 'height=500, width=800, left=300, top=100');\">";
	            		datas += "</span></td>";
	            	}
	              	
	            	$("#eList").html(datas);
	            }
	             
	        });
		});
	});
</script>

<!-- Menu -->
<div class="row">
	<div class="col-xs-12">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Home</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<!-- 메뉴의 왼쪽 -->
					<ul class="nav navbar-nav">
						<!--<li>&nbsp&nbsp&nbsp&nbsp&nbsp</li>-->
						<!-- 사원 관리 메뉴 -->
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">사원관리
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a id="eAdd" href="" data-toggle="modal"
									data-target="#eAddModal">사원등록</a></li>
								<li><a id="eSearch" href="" data-toggle="modal"
									data-target="#eListModal">사원조회</a></li>
							</ul></li>

						<!-- 도어락 관리 메뉴 -->
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">도어락관리
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a id="dAdd" href="" data-toggle="modal"
									data-target="#dAddModal">도어락등록</a></li>
								<li><a id="dSearch" href="" data-toggle="modal"
									data-target="#dListModal">도어락조회</a></li>
							</ul></li>
					</ul>

					<!-- 메뉴의 오른쪽 -->
					<ul class="nav navbar-nav navbar-right">
						<form class="navbar-form navbar-left" role="search">
							<div class="form-group">
								<strong>필터 : </strong> <input type="text" class="form-control"
									placeholder="도어락 이름 입력">
							</div>
							<button type="submit" class="btn btn-default">검색</button>
						</form>
					</ul>
				</div>
			</div>
		</nav>
	</div>
</div>

<!-- Doorlock List -->
<div class="row">
	<div class="col-xs-12">
		<!-- Tab 자바스크립트 -->
		<script>
			$('#myTab a').click(function(e) {
				e.preventDefault()
				$(this).tab('show')
			})

			$('#myTab a[href="#doorlockList"]').tab('show') // Select tab by name
			$('#myTab a:last').tab('show') // Select last tab
		</script>
		<!-- Tab 메뉴 (도어락리스트, 로그) -->
		<div role="tabpanel">

			<!-- Nav tabs -->
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#doorlockList"
					aria-controls="doorlockList" role="tab" data-toggle="tab">도어락목록</a></li>
				<li role="presentation"><a href="#rLog" aria-controls="rLog"
					role="tab" data-toggle="tab">로그</a></li>
			</ul>

			<!-- Tab panes -->
			<div id="myTab" class="tab-content">
				<div role="tabpanel" class="tab-pane fade in active"
					id="doorlockList">
					<!-- Doorlock List Table -->
					<div class="row">
						<div class="col-xs-12">
							<table class="table table-hover table-fixed">
								<!-- 컬럼 설정 -->
								<thead class="col-xs-12">
									<tr class="col-xs-12">
										<th class="col-xs-4">Mac</th>
										<th class="col-xs-4">Location</th>
										<th class="col-xs-2 text-center">Streaming</th>
										<th class="col-xs-2 text-center">RemoteOpen</th>
									</tr>
								</thead>
								<tbody id="rDoorlockList" class="col-xs-12">
									<c:forEach var="i" begin="1" end="10" step="1">
										<tr class="col-xs-12">
											<td class="col-xs-4">AA:BB:CC:DD:CC:AA</td>
											<td class="col-xs-4">집</td>
											<td class="col-xs-2 text-center"><a href="#"><span
													class="glyphicon glyphicon-search"></span></a></td>
											<td class="col-xs-2 text-center"><a href="#"><span
													class="glyphicon glyphicon-lock"></span></a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div role="tabpanel" class="tab-pane fade" id="rLog">
					<!-- Log window -->
					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-default" style="margin:10px;">
								<div class="panel-body">
									<div id="rLogWindow" style="height:300px;overflow:scroll;">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>

<!-- include Modals -->
<%@include file="include/employee_list_modal.jsp"%>
<%@include file="include/doorlock_list_modal.jsp"%>
<%@include file="include/employee_add_modal.jsp" %>
<%@include file="include/doorlock_add_modal.jsp" %>

<%@include file="include/footer.jsp"%>
