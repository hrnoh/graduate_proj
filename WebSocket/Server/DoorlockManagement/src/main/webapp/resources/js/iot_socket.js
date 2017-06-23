/**
 * WebSocket을 통한 서버 통신
 */
var sock = null;
var message = {}
var rmessage = {}
var host = "ws://172.30.1.17:8080/doorlock-ws";

$(document)
		.ready(
				function() {
					sock = new WebSocket(host);
					sock.onopen = function() {
						init(sock);
						$('#rLogWindow')
								.append(
										"<span><strong>서버에 연결되었습니다.</strong></span><br/>")
					}

					sock.onmessage = function(evt) {
						rmessage = {};
						rmessage = JSON.parse(evt.data);

						if (rmessage["type"] == 600) {
							addLog(sock, rmessage["data"]);
						} else if (rmessage["type"] == 500) {
							var list = null
							if (rmessage["data"] != null)
								list = JSON.parse(rmessage["data"]);
							doorlockList(list);
						}
					}

					sock.onclose = function() {
						$('#rLogWindow')
								.append(
										"<span><strong>연결이 해제되었습니다.</strong></span><br/>")
					}

					function init(sock) {
						message = {};
						message.type = 200;
						message.data = "null"
						sock.send(JSON.stringify(message));
					}

					function addLog(sock, message) {
						if (message.indexOf("WARNING") > -1
								|| message.indexOf("CRITICAL") > -1) {
							$('#rLogWindow')
									.append(
											"<span style=\"color:red;\"><strong>"
													+ message
													+ "</strong></span><br/>");
						} else {
							$('#rLogWindow').append(
									"<span>" + message + "</span><br/>");
						}
						$("#rLogWindow").scrollTop(
								$('#rLogWindow').prop('scrollHeight'));
					}

					function doorlockList(doorlockList) {

						if (doorlockList == null) {
							var text = "<tr class='col-xs-12'><td class='col-xs-12 text-center' colspan='4'>";
							text += "<span><strong>";
							text += "연결 된 도어락이 없습니다." + "</strong></span>";
							text += "</td></tr>";
							$("#rDoorlockList").html(text);
						} else {
							var text = "";
							for ( var i in doorlockList) {
								text += "<tr class='col-xs-12'>";
								text += "<td class='col-xs-4'>"
										+ doorlockList[i].mac + "</td>";
								text += "<td class='col-xs-4'>"
										+ doorlockList[i].location + "</td>";
								text += "<td class='col-xs-2 text-center'>";
								//text += "<a href='http://" + doorlockList[i].ip + ":8080/stream' target='_blank'>";
								text += "<a href='#'><span class='glyphicon glyphicon-search' ";
								text += "onclick=\"window.open('http://" + doorlockList[i].ip + ":8080/stream', 'streaming', 'height=520, width=700, left=300, top=100'); \">"
								text += "</span></a></td>";
								text += "<td class='col-xs-2 text-center'>";
								text += "<a href='#'>"
								text += "<span class='glyphicon glyphicon-lock' onclick='remoteOpen(\""
										+ doorlockList[i].mac
										+ "\");'></a></span></td>";
								text += "</tr>";
							}
							$("#rDoorlockList").html(text);
						}
					}
				})

function remoteOpen(mac) {
	message = {};
	message.type = 400;
	message.data = mac;
	sock.send(JSON.stringify(message));
}