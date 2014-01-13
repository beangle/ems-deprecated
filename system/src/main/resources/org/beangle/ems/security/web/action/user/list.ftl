[#ftl]
[@b.head/]
[#include "../status.ftl"/]
[@b.grid items=users var="user"]
  [@b.gridbar]
  function activateUser(isActivate){return action.multi("activate","确定提交?","isActivate="+isActivate);}
  bar.addItem("${b.text("action.new")}",action.add());
  bar.addItem("${b.text("action.modify")}",action.edit());
  bar.addItem("${b.text("action.freeze")}",activateUser('false'),'action-freeze');
  bar.addItem("${b.text("action.activate")}",activateUser('true'),'action-activate');
  bar.addItem("${b.text("action.delete")}",action.remove());
  bar.addItem("${b.text("action.export")}",action.exportData("name,fullname,mail,creator.fullname,effectiveAt,invalidAt:common.invalidAt,passwordExpiredAt,createdAt:common.createdAt,updatedAt:common.updatedAt,enabled","Csv","&fileName=用户信息"));
  [/@]
  [@b.row]
    [@b.boxcol/]
    [@b.col property="name" width="15%"]&nbsp;[@b.a href="!dashboard?user.id=${user.id}" target="_blank"]${user.name}[/@][/@]
    [@b.col property="fullname" width="10%"/]
    [@b.col title="user.members" width="20%"][#list user.members as m][#if m.member]${m.role.name} [/#if][/#list][/@]
    [@b.col property="mail" width="20%"/]
    [@b.col property="creator.name" width="10%"/]
    [@b.col property="effectiveAt" width="12%"]${user.effectiveAt?string("yyyy-MM")}~${(user.invalidAt?string("yyyy-MM"))!}[/@]
    [@b.col property="enabled" width="8%"][@enableInfo user.enabled/][/@]
  [/@]
[/@]
[@b.foot/]