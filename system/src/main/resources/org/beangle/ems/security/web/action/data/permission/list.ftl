[#ftl/]
[@b.grid  items=permissions var="permission" sortable="false"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.edit")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove());
  [/@]
  [@b.row]
    [@b.boxcol/]
    [@b.col width="10%" property="resource.title" title="dataPermission.resource" /]
    [@b.col width="10%" property="role.name" title="entity.role" /]
    [@b.col width="15%" property="funcResource.title" title="entity.funcResource" ]
    <span title="${(permission.funcResource.name)!}">${(permission.funcResource.title)!}</span>[/@]
    [@b.col width="52%" property="filters" title="dataPermission.filters" /]
    [@b.col width="8%" property="restrictions" title="其他约束" /]
  [/@]
[/@]
  