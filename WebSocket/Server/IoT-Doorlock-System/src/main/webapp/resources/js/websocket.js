/**
 * DashBoard Socket
 */
var sock = null;
var message = {}
var rmessage = {}
var host = "ws://172.30.1.10:8080/doorlock/doorlock-ws";

$(document).ready(
		function() {
			sock = new WebSocket(host);
			sock.onopen = function() {
				init(sock);
				$('#logBox').append("<span><b>서버에 연결되었습니다.</b></span><br/>")
			}

			sock.onmessage = function(evt) {
				rmessage = {};
				rmessage = JSON.parse(evt.data);

				if (rmessage["type"] == "log") {
					addLog(sock, rmessage["data"]);
				}
				else if(rmessage["type"] == "log_search"){
					logSearchResult(JSON.parse(rmessage["data"]))
				}
				else if(rmessage["type"] == "log_search_error") {
					errMsg = "<span style=\"color:red;\"><b>" + rmessage["data"] + "</b></span>";
					$('#emp_log_box').html(errMsg);
				}
				else if(rmessage["type"] == "doorlockList") {
					doorlockList(JSON.parse(rmessage["data"]));
				}
			}

			sock.onclose = function() {
				$('#logBox').append("<span><b>연결이 해제되었습니다.</b></span><br/>")
			}

			function init(sock) {
				message = {};
				message.type = "uinit";
				message.data = "null"
				sock.send(JSON.stringify(message));
			}

			function addLog(sock, message) {
				if (message.indexOf("WARNING") > -1
						|| message.indexOf("CRITICAL") > -1) {
					$('#logBox').append(
							"<span style=\"color:red;\"><b>" + message
									+ "</b></span><br/>");
				} else {
					$('#logBox').append("<span>" + message + "</span><br/>");
				}
				$("#logBox").scrollTop($('#logBox').prop('scrollHeight'));
			}
		
			function logSearchResult(logArr) {
				var log = "";
				
				for(i=0; i<logArr.length; i++) {
					log += "<span>" + logArr[i].time + " : " + logArr[i].message + "</span><br/>";
				}
				
				$('#emp_log_box').html(log);
			}
			
			function doorlockList(doorlockList) {
	
				if(doorlockList == null) {
					var text = "<span style=\"color:red;line-height:middle;\"><b>" + "등록 된 도어락이 없습니다." + "</b></span>";
					$("#doorlockBox").html(text);
				}
				else {
					var text = "<table border='0'>"
						for(var i=0; i<doorlockList.length; i++) {
							text += "<tr align='center'>";
							text += "<td wdith='100px'>" + doorlockList[i].location + "</td>";
							text += "<td width='250px'>" + doorlockList[i].mac + "</td>";
							text += "<td width='50px'>" + doorlockList[i].level + "</td>";
							
							if(doorlockList[i].isOnline == 1) {
								text += "<td width='100px'>" + "<span style='color:green'><b>온라인</b></span>" + "</td>";
								text += "<td width='100px'>" + "<button onclick='remoteOpen(" + doorlockList[i].dno + ")' class='remoteOpen'>원격개방</button>" + "</td>";
							}
							else {
								text += "<td width='100px'>" + "<span style='color:red'><b>오프라인</b></span>" + "</td>";
								text += "<td wdith='100px'>" + "<button class='remoteOpen' disabled='true'>원격개방</button>" + "</td>";
							}
							text += "</tr>";
						}
					text += "</table>";
					$("#doorlockBox").html(text);
				}
			}
		})