[#ftl]
[#assign labInfo]${b.text("security.dataPermission.info")}[/#assign]
[@b.toolbar title=labInfo]
bar.addBack("${b.text("action.back")}");
[/@]
[@b.form action="!save" theme="list" title="数据授权模式"]
	[@b.select label="业务数据" name="permission.resource.id" value=permission.resource! required="true" items=dataResources?sort_by("title") width="200px"/]
	[@b.select label="角色" name="permission.role.id" value=permission.role! empty="..." items=roles?sort_by("code") width="200px"/]
	[@b.select label="系统功能" name="permission.funcResource.id" empty="..." option=r"${item.name} ${item.title!}" value=permission.funcResource! items=funcResources?sort_by("name") width="200px"/]
	
	[@b.textarea label="permission.filters" name="permission.filters" required="true" value="${permission.filters!}" maxlength="400" rows="4" style="width:500px;"/]
	[@b.startend label="生效时间范围" name="permission.effectiveAt,permission.invalidAt" start=permission.effectiveAt! end=permission.endAt! required="true,false"/]
	[@b.textfield label="描述" name="permission.remark" value="${permission.remark!}" maxlength="50" width="100px"/]
	[@b.formfoot]
		<input type="hidden" name="permission.id" value="${permission.id!}"/>
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
	[/@]
</table>
[/@]