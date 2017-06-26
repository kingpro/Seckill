<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/tag.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>秒杀详情页</title>
<%@ include file="common/head.jsp"%>
</head>
<body>
	<!-- 页面显示部分 -->
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading">
				<h1>${seckill.name}</h1>
			</div>
			<div class="panel-body">
				<h2 class="text-danger">
					<!-- 显示time图标 -->
					<span class="glyphicon glyphicon-time"></span>
					<!-- 展示倒计时 -->
					<span class="glyphicon" id="seckill-box"></span>
				</h2>
			</div>
		</div>
		<!-- 登录弹出层，输入电话 -->
		<div id="killPhoneModal" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title text-center">
							<span class="glyphicon glyphicon-phone"></span>
						</h3>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-xs-8 col-xs-offset-2">
								<input type="text" name="killPhone" id="killPhoneKey"
									placeholder="请填写手机号" class="form-control">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<!-- 验证信息 -->
						<span id="killPhoneMessage" class="glyphicon"></span>
						<button type="button" id="killPhoneBtn" class="btn btn-success">
							<span class="glyphicon glyphicon-phone"></span> Submit
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script
		src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
	<!-- jquery-cookie插件 -->
	<script
		src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
	<!-- jquery-countdown插件 -->
	<script
		src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.js"></script>
	<!-- 开始编写交互逻辑 -->
	<script src="<%=basePath%>/resources/js/seckill.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			//使用EL表达式传入参数
			seckill.detail.init({
				seckillId : ${seckill.id},
				startTime : ${seckill.startTime.time}, //毫秒时间
				endTime : ${seckill.endTime.time}
			});
		});
	</script>
</body>
</html>