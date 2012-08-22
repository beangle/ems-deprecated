[#ftl/]
[@b.grid  items=permissions var="permission" sortable="false"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.edit")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol width="5%"/]
		[@b.col width="10%" property="resource.title" title="dataPermission.resource" /]
		[@b.col width="10%" property="role.name" title="entity.role" /]
		[@b.col width="15%" property="funcResource.title" title="entity.funcResource" /]
		[@b.col width="40%" property="filters" title="dataPermission.filters" /]
		[@b.col width="10%" property="restrictions" title="其他约束" /]
		[@b.col width="10%" property="remark" title="common.remark" /]
	[/@]
[/@]
	