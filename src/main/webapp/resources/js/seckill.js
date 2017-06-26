//存放主要交互逻辑js代码
//JavaScript模块化
var seckill = {
	// 封装秒杀相关ajax的url
	URL : {
		now : function() {
			return '/Seckill/seckill/time/now';
		},
		exposer : function(seckillId) {
			return '/Seckill/seckill/' + seckillId + '/exposer';
		},
		execution : function(seckillId, md5) {
			return '/Seckill/seckill/' + seckillId + '/' + md5 + '/execute';
		}
	},
	validatePhone : function(phone) {
		if (phone && phone.length == 11 && !isNaN(phone)) {
			return true;
		} else {
			return false;
		}
	},
	// 处理秒杀逻辑
	handlerSeckill : function(seckillId, node) {
		node
				.hide()
				.html(
						'<button class="btn btn-primary btn-lg" id = "killButton">开始秒杀</botton>');
		$
				.post(
						seckill.URL.exposer(seckillId),
						{},
						function(result) {
							if (result && result['success']) {
								var exposer = result['data'];
								if (exposer['exposed']) {
									// 开启秒杀
									// 获取秒杀地址
									var md5 = exposer['md5'];
									var killUrl = seckill.URL.execution(
											seckillId, md5);
									console.log('KillUrl:' + killUrl); // 绑定一次点击事件
									$('#killButton')
											.one(
													'click',
													function() {
														// 1.禁用按钮
														$(this).addClass(
																'disabled');
														// 2.发送秒杀请求执行秒杀
														$
																.post(
																		killUrl,
																		{},
																		function(
																				result) {
																			if (result
																					&& result['success']) {
																				var killResult = result['data'];
																				var state = killResult['state'];
																				var stateInfo = killResult['stateInfo']; // 显示秒杀结果
																				node
																						.html('<span class="label label-success">'
																								+ stateInfo
																								+ '	</span>');
																			}
																		});
													});
									node.show();
								} else {
									// 未开启秒杀
									var now = exposer['now'];
									var start = exposer['start'];
									var end = exposer['end'];
									seckill.countdown(seckillId, now, start,
											end);
								}
							} else {
								console.log('result:' + result);
							}
						});
	},
	countdown : function(seckillId, nowTime, startTime, endTime) {
		// 时间的判断
		var seckillBox = $('#seckill-box');
		if (nowTime > endTime) {
			// 秒杀结束
			seckillBox.html('秒杀结束！');
		} else if (nowTime < startTime) {
			// 秒杀未开始，计时
			var killTime = new Date(startTime + 1000);// 防止出现时间的偏移
			seckillBox.countdown(killTime, function(event) {
				// 时间格式
				var format = event.strftime("秒杀倒计时： %D天 %H时 %M分 %S秒");
				seckillBox.html(format);
				// 时间完成后回调事件
			}).on('finish.countdown', function() {
				seckill.handlerSeckill(seckillId, seckillBox);
			});
		} else {
			// 秒杀开始
			seckill.handlerSeckill(seckillId, seckillBox);
		}
	},
	// 详情页秒杀逻辑
	detail : {
		// 详情页初始化
		init : function(params) {
			// 手机号码验证和登录，计时交互
			// 规划我们的交互流程
			// 在cookie中查找手机号
			var userPhone = $.cookie('userPhone');
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			// 验证手机号
			if (!seckill.validatePhone(userPhone)) {
				// 绑定phone
				// 控制输出
				var killPhoneModal = $('#killPhoneModal');
				killPhoneModal.modal({
					show : true,// 显示弹出层
					backdrop : 'static',// 禁止移动
					keyboard : false
				// 关闭键盘事件
				});
				$('#killPhoneBtn')
						.click(
								function() {
									var inputPhone = $('#killPhoneKey').val();
									if (seckill.validatePhone(inputPhone)) {
										// 手机号码写入cookie
										$.cookie('userPhone', inputPhone, {
											expires : 7,
											path : '/Seckill/seckill/'
										});
										// 刷新页面
										window.location.reload();
									} else {
										$('#killPhoneMessage')
												.hide()
												.html(
														'<label class="label label-danger">手机号码错误</label>')
												.show(300);
									}
								});
			}
			// 已经登录
			// 计时交互
			$.get(seckill.URL.now(), {}, function(result) {
				if (result && result['success']) {
					var nowTime = result['data'];
					// 时间判断，计时交互
					seckill.countdown(seckillId, nowTime, startTime, endTime);
				} else {
					console.log('result:' + result);
				}
			});
		}
	}
}