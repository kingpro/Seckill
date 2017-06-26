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
<title>秒杀列表页</title>
<%@ include file="common/head.jsp"%>
</head>
<body>
	<!-- 页面显示部分 -->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>秒杀列表</h2>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>详情页</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sk" items="${seckillList}">
							<tr>
								<th>${sk.name }</th>
								<th>${sk.number }</th>
								<th><fmt:formatDate value="${sk.startTime }"
										pattern="yyyy-MM-dd HH:mm:ss" /></th>
								<th><fmt:formatDate value="${sk.endTime }"
										pattern="yyyy-MM-dd HH:mm:ss" /></th>
								<th><fmt:formatDate value="${sk.createTime }"
										pattern="yyyy-MM-dd HH:mm:ss" /></th>
								<th><a class="btn btn-info"
									href="<%=basePath %>seckill/${sk.id}/detail" target="_blank">进入</a></th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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
</body>
</html>