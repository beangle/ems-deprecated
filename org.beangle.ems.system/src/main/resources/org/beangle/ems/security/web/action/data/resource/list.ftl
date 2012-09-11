[#ftl]
[@b.head/]
[@b.grid items=resources var="resource"]
	[@b.gridbar ]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.edit")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol width="5%"/]
		[@b.col property="title" title="标题" width="20%"/]
		[@b.col property="name" title="名称" width="40%"/]
		[@b.col property="remark" title="描述"  width="35%"/]
	[/@]
[/@]
[@b.foot/]