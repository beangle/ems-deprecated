[#ftl]
[@b.head/]
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
    [@b.col property="title" title="描述"  width="10%"/]
    [@b.col property="type.name" title="类型" width="20%"/]
    [@b.col property="multiple" title="多值" width="5%"]${field.multiple?string('是','否')}[/@]
    [@b.col property="source" title="来源"  width="50%"/]
  [/@]
[/@]
</div>
[@b.foot/]