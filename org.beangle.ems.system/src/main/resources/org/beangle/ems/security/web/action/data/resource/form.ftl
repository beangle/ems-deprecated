[#ftl/]
[@b.form theme="list" action="!save"]
	[@b.textfield name="resource.title" required="true" value=resource.title! label="common.title"/]
	[@b.textfield name="resource.name" required="true" value=resource.name! label="common.name"/]
	[@b.textfield name="resource.remark" value=resource.remark! label="common.remark"/]
	[@b.formfoot]
		<input type="hidden" name="resource.id" value="${(resource.id)!}" />
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
	[/@]
[/@]
	