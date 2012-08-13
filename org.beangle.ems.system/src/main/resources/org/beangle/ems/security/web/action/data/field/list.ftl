[#ftl]
[@b.head/]
[@b.toolbar title="数据限制项"/]
<div id="restrict_metas">
[@b.grid items=fields var="field"]
	[@b.gridbar ]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.edit")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="name" title="名称" width="10%"/]
		[@b.col property="remark" title="描述"  width="10%"/]
		[@b.col property="valueType" title="类型" width="20%"/]
		[@b.col property="keyName" title="关键字" width="5%"/]
		[@b.col property="propertyNames" title="其他显示属性" width="15%"/]
		[@b.col property="multiple" title="多值" width="5%"]${field.multiple?string('是','否')}[/@]
		[@b.col property="source" title="来源"  width="35%"/]
	[/@]
[/@]
</div>
[@b.foot/]