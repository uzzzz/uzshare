<#include "/admin/utils/ui.ftl"/>
<@layout>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <span>公告管理</span>
                <ul class="nav navbar-right panel-toolbox">
                    <li>
                        <a href="${base}/admin/notice/view">添加公告</a>
                    </li>
                </ul>
            </div>
            <div class="panel-body">
                <form id="qForm" class="form-inline">
                    <input type="hidden" name="pn" value="${page.pageNo}" />
                </form>
                <div class="table-responsive">
                    <table id="dataGrid" class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th width="64">#</th>
                            <th width="256">title</th>
                            <th>image</th>
                            <th>url</th>
                            <th>type</th>
                            <th>content</th>
                            <th width="200"></th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list list as row>
                            <tr>
                                <td class="text-center">${row.id}</td>
                                <td>${row.title}</td>
                                <td><a href="${row.image}" target="_blank"><img src="${row.image}" width="48" height="48" /></a></td>
                                <td><a href="${row.url}" target="_blank">跳转链接</a></td>
                                <td>${row.type}</td>
                                <td>${row.content}</td>
                                <td class="text-center">
                                    <a href="view?id=${row.id}" class="btn btn-xs btn-success">
                                        <i class="fa fa-edit"></i> 修改
                                    </a>
                                    <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}"
                                       data-action="delete">
                                        <i class="fa fa-bitbucket"></i> 删除
                                    </a>
                                </td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var J = jQuery;

    function ajaxReload(json) {
        if (json.code >= 0) {
            if (json.message != null && json.message != '') {
                layer.msg(json.message, {icon: 1});
            }
            $('#qForm').submit();
        } else {
            layer.msg(json.message, {icon: 2});
        }
    }

    $(function () {
        // 删除
        $('#dataGrid a[data-action="delete"]').bind('click', function () {
            var that = $(this);

            layer.confirm('确定删除此项吗?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/notice/delete', {id: that.attr('data-id')}, ajaxReload);
            }, function () {
            });
            return false;
        });

    })
</script>
</@layout>