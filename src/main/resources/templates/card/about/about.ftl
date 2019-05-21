<#include "/card/utils/ui.ftl"/>

<@layout "关于我们">

<div class="row main">
    <div class="col-xs-12 col-md-9 side-left topics-show">
        <!-- view show -->
        <div class="topic panel panel-default">
            <div class="infos panel-heading">
                <h1 class="panel-title topic-title">关于我们</h1>
                <div class="clearfix"></div>
            </div>
            <div class="content-body entry-content panel-body ">
                <div class="markdown-body" id="emojify">
                    <p><strong>关于柚子社区(uzshare.com)</strong><br/></p>
                    <p>区块链、技术、资讯</p>
				</div>
            </div>
        </div>
        <!-- /view show -->
    </div>
    <div class="col-xs-12 col-md-3 side-right hidden-xs hidden-sm">
		<#include "/card/inc/right.ftl"/>
    </div>
</div>

</@layout>