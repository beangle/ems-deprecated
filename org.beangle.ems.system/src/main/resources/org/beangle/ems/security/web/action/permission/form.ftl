[#ftl]
[@b.head/]
[#include "../status.ftl"/]
<script type="text/javascript">
  bg.ui.load("tabletree");
</script>
<script type="text/javascript">
  function getIds(){
    return(getCheckBoxValue(document.getElementsByName("menuId")));
  }
  function save(){
    document.permissionForm.action="${b.url('!save')}";
    if(confirm("${b.text("alert.authoritySave",role.name)}")){
      document.permissionForm.submit();
    }
  }
  /**选中或取消资源*/
  function checkResource(ele){
    menuBoxId=ele.id;
    var stats = ele.checked;
    var num=0;
    var resourceBox;
    do{
      resourceBox = document.getElementById(menuBoxId+'_'+num);
      if(null==resourceBox) break;
      if(!resourceBox.disabled) resourceBox.checked = stats;
      num++;
    }while(resourceBox!=null);
  }
</script>
<table width="90%" align="center">
<tr>
<td valign="top">
[@b.toolbar]
  bar.setTitle('角色-->菜单和资源权限');
  bar.addItem("${b.text("action.spread")}","displayAllRowsFor(1);",'tree-folder');
  bar.addItem("${b.text("action.collapse")}","collapseAllRowsFor(1);",'tree-folder-open');
  bar.addItem("${b.text("action.save")}",save,'save.png');
[/@]
[@b.form name="permissionForm" action="!edit"]
<table width="100%" class="searchTable" id="meunAuthorityTable">
  <tr>
    <td>
    角色:<select name="role.id" onchange="this.form.submit()" style="width:250px">
       [#list mngRoles?sort_by("indexno")! as r]
        <option value="${r.id}" [#if r.id=role.id]selected="selected"[/#if]>${r.indexno} ${r.name}</option>
       [/#list]
    </select>
    </td>
    <td class="title">
    菜单配置:<select name="menuProfileId" style="width:150px;" onchange="this.form.submit();">
      [#list menuProfiles as profile]
      <option value="${profile.id}" [#if profile=menuProfile]selected="selected"[/#if]>${profile.name}</option>
      [/#list]
      </select>
    </td>
    <td><input name="displayFreezen" [#if Parameters['displayFreezen']??]checked="checked"[/#if] onclick="this.form.submit();" id="displayFreezen" type="checkbox"><label for="displayFreezen">显示冻结菜单</label></td>
  </tr>
</table>

<table width="100%" class="gridtable">
  <tbody>
  <tr class="gridhead">
  <th width="6%"><input type="checkbox" onclick="treeToggleAll(this,checkResource)"/></th>
  <th width="28%">${b.text("common.name")}</th>
  <th width="10%">${b.text("common.id")}</th>
  <th width="50%">可用资源</th>
  <th width="6%">${b.text("common.status")}</th>
  </tr>
  [#macro i18nTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]
  [#list menus?sort_by("indexno") as menu]

  <tr class="grayStyle [#if !menu.enabled]ui-disabled[/#if]" id="${menu.indexno}">
    <td  class="gridselect">
      <input type="checkbox" id="checkbox_${menu_index}" onclick="treeToggle(this,checkResource)"  name="menuId" [#if parentMenus?seq_contains(menu)]checked="checked" disabled="disabled"[#else][#if (roleMenus?seq_contains(menu))]checked="checked"[/#if][/#if] value="${menu.id}">
    </td>
    <td>
    <div class="tree-tier${menu.depth}">
      [#if menu.children?size==0]
      <a href="#" class="tree-item"></a>[#rt]
      [#else]
      <a href="#" class="tree-folder-open" id="${menu.indexno}_folder" onclick="toggleRows(this);"></a>[#rt]
      [/#if]
       [@i18nTitle menu/]
    </div>
    </td>
    <td >&nbsp;${menu.indexno}</td>
    <td>
      [#list menu.resources as resource]
        [#if resources?seq_contains(resource)]
        <input type="checkbox" name="resourceId" id="checkbox_${menu_index}_${resource_index}" [#if parentResources?seq_contains(resource)]checked="checked" disabled="disabled"[#else][#if roleResources?seq_contains(resource)]checked="checked"[/#if][/#if] value="${resource.id}">[#rt]
        ${resource.title}
        [/#if]
        [#if resource_index%3==1]<br/>[/#if]
      [/#list]
    </td>
    <td align="center">[@shortEnableInfo menu.enabled/]</td>
  </tr>
  [/#list]
  </tbody>
</table>
[/@]
  </td>
  </tr>
</table>
[@b.foot/]