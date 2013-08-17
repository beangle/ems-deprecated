[#ftl]
[@b.head/]
<script type="text/javascript">
  bg.ui.load("tabletree");
</script>
[#include "../../status.ftl"/]
[@b.grid  items=menus var="menu" sortable="false"]
[@b.gridbar title="菜单列表"]
  action.addParam('menu.profile.id',"${Parameters['menu.profile.id']!}");
  function activate(isActivate){
    return action.multi("activate","确定操作?","isActivate="+isActivate);
  }
  function preview(){window.open("${b.url('!preview')}?${b.paramstring}");}
  function redirectTo(url){window.open(url);}
  bar.addItem("${b.text("action.new")}",action.add());
  bar.addItem("${b.text("action.edit")}",action.edit());
  bar.addItem("${b.text("action.freeze")}",activate(0),'action-freeze');
  bar.addItem("${b.text("action.activate")}",activate(1),'action-activate');
  bar.addItem("${b.text("action.delete")}",action.remove());
  bar.addItem("${b.text("action.export")}",action.exportData("code:代码,title:common.title,name:common.name,entry:入口,resources:使用资源,enabled:common.status,remark:common.remark",null,"&fileName=菜单信息"));
  bar.addItem("打印","preview()","action-print");
  bar.addItem("菜单配置","redirectTo('${b.url('profile!search')}')");
[/@]
  [@b.row]
    <tr [#if menu??] title="入口及备注:${menu.entry!} ${(menu.remark?html)!}" id="${menu.indexno}"[/#if]>
    [@b.boxcol/]
    [@b.treecol title="common.title" width="30%"][@b.a href="!info?menu.id=${menu.id}"]${menu.indexno} ${menu.title}[/@][/@]
    [@b.col property="name" title="common.name" width="15%"/]
    [@b.col width="40%" title="使用资源"][#list menu.resources as re]${re.title?html}[#if re_has_next],[/#if][/#list][/@]
    [@b.col property="enabled" width="10%" title="common.status"][@enableInfo menu.enabled/][/@]
    </tr>
  [/@]
[/@]
[@b.foot/]