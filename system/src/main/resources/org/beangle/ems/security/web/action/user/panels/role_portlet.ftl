[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
  <div class="ui-widget-header ui-corner-all"><span class="title">角色信息</span></div>
  <div class="portlet-content">
  [@b.grid  items=user.members var="m" sortable="false"]
    [@b.row]
      [@b.col width="49%" title="info.role" property="role.name"]<span title="加入时间：${(m.createdAt?string('yyyy-MM-dd HH:mm'))!}">${m.role.name}</span>[/@]
      [@b.col width="17%" title="成员"][#if m.member]<img src="${b.theme.iconurl('actions/activate.png')}"/>[/#if][/@]
      [@b.col width="17%" title="授权"][#if m.granter]<img src="${b.theme.iconurl('actions/activate.png')}"/>[/#if][/@]
      [@b.col width="17%" title="管理"][#if m.manager]<img src="${b.theme.iconurl('actions/activate.png')}"/>[/#if][/@]
    [/@]
  [/@]
  </div>
</div>
