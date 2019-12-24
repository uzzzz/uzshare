<!-- Login dialog BEGIN -->
<div id="login_alert" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">请登录</h4>
            </div>
            <div class="modal-body">
                <form method="POST" action="/login" accept-charset="UTF-8">
                    <div class="form-group ">
                        <label class="control-label" for="username">账号</label>
                        <input class="form-control" id="ajax_login_username" name="username" type="text" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="password">密码</label>
                        <input class="form-control" id="ajax_login_password" name="password" type="password" required>
                    </div>
                    <div class="form-group ">
                        <label>
                            <input type="checkbox" name="rememberMe" value="1"> 记住登录
                        </label>
                        <a class="forget-password" href="/forgot/apply" style="float: right;">忘记密码？</a>
                    </div>
                    <button id="ajax_login_submit" class="btn btn-success btn-block" type="button">
                        登录
                    </button>
                    <div id="ajax_login_message" class="text-danger" style="text-align: center;margin-top: 16px;"></div>
                    <hr>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- Login dialog END -->

<style>

.top_channel_wrap {
	flex-grow: 1;
	padding: 0px;
}

@media only screen and (max-width: 767px) {
	.top_channel_wrap {
	    position: absolute;
	    top: 50px;
	    background: rgb(255, 255, 255);
	    margin: 0px -16px;
	    border-bottom: solid 1px #cccccc;
	}
	
	.top_channel {
		height: auto !important;
		flex-wrap: wrap !important;
		padding: 0 15px;
		margin: 0px !important;
		justify-content: flex-start !important;
	}
	
	.navbar-nav .open .dropdown-menu {
		border: 1px solid rgba(0,0,0,.15);
		min-width: auto;
		margin-bottom: 8px;
	}
}
</style>

<!-- Fixed navbar -->
<header class="site-header headroom">
    <!--[if lt IE 9]>
	<div class="alert alert-danger alert-dismissible fade in" role="alert" style="margin-bottom:0">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		<strong>您正在使用低版本浏览器，</strong> 在本页面的显示效果可能有差异。
		建议您升级到
		<a href="http://www.google.cn/intl/zh-CN/chrome/" target="_blank">Chrome</a>
		或以下浏览器：
		<a href="www.mozilla.org/en-US/firefox/‎" target="_blank">Firefox</a> /
		<a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> /
		<a href="http://www.opera.com/" target="_blank">Opera</a> /
		<a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie" target="_blank">Internet Explorer 9+</a>
	</div>
    <![endif]-->

    <div class="container">
        <nav class="navbar" role="navigation" style="display: flex;justify-content: space-between;height: 50px;">
            <a class="navbar-brand" href="/">
                <img src="//${cookieFreeDomain}/theme/default/images/logo.png"/>
            </a>
            <div class="collapse navbar-collapse top_channel_wrap">
                <ul class="nav navbar-nav top_channel">
					<#if profile??>
						<li data="user">
							<a href="/user" nav="user">我的主页</a>
						</li>
					</#if>
					<#list channels as row>
						<li class="${(channel && row.id == channel.id)?string("active","")}">
							<a href="/channel/${row.id}" nav="${row.name}">${row.name}</a>
						</li>
					</#list>
					<li>
                        <form method="GET" action="/search" accept-charset="UTF-8" class="navbar-form navbar-left" target="_blank">
                            <div class="form-group">
                                <input class="form-control search-input mac-style" placeholder="搜索" name="kw" type="search" value="${kw}">
                                <button class="search-btn" type="submit"><i class="fa fa-search"></i></button>
                            </div>
                        </form>
                    </li>
                    <li>
                    	<ul class="navbar-button list-inline" id="header_user" style="white-space: nowrap;">
						<#if profile??>
		                    <li>
		                        <a href="${base}/post/editing" class="plus"><i class="icon icon-note"></i> 写文章</a>
		                    </li>
		                    <li class="dropdown">
		                        <a href="#" class="user dropdown-toggle" data-toggle="dropdown" style="background-color: #0000;">
		                            <img class="img-circle" src="${profile.avatar}">
		                            <span>${profile.name}</span>
		                        </a>
		                        <ul class="dropdown-menu" role="menu">
		                            <li>
		                                <a href="${base}/user">我的主页</a>
		                            </li>
		                            <li>
		                                <a href="${base}/user/profile">编辑资料</a>
		                            </li>
		                            <@shiro.hasPermission name="admin">
		                                <li><a href="${base}/admin">后台管理</a></li>
		                            </@shiro.hasPermission>
		                            <li><a href="${base}/logout">退出</a></li>
		                        </ul>
		                    </li>
						<#else>
		                    <li><a href="${base}/login" class="btn btn-default btn-sm">登录</a></li>
		                    <li><a href="${base}/register" class="btn btn-primary btn-sm">注册</a></li>
						</#if>
		                </ul>
                    </li>
                </ul>
            </div>
            
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".top_channel_wrap">
                <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
            </button>
        </nav>
    </div>
</header>
<!-- Header END -->