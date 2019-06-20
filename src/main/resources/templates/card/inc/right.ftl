<div class="panel panel-default corner-radius panel-hot-topics">
	<div class="panel-heading">
		<h3 class="panel-title"><i class="fa fa-area-chart"></i> 热门文章</h3>
	</div>
	<div class="panel-body">
		<ul class="list" id="hots">
            <img src="//${cookieFreeDomain}${base}/dist/images/spinner.gif">
		</ul>
	</div>
</div>

<div class="panel panel-default corner-radius panel-hot-topics">
	<div class="panel-heading">
		<h3 class="panel-title"><i class="fa fa-bars"></i> 最新发布</h3>
	</div>
	<div class="panel-body">
		<ul class="list" id="latests">
			<img src="//${cookieFreeDomain}${base}/dist/images/spinner.gif">
		</ul>
	</div>
</div>

<div class="panel panel-default corner-radius panel-hot-topics">
    <div class="panel-heading">
        <h3 class="panel-title"><i class="fa fa-users"></i> 热门用户</h3>
    </div>
    <div class="panel-body">
        <ul class="hotusers" id="hotuser">
            <img src="//${cookieFreeDomain}${base}/dist/images/spinner.gif">
        </ul>
    </div>
</div>

<div class="panel panel-default corner-radius panel-hot-topics">
	<div class="panel-heading">
        <h3 class="panel-title"><i class="fa fa-coffee"></i></h3>
    </div>
    <div class="remove-padding-horizontal">
        <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		<!-- uzshare_right -->
		<ins class="adsbygoogle"
		     style="display:block"
		     data-ad-client="ca-pub-8889449066804352"
		     data-ad-slot="2081363239"
		     data-ad-format="auto"
		     data-full-width-responsive="true"></ins>
		<script>
		(adsbygoogle = window.adsbygoogle || []).push({});
		</script>
    </div>
</div>

<script type="text/javascript">

var li_template = '<li>{0}. <a href="${base}/view/{1}">{2}</a></li>';

var hotUser_li_template = '<li><a href="{1}"><img src="{0}" class="avatar avatar-small"/></a></li>'

seajs.use('sidebox', function (sidebox) {
	sidebox.init({
        latestUrl : '${base}/api/latests',
    	hotUrl : '${base}/api/hots',
		hotTagUrl : '${base}/api/hot_tags',
		hotUserUrl:'${base}/api/hotusers',

        size :10,
        // callback
        onLoadHot : function (i, data) {
      		return jQuery.format(li_template, i + 1, data.id, data.title);
        },
        onLoadLatest : function (i, data) {
      		return jQuery.format(li_template, i + 1, data.id, data.title);
        },
        onLoadHotUser : function (i, data) {
        	var url = '${base}/users/' + data.id;
        	var avatar = (data.avatar.indexOf('/') == 0) ? '//${cookieFreeDomain}' + data.avatar : data.avatar;
      		var item = jQuery.format(hotUser_li_template,avatar,url,data.name, data.fans);
      		return item;
        }
	});
});
</script>