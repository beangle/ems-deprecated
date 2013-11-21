[#ftl]
[@b.head/]
[@b.grid items=resources var="resource"]
  [@b.gridbar ]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.edit")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove());
  [/@]
  [@b.row]
    [@b.boxcol/]
    [@b.col property="title" title="标题" width="10%"/]
    [@b.col property="name" title="名称" width="30%"/]
    [@b.col property="remark" title="属性"  width="55%"][#list resource.fields as f]${f.name}(${f.title})[#if f_has_next],[/#if][/#list][/@]
  [/@]
[/@]
[@b.foot/]