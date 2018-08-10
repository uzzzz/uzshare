<footer class="footer">
    <div class="container">
        <div class="footer-col footer-col-logo hidden-xs hidden-sm">
            <img src="${base}/theme/default/images/logo.png"/>
        </div>
        <div class="footer-col footer-col-copy">
            <ul class="footer-nav hidden-xs">
                <li class="menu-item"><a href="${base}/about">关于我们</a></li>
                <li class="menu-item"><a href="${base}/joinus">联系我们</a></li>
                <li class="menu-item"><a href="${base}/faqs">常见问题</a></li>
                <li>
                    <script>
                        var _hmt = _hmt || [];
                        (function() {
                            var hm = document.createElement("script");
                            hm.src = "//hm.baidu.com/hm.js?1312b79c333e49ab4682fff950809062";
                            var s = document.getElementsByTagName("script")[0];
                            s.parentNode.insertBefore(hm, s);
                        })();
                    </script>
                    <script>
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
                </li>
            </ul>
            <div class="copyright">
				<span>${site_copyright}. ${site_icp}</span>
			</div>
        </div>
    </div>
</footer>

<a href="#" class="site-scroll-top"></a>

<script type="text/javascript">
    seajs.use('main', function (main) {
        main.init();
    });
</script>