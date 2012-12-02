[#ftl]
[@b.head/]
[@b.toolbar title='${b.text("entity.user")}${b.text("common.detail")}']bar.addBack("${b.text("action.back")}");[/@]
[#macro info(name,title='')]
  [#if title=='']
   <td class="title">${b.text('user.'+name)}:</td>
  [#else]
  <td class="title">${b.text(title)}:</td>
  [/#if]
   <td class="content"> ${(user[name]?string)!}</td>
[/#macro]

<table class="infoTable">
  <tr>
   [@info 'name' /]
   [@info 'fullname' /]
  </tr>
  <tr>
  [@info 'mail' /]
  [@info 'createdAt','common.createdAt' /]
  </tr>
  <tr>
  [@info 'enabled'][#if user.enabled] ${b.text("action.activate")}[#else]${b.text("action.freeze")}[/#if][/@]
  [@info 'updatedAt','common.updatedAt' /]
  </tr>
  <tr>
  <td class="title" >${b.text("common.creator")}:</td>
  <td class="content">${(user.creator.name)!}  </td>
  <td class="title" >${b.text("user.members")}:</td>
  <td  class="content">[#list user.members! as m]${m.role.name}([#if m.member]${b.text('member.member')}[/#if][#if m.manager] ${b.text('member.manager')}[/#if][#if m.granter] ${b.text('member.granter')}[/#if])[/#list]</td>
  </tr>
  <tr>
  <td class="title" >${b.text("common.remark")}:</td>
  <td class="content">${user.remark!}</td>
  <td class="title" >权限控制台:</td>
  <td class="content">[@b.a target="_blank" href="user!dashboard?user.id=${user.id}"]查看权限用户控制台[/@]</td>
  </tr>
</table>
[@b.foot/]