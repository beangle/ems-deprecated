[#ftl]
[@b.head/]
[#include "../userNav.ftl"/]
<table  class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="roleSearchForm"  action="!search" target="rolelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="role.name;common.name,role.owner.name;common.creator"/]
    [/@]
    </td>
    <td class="index_content">[@b.div id="rolelist" href="!search?orderBy=role.indexno" /]</td>
  </tr>
</table>
[@b.foot/]