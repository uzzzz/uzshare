<#include "/card/utils/ui.ftl"/>

<@layout "消息提示">

	<div class="panel panel-default">
		<div class="panel-heading">提示<#if error??>${":" + error}</#if></div>
		<div class="panel-body" style="padding-top: 0px;">
			<h2 class="description text-center">抱歉, 您访问的页面出了问题!</h2>
			<h3 class="text-center">您可以从这里返回<a href="/" style="color: #337ab7;">首页</a></h3>
		</div><!-- /panel-content -->
	</div><!-- /panel -->

	<@contents size=12>
    <div class="row main">
    	<div class="col-xs-12 col-md-9 side-left topics-show">
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
			                        <p class="tit">${row.title}</p>
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
    	</div>
    	
    	<div class="col-xs-12 col-md-3 side-right hidden-xs hidden-sm" style="margin-left: 0px;">
    		<#include "/card/inc/right.ftl"/>
    	</div>
    </div>
    </@contents>
</@layout>