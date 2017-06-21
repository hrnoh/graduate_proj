<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
	function eValidation() {
		if($("#name").val() == null) {
			alert("양식을 모두 채워주세요!");
			return false;
		}
		
		if($("#position").val() == null) {
			alert("양식을 모두 채워주세요!");
			return false;
		}
		
		if($("#age").val() == null) {
			alert("양식을 모두 채워주세요!");
			return false;
		}
		
		if($("#phone").val() == null) {
			alert("양식을 모두 채워주세요!");
			return false;
		}
		
		if($("#elevel").val() == null) {
			alert("양식을 모두 채워주세요!");
			return false;
		}
		
		$("#eAddForm").submit();
	}
</script>

<!-- Modal -->
<div class="modal fade" id="eAddModal" tabindex="-1" role="dialog"
	aria-labelledby="eAddModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="eAddModalLabel">사원 등록</h4>
			</div>
			<form class="form-horizontal" method="post" action="/web/eAdd" id="eAddForm">
				<div class="modal-body">
					<div class="container-fluid">
						<!-- 등록 양식 -->
						<div class="row">
							<div class="form-group">
								<label for="name" class="col-xs-2">Name</label> 
								<div class="col-xs-10">
									<input type="text" class="form-control" id="name" 
										name="name" placeholder="이름을 입력하세요">
								</div>
							</div>
							<div class="form-group">
								<label for="department" class="col-xs-2">Department</label> 
								<div class="col-xs-10">
									<select name="dno" id="department"></select>
								</div>
							</div>
							<div class="form-group">
								<label for="position" class="col-xs-2">Position</label> 
								<div class="col-xs-10">
									<input type="text" class="form-control" id="position" 
										name="position" placeholder="직급을 입력하세요">
								</div>
							</div>
							<div class="form-group">
								<label for="age" class="col-xs-2">Age</label> 
								<div class="col-xs-10">
									<input type="text" class="form-control" id="age" 
										name="age" placeholder="나이를 입력하세요">
								</div>
							</div>
							<div class="form-group">
								<label for="phone" class="col-xs-2">Phone</label> 
								<div class="col-xs-10">
									<input type="text" class="form-control" id="phone" 
										name="phoneNum" placeholder="전화번호를 입력하세요">
								</div>
							</div>
							<div class="form-group">
								<label for="level" class="col-xs-2">Level</label> 
								<div class="col-xs-10">
									<input type="text" class="form-control" id="elevel" 
										name="level" placeholder="Level을 입력하세요">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="eValidation();">등록</button>
				</div>
			</form>
		</div>
	</div>
</div>