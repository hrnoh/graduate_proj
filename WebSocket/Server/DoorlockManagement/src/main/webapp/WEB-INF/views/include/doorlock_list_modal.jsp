<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Modal -->
<div class="modal fade" id="dListModal" tabindex="-1" role="dialog"
	aria-labelledby="dListModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="dListModalLabel">도어락 조회</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<!-- 검색 창 -->
					<div class="row">
						<table class="table table-hover table-fixed">
							<thead class="col-xs-12">
								<tr class="col-xs-12">
									<th class="col-xs-4">Mac</th>
									<th class="col-xs-3">Location</th>
									<th class="col-xs-3">Level</th>
									<th class="col-xs-2">Logs</th>
								</tr>
							</thead>
							<tbody id="dList" class="col-xs-12">
								<tr class="col-xs-12">
									<td class="col-xs-12 text-center" colspan="4">
										<strong>도어락이 존재하지 않습니다.</strong>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>