<#include "/card/utils/ui.ftl"/>
<#assign topId = 1 />

<@server_name>
	<#assign host = result />
</@server_name>

<#assign site_metas>
	<link rel="canonical" href="https://${host}">
	<script type="application/ld+json">
		{
			"@context":"http://schema.org",
			"@type":"WebSite",
			"name":"${site_name}",
			"url":"https://${host}/",
			"headline":"资讯、技术、生活、思想 - ${site_name}"
		}
	</script>
</#assign>

<@layout>

    <@contents pn=pn order=order>
    <div class="row main">

		<div class="col-xs-3 col-md-3" style="margin-left: 0px;">
			<#include "/card/inc/left.ftl"/>
		</div>

    	<div class="col-xs-9 col-md-6">

			<ol class="breadcrumb ">
				<li title="发布时间排序" <#if order == 'newest'> class="active" </#if>>
					<a href="?order=newest">最近</a>
				</li>
				<li title="点赞数排序" <#if order == 'favors'> class="active" </#if>>
					<a href="?order=favors">投票</a>
				</li>
				<li title="评论次数排序" <#if order == 'hottest'> class="active" </#if>>
					<a href="?order=hottest">热门</a>
				</li>
			</ol>

	    	<div class="row">
	    		<#list results.content as row>
		            <div class="col-md-12">
		                <div class="block">
		                
		                    <#if row.thumbnail?? && row.thumbnail != "">
		                        <a class="block-thumbnail"
		                        	style="margin-right:-8px;position: absolute;"
		                        	href="/view/${row.id}" target="_blank">
		                            <img src="${row.thumbnail}" />
		                        </a>
		                    </#if>
		                    
		                    <div class="block-contents"style="margin-left: ${(row.thumbnail?? && row.thumbnail != "")?string('146px', '0px')};height: 96px;">
								<a class="block-thumbnail" href="/view/${row.id}" target="_blank">
									<p class="tit"
									   style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;overflow: hidden;">${row.title}</p>
								</a>
								<a class="block-thumbnail" href="/view/${row.id}" target="_blank">
									<p style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;font-size: 13px;">${row.summary}</p>
								</a>
			                    <div style="position: absolute; bottom: 4px;">
			                    	<a href="/users/${row.author.id}">
			                    		<img src="${(row.author.avatar?index_of("/")==0)?string( '//' + cookieFreeDomain + row.author.avatar, row.author.avatar)}" class="avatar avatar-xs" />
			                    		<span>${row.author.name}</span>
			                    	</a>
			                    	<abbr class="meta timeago" style="margin-left:4px;">${timeAgo(row.created)}</abbr>
			                    </div>
			                </div>
		                   
		                </div>
		            </div>
		        </#list>
	        </div>
	        
	        <div style="width:100%; text-align:center;">
		        <!-- Pager -->
		        <@pager request.requestURI + "?order=" + order, results, 5/>
		    </div>
					    
		    <!-- match content ads -->
	        <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
			<ins class="adsbygoogle"
			     style="display:block"
			     data-ad-format="autorelaxed"
			     data-ad-client="ca-pub-8889449066804352"
			     data-ad-slot="1928667997"></ins>
			<script>
			     (adsbygoogle = window.adsbygoogle || []).push({});
			</script>

    	</div>
    	
    	<div class="col-xs-0 col-md-3 hidden-xs hidden-sm" style="margin-left: 0px;">
    		<#include "/card/inc/right.ftl"/>
    	</div>
    </div>

    </@contents>

</@layout>