[#ftl]
[@b.head/]
[#include "../../nav.ftl"/]
<table  class="indexpanel">
  <tr>
    <td class="index_view">
      [@b.form action="!search?orderBy=menu.indexno" title="ui.searchForm" target="menulist" theme="search"]
        [@b.select name="menu.profile.id" items=profiles label="配置"/]
        [@b.textfields names="menu.indexno;common.code,menu.title;标题,menu.entry;menu.entry"/]
        [@b.select name="menu.enabled" items=profiles label="common.status" items={'true':'${b.text("action.activate")}','false':'${b.text("action.freeze")}'}  empty="..."/]
      [/@]
    </td>
    <td class="index_content">
    [@b.div  href="!search?menu.profile.id=${(profiles?first.id)!}&orderBy=menu.indexno" id="menulist"/]
    </td>
  </tr>
</table>
[@b.foot/]
