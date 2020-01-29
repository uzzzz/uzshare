<#-- Layout -->
<#macro layout title keywords description>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, minimum-scale=1, initial-scale=1">
    <!--[if IE]>
    <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'/>
    <![endif]-->

    <title>${title?default(site_name)}</title>
    <meta name="keywords" content="${keywords?default(site_keywords)}">
    <meta name="description" content="${description?default(site_description)}">

    <#include "/card/inc/include.ftl"/>
</head>
<body>

<!-- header -->
    <#include "/card/inc/header_without_ads.ftl"/>
<!-- /header -->

<!-- content -->
<div class="wrap">
    <!-- Main -->
    <div class="container">
        <#nested>
    </div>
</div>
<!-- /content -->

<!-- footer -->
    <#include "/card/inc/footer_without_ads.ftl"/>

</body>
</html>
</#macro>


<#-- 用户头像显示 -->
<#macro showAva avatar clazz>
    <#if avatar?starts_with("http:")>
    <img class="${clazz}" src="${avatar}"/>
    <#else>
    <img class="${clazz}" src="<@resource src=avatar />"/>
    </#if>
</#macro>

<#macro classify row>
    <#if row.channel??>
    <span class="hidden-xs label label-default <#if (row.featured > 0)>channel_featured</#if> <#if (row.weight > 0)>channel_top</#if>">${row.channel.name}</span>
    </#if>
</#macro>

<#macro pager url p spans>
    <#if p??>
        <#local span = (spans - 3)/2 />
        <#if (url?index_of("?") != -1)>
            <#local cURL = (url + "&pn=") />
        <#else>
            <#local cURL = (url + "?pn=") />
        </#if>

    <ul class="pagination">
        <#assign pageNo = p.number + 1/>
        <#assign pageCount = p.totalPages />
        <#if (pageNo > 1)>
            <li><a href="${cURL}${pageNo - 1}" pageNo="${pageNo - 1}" class="prev">上页</a></li>
        <#else>
            <li class="disabled"><span>上页</span></li>
        </#if>

        <#local totalNo = span * 2 + 3 />
        <#local totalNo1 = totalNo - 1 />
        <#if (pageCount > totalNo)>
            <#if (pageNo <= span + 2)>
                <#list 1..totalNo1 as i>
                    <@pagelink pageNo, i, cURL/>
                </#list>
                <@pagelink 0, 0, "#"/>
                <@pagelink pageNo, pageCount, cURL />
            <#elseif (pageNo > (pageCount - (span + 2)))>
                <@pagelink pageNo, 1, cURL />
                <@pagelink 0, 0, "#"/>
                <#local num = pageCount - totalNo + 2 />
                <#list num..pageCount as i>
                    <@pagelink pageNo, i, cURL/>
                </#list>
            <#else>
                <@pagelink pageNo, 1, cURL />
                <@pagelink 0 0 "#" />
                <#local num = pageNo - span />
                <#local num2 = pageNo + span />
                <#list num..num2 as i>
                    <@pagelink pageNo, i, cURL />
                </#list>
                <@pagelink 0, 0, "#"/>
                <@pagelink pageNo, pageCount, cURL />
            </#if>
        <#elseif (pageCount > 1)>
            <#list 1..pageCount as i>
                <@pagelink pageNo, i, cURL />
            </#list>
        <#else>
            <@pagelink 1, 1, cURL/>
        </#if>

        <#if (pageNo < pageCount)>
            <li><a href="${cURL}${pageNo + 1}" pageNo="${pageNo + 1}" class="next">下页</a></li>
        <#else>
            <li class="disabled"><span>下页</span></li>
        </#if>
    </ul>
    </#if>
</#macro>

<#macro pagelink pageNo idx url>
    <#if (idx == 0)>
    <li><span>...</span></li>
    <#elseif (pageNo == idx)>
    <li class="active"><span>${idx}</span></li>
    <#else>
    <li><a href="${url}${idx}">${idx}</a></li>
    </#if>
</#macro>