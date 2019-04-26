<#include "/default/utils/ui.ftl"/>

<@layout "消息提示">

<div class="panel panel-default" style="min-height: 300px; max-width: 460px; margin: 30px auto;">
	<div class="panel-heading">提示</div>
	<div class="panel-body">
		<fieldset>
			<#if error??>
				${error}
			</#if>
		</fieldset>
		<h2 class="description text-center">抱歉, 您访问的页面出了问题!</h2>
		<h3 class="text-center">您可以从这里返回<a href="/" style="color: #337ab7;">首页</a></h3>
	</div><!-- /panel-content -->
</div><!-- /panel -->
</@layout>