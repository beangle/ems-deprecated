[#ftl]
[@b.head/]
[@b.grid items=dataTypes var="dataType"]
	[@b.gridbar ]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.edit")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="name" title="名称" width="20%"/]
		[@b.col property="typeName" title="类型" width="35%"/]
		[@b.col property="keyName" title="关键字"  width="10%"/]
		[@b.col property="properties" title="其他属性" width="30%"/]
	[/@]
[/@]
[@b.foot/]