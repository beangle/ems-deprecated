[#ftl]
[@b.head/]
<div>
[#include "../userNav.ftl"/]
<table class="indexpanel">
<tr>
  <td class="index_view">
  [@b.form name="userSearchForm" action="!search" title="ui.searchForm" target="userlist" theme="search"]
    [@b.textfields names="user.name,user.fullname,user.creator.name,roleName;entity.role"/]
    [@b.select name="user.enabled" label="common.status" value="1" empty="..." items={'1':'${b.text("action.activate")}','0':'${b.text("action.freeze")}'}/]
  [/@]
  </td>
  <td class="index_content">
  [@b.div id="userlist" href="!search?user.enabled=1" /]
  </td>
</tr>
</table>
</div>
[@b.foot/]