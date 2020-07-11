<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span>添加/修改公告</span>
            </div>
            <div class="panel-body">
                <#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post" action="update">
                    <#if view??>
                        <input type="hidden" name="id" value="${view.id}" />
                    </#if>

                    <div class="form-group">
                        <label class="col-lg-3 control-label">title：</label>
                        <div class="col-lg-4">
                            <input type="text" name="title" class="form-control" value="${view.title}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">image：</label>
                        <div class="col-lg-4">
                            <input type="text" name="image" class="form-control" value="${view.image}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">url：</label>
                        <div class="col-lg-4">
                            <input type="text" name="url" class="form-control" value="${view.url}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">type：</label>
                        <div class="col-lg-4">
                            <input type="text" name="type" class="form-control" value="${view.type}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">content：</label>
                        <div class="col-lg-4">
                            <input type="text" name="content" class="form-control" value="${view.content}">
                        </div>
                    </div>

                    <div class="ln_solid"></div>
                    <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <button type="submit" class="btn btn-success">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
var J = jQuery;

$(function() {
})
</script>
</@layout>