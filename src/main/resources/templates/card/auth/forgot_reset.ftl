<#include "/card/utils/ui.ftl"/>

<@layout "重置密码">

<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">重置密码</h3>
            </div>
            <div class="panel-body">
                <div id="message">
                    <#include "/card/inc/action_message.ftl"/>
                </div>
                <form method="POST" action="/forgot/reset" accept-charset="UTF-8">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <input type="hidden" name="secretId" value="${secret.id}" />
                    <input type="hidden" name="question" value="${secret.question}"/>
                    <div class="form-group">
                        <label class="control-label" for="answer">密保问题：${secret.question}</label>
                        <input class="form-control" name="answer" id="answer" placeholder="请输入密保答案" data-required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password">设置密码</label>
                        <input class="form-control" name="password" id="password" type="password" maxlength="18" placeholder="新密码" data-required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password2">确认密码</label>
                        <input class="form-control" name="password2" id="password2" type="password" maxlength="18" placeholder="请再输入一次密码" data-required data-conditional="confirm" data-describedby="message" data-description="confirm">
                    </div>
                    <button type="submit" class="btn btn-success btn-block">
                        提 交
                    </button>

                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function(){
        $('form').validate({
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
                confirm : {
                    conditional : '<div class="alert alert-danger">两次输入的密码不一致</div>'
                }
            }
        });
    })
</script>
</@layout>