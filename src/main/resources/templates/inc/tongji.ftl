<@yaml>
	<#assign host = results.getProperty("server.host") />
</@yaml>

<script>
    // 百度站长自动推送，不用区分域名
	(function(){
	    var bp = document.createElement('script');
	    var curProtocol = window.location.protocol.split(':')[0];
	    if (curProtocol === 'https') {
	        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
	    }
	    else {
	        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
	    }
	    var s = document.getElementsByTagName("script")[0];
	    s.parentNode.insertBefore(bp, s);
	})();
</script>

<#if host == "blog.uzzz.org">
  	<script>
		// 百度统计
	    var _hmt = _hmt || [];
	    (function() {
	        var hm = document.createElement("script");
	        hm.src = "//hm.baidu.com/hm.js?48e99ee00f722fe26b6ccf7ebae23810";
	        var s = document.getElementsByTagName("script")[0];
	        s.parentNode.insertBefore(hm, s);
	    })();
	</script>
	
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-123893816-1"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', 'UA-123893816-1');
	</script>
<#elseif host == "blog.uzzz.org.cn">
	<script>
		// 百度统计
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "https://hm.baidu.com/hm.js?20e1def17e32eb03f392c0e7bd13566b";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
	</script>
	
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-135543318-1"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', 'UA-135543318-1');
	</script>
<#else>
	<script>
		var noTongji = true;
	</script>
</#if>

<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
  (adsbygoogle = window.adsbygoogle || []).push({
    google_ad_client: "ca-pub-8889449066804352",
    enable_page_level_ads: true
  });
</script>