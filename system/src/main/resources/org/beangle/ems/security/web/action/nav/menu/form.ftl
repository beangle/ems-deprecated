[#ftl]
[@b.head/]
[@b.toolbar title="info.moduleUpdate"]bar.addBack();[/@]
[#assign userMsg]${b.text("entity.menu")}[/#assign]
[#assign labelInfo]${b.text("ui.editForm",userMsg)}[/#assign]
[@b.form action="!save" title=labelInfo theme="list"]
  [@b.field label="菜单配置" required="true"]
    <select  name="menu.profile.id" style="width:150px;" >
    [#list profiles as profile]
    <option value="${profile.id}" [#if ((menu.profile.id)!0) ==profile.id]selected="selected"[/#if]>${profile.name}</option>
    [/#list]
    </select>
  [/@]
  [@b.textfield label="common.name" name="menu.name" value="${menu.name!}" style="width:200px;"  required="true" maxlength="100" /]
  [@b.textfield label="标题" name="menu.title" value="${menu.title!}" style="width:200px;" required="true" maxlength="50"/]
  [@b.select label="上级菜单" name="parent.id" value=(menu.parent)! style="width:200px;"  items=parents option="id,description" empty="..."/]
  [@b.textfield label="同级顺序号" name="indexno" value="${menu.index!}" required="true" maxlength="2" check="match('integer').range(1,100)" /]
  [@b.radios label="common.status"  name="menu.enabled" value=menu.enabled items="1:action.activate,0:action.freeze" comment="冻结会禁用该菜单及其所有下级"/]
  [@b.textfield label="menu.entry"  name="menu.entry" value="${menu.entry!}" maxlength="100" /]
  [@b.select2 label="使用资源" name1st="Resources" name2nd="resourceId" items1st=resources?sort_by("name") items2nd= menu.resources?sort_by("name") option="id,description"/]
  [@b.textarea label="common.remark"  name="menu.remark" maxlength="50" value=menu.remark! rows="2" cols="40"/]
  [@b.formfoot]
    [@b.reset/]&nbsp;&nbsp;
    [@b.submit value="action.submit" /]
    <input type="hidden" name="menu.id" value="${menu.id!}" />
  [/@]
[/@]
[@b.foot/]