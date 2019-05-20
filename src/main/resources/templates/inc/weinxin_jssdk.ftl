<#assign isWxApp = userAgent() ?lower_case ?contains("w") />

<#if isWxApp>
	<script src="//res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
</#if>

<#if isWxApp>
	<@wx_signature>
	<script type="text/javascript">
		wx.config({
		    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: '${result.appId}', // 必填，公众号的唯一标识
		    timestamp: ${result.timestamp}, // 必填，生成签名的时间戳
		    nonceStr: '${result.noncestr}', // 必填，生成签名的随机串
		    signature: '${result.signature}',// 必填，签名
		    jsApiList: ['updateAppMessageShareData', 'updateTimelineShareData'] // 必填，需要使用的JS接口列表
		});
		wx.ready(function () {   //需在用户可能点击分享按钮前就先调用
		    wx.updateAppMessageShareData({ 
		        title: '${view.title}', // 分享标题
		        desc: '${view.summary}', // 分享描述
		        link: 'https://${host}/view/${view.id}', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		        imgUrl: '${view.thumbnail}', // 分享图标
		        success: function () {
		          // 设置成功
		        }
		    })
		    wx.updateTimelineShareData({ 
		        title: '${view.title}', // 分享标题
		        link: 'https://${host}/view/${view.id}', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		        imgUrl: '${view.thumbnail}', // 分享图标
		        success: function () {
		          // 设置成功
		        }
		    })
		});
	</script>
	</@wx_signature>
</#if>