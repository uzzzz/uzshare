<#include "/card/utils/ui.ftl"/>

<@server_name>
	<#assign host = result />
</@server_name>

<#assign site_metas>
	<link rel="canonical" href="https://${host}${base}/channel/${channel.id}">
	<script type="application/ld+json">
		{
			"@context":"http://schema.org",
			"@type":"WebPage",
			"name":"${site_name}",
			"url":"https://${host}${base}/channel/${channel.id}",
			"headline":"${channel.name} - ${site_name}"
		}
	</script>
</#assign>

<@layout channel.name>

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

    <@contents channelId=channel.id pn=pn order=order>
    <div class="row main">
        <div class="col-xs-12 col-md-9 side-left topics-show">
        	<div class="row">
	        	<#list results.content as row>
	                <div class="col-lg-4 col-md-6 col-sm-12 col-xs-12">
	                    <div class="block">
	                        <a class="block-thumbnail" href="${base}/view/${row.id}">
	                            <div class="thumbnail-overlay"></div>
	                            <span class="button-zoom">
	                                <img src="${base}/dist/images/image-overlay-view-icon.png">
	                            </span>
	
	                            <#if row.thumbnail??>
	                                <img src="${base + row.thumbnail}">
	                            <#else>
	                                <img src="${base}/dist/images/spinner-overlay.png">
	                            </#if>
	                            <div class="block-contents">
		                            <p class="tit">${row.title?html}
		                            </p>
		                        </div>
	                        </a>
	                    </div>
	                </div>
	            </#list>
            </div>
            
            <#if results.content?size == 0>
	            <div class="row">
	                <div class="infos text-center">
	                    <div class="media-heading">该目录下还没有内容!</div>
	                    <h5><a href="https://uzzz.org/" target="_blank">点击这里去看看更多资讯吧</a></h5>
	                </div>
	            </div>
	        </#if>
	   
	        <div style="width:100%; text-align:center;">
	            <!-- Pager -->
	            <@pager request.requestURI!"", results, 5/>
	        </div>
	        
        </div>
        
        <div class="col-xs-12 col-md-3 side-right hidden-xs hidden-sm" style="margin-left: 0px;">
    		<#include "/card/inc/right.ftl"/>
    	</div>
    	
 	</div>
    </@contents>

</@layout>

