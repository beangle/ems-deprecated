[#ftl/]
[@b.toolbar title="限制模式"/]
[@b.grid  items=permissions var="permission" sortable="false"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.edit")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol width="5%"/]
		[@b.col width="10%" property="role.name" title="角色" /]
		[@b.col width="10%" property="resource.title" title="业务数据" /]
		[@b.col width="15%" property="funcResource.title" title="系统功能" /]
		[@b.col width="40%" property="filters" title="资源过滤器" /]
		[@b.col width="10%" property="restrictions" title="其他约束" /]
		[@b.col width="10%" property="remark" title="说明" /]
	[/@]
[/@]
	