<#include "/card/utils/ui.ftl"/>
<@layout "修改用户信息">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li><a href="profile">基本信息</a></li>
			<li><a href="avatar">修改头像</a></li>
			<li><a href="password">修改密码</a></li>
			<li class="active"><a href="secret">密码保护</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div id="message">
		<#include "/default/inc/action_message.ftl"/>
		</div>
		<div class="tab-pane active">
			<div class="col-lg-8 col-lg-offset-2" style="padding-right: 0px;margin-bottom:10px;">
				<button type="button" class="btn btn-success pull-right"
						data-toggle="modal" data-target="#new_secret">新增</button>
			</div>
			<!-- 新增 start -->
			<div class="modal fade" id="new_secret" tabindex="-1" role="dialog">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">添加密保</h4>
						</div>
						<form class="form-horizontal" role="form"
							action="/user/add_secret" method="post">
							<div class="modal-body">
								<div class="form-group">
									<label for="name" class="col-sm-3 control-label">
										<span>问题：</span>
									</label>
									<div class="col-sm-9">
										<input required="required" type="text"
											class="form-control input-sm" name="question" />
									</div>
								</div>
								<div class="form-group">
									<label for="title" class="col-sm-3 control-label">
										<span>答案：</span>
									</label>
									<div class="col-sm-9">
										<input required="required" type="text"
											class="form-control input-sm" name="answer" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">取消</button>
								<button type="submit" class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- 新增 end -->
			<#list secrets as secret>
				<div class="panel panel-default col-lg-8 col-lg-offset-2">
				  <div class="panel-heading">
				    <h3 class="panel-title">
						问题${secret_index + 1}：${secret.question}
				    	<span class="glyphicon glyphicon-trash pull-right" 
				    		aria-hidden="true" data-toggle="modal" data-target="#del_secret_${secret.id}"></span>
				    </h3>
				  </div>
				  <div class="panel-body">答案${secret_index + 1}：${secret.answer}</div>
				</div>
				
				<!-- 删除 start -->
				<div class="modal fade" id="del_secret_${secret.id}" tabindex="-1" role="dialog">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title">删除密保</h4>
							</div>
							<form action="/user/delete_secret" method="get">
								<div class="modal-body">
									<input type="hidden" name="id" value="${secret.id}" />
									<div>确认删除问题：${secret.question}</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">取消</button>
									<button type="submit" class="btn btn-primary">删除</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- 删除 end -->
			</#list>
		</div>
	</div><!-- /panel-content -->
</div><!-- /panel -->

<script type="text/javascript">
$(function () {
	$('#pw').validate({
		onKeyup : true,
		onChange : true,
		eachValidField : function() {
			$(this).closest('div').removeClass('has-error').addClass('has-success');
		},
		eachInvalidField : function() {
			$(this).closest('div').removeClass('has-success').addClass('has-error');
		},
		conditional : {
			confirm : function() {
				return $(this).val() == $('#password').val();
			}
		},
		description : {
			passwd : {
				conditional : '<div class="alert alert-danger">两次输入的密码不一致</div>'
			}
		}
	});
});
</script>
</@layout>