<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Modal -->
<div class="modal fade" id="dAddModal" tabindex="-1" role="dialog"
	aria-labelledby="dAddModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="dAddModalLabel">도어락 등록</h4>
			</div>
			<form class="form-horizontal" method="post" action="/web/dAdd">
				<div class="modal-body">
					<div class="container-fluid">
						<!-- 등록 양식 -->
						<div class="row">
							<div class="form-group">
								<label for="location" class="col-xs-2">Location</label> 
								<div class="col-xs-10">
									<input	type="text" class="form-control" id="location" placeholder="장소를 입력하세요">
								</div>
							</div>
							<div class="form-group">
								<label for="mac1" class="col-xs-2">Mac</label> 
								<div class="form-inline col-xs-10">
									<input type="text" class="form-control" id="mac1" size="2">:
									<input type="text" class="form-control" id="mac2" size="2">:
									<input type="text" class="form-control" id="mac3" size="2">:
									<input type="text" class="form-control" id="mac4" size="2">:
									<input type="text" class="form-control" id="mac5" size="2">:
									<input type="text" class="form-control" id="mac6" size="2">
								</div>
							</div>
							<div class="form-group">
								<label for="location" class="col-xs-2">Level</label> 
								<div class="col-xs-10">
									<input type="text" class="form-control" id="level" placeholder="Level을 입력하세요">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="submit();">등록</button>
				</div>
			</form>
		</div>
	</div>
</div>