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
        <nav class="navbar" role="navigation">
            <div class="navbar-header">
                <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${base}/">
                    <img src="//${cookieFreeDomain}${base}/theme/default/images/logo.png"/>
                </a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
					<#if profile??>
						<li data="user">
							<a href="${base}/user" nav="user">我的主页</a>
						</li>
					</#if>
					<#list channels as row>
						<li class="${(channel && row.id == channel.id)?string("active","")}">
							<a href="${base}/channel/${row.id}" nav="${row.name}">${row.name}</a>
						</li>
					</#list>
                </ul>
                <ul class="navbar-button list-inline" id="header_user">
                    <li view="search" class="hidden-xs hidden-sm">
                        <form method="GET" action="${base}/search" accept-charset="UTF-8" class="navbar-form navbar-left">
                            <div class="form-group">
                                <input class="form-control search-input mac-style" placeholder="搜索" name="kw" type="text" value="${kw}">
                                <button class="search-btn" type="submit"><i class="fa fa-search"></i></button>
                            </div>
                        </form>
                    </li>

				<#if profile??>
                    <li>
                        <a href="${base}/post/editing" class="plus"><i class="icon icon-note"></i> 写文章</a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="user dropdown-toggle" data-toggle="dropdown">
                            <img class="img-circle" src="${base}${profile.avatar}?t=${.now}">
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
                    <li><a href="${base}/login" class="btn btn-default btn-sm signup">登录</a></li>
                    <li><a href="${base}/register" class="btn btn-primary btn-sm signup">注册</a></li>
				</#if>

                </ul>
            </div>
        </nav>
    </div>
</header>
<!-- Header END -->