[#ftl]
[@b.head/]
[#include "../monitor/nav.ftl"/]
[@b.form target="sessioninfoLogResult"]
<table width="100%" class="searchTable">
  <tr>
    <td>登录名:<input name="sessioninfoLog.username" value="" style="width:100px" maxlength="32" />
    姓名:<input name="sessioninfoLog.fullname" value="" style="width:80px" maxlength="32"/>
    [@b.startend name="startTime,endTime" format="datetime" label="登录起始,截止" style="width:160px" /]
    ip:<input name="sessioninfoLog.ip" value="" style="width:100px" maxlength="32"/>
    </td>
    <td>
      [@b.submit action="!search" value="查询"/]
      [@b.submit action="!loginCountStat" value="次数统计"/]
      [@b.submit action="!timeIntervalStat" value="时段统计"/]
      [@b.reset/]
    </td>
  </tr>
</table>
[/@]
[@b.div id="sessioninfoLogResult" href="!search" /]
[@b.foot/]