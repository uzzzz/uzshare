<#include "/card/utils/ui.ftl"/>

<#assign site_metas>
	<link rel="canonical" href="https://blog.uzzz.org${base}/channel/${channel.id}">
	<script type="application/ld+json">
		{
			"@context":"http://schema.org",
			"@type":"WebPage",
			"name":"区块链大本营",
			"url":"https://blog.uzzz.org${base}/channel/${channel.id}",
			"headline":"${channel.name} - 区块链大本营"
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
            <#list results.content as row>
                <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
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

            <#if results.content?size == 0>
                <div class="col-md-12 col-sm-12">
                    <div class="infos text-center">
                        <div class="media-heading">该目录下还没有内容!</div>
                        <h5><a href="https://uzzz.org/" target="_blank">点击这里去看看更多资讯吧</a></h5>
                    </div>
                </div>
            </#if>
        </div>
        <div class="row" style="width:100%; text-align:center;">
            <!-- Pager -->
            <@pager request.requestURI!"", results, 5/>
        </div>

    </@contents>

</@layout>

