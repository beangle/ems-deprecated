[#ftl/]
[@b.toolbar title="entity.dataResource"]
bar.addBack("${b.text("action.back")}");
[/@]
[@b.form theme="list" action="!save"]
  [@b.textfield name="resource.title" required="true" value=resource.title! label="common.title"/]
  [@b.textfield name="resource.name" required="true" value=resource.name! label="common.name" style="width:300px"/]
  [@b.field label="属性"]
  <div id="fieldtable" style="display:table;">
      [#list resource.fields as field]
      <div id="field${field_index}">
        <input name="field${field_index}.name" maxlength="14" type="text" placeholder="请输入名称" value="${field.name}"/>
        <input name="field${field_index}.title" maxlength="14" type="text" placeholder="请输入标题" value="${field.title}" />
        <input name="field${field_index}.typeName" maxlength="100" required="true" value="${field.typeName}" style="width:300px" />
        <input type="button" value="删除" onclick="removeField(this)" />
      </div>
      [/#list]
      <input id="fieldaddbutton" type="button" value="添加条目" onclick="addField()" />
      <div id="fieldblueprint" style="display:none;">
        <input name="" maxlength="14" role="name" type="text" placeholder="请输入名称" value="" />
        <input name="" maxlength="14" role="title" type="text" placeholder="请输入标题" value="" />
        <input name="" maxlength="100" role="type" style="width:300px" type="text" placeholder="请输入类型" value="" />
        <input type="button" value="删除" onclick="removeField(this)"/>
      </div>
    </div>
    [/@]
  [@b.textfield name="resource.remark" value=resource.remark! label="common.remark"/]
  [@b.formfoot]
    <input type="hidden" name="resource.id" value="${(resource.id)!}" />
    <input type="hidden" name="fieldCount" id="fieldCount" value="${resource.fields?size}" />
    [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
  [/@]
[/@]
<script>
prefix="field";
count=${resource.fields?size};
function addField() {
  var newField = jQuery("#" + prefix + "blueprint").clone(true);
  newField.attr('id', prefix + count);
  newField.removeAttr('style');
  newField.insertBefore(jQuery("#" + prefix + "addbutton"));
  newField.find("input[role='name']").attr('name', prefix + count + ".name");
  newField.find("input[role='title']").attr('name', prefix + count + ".title");
  newField.find("input[role='type']").attr('name', prefix + count + ".typeName");
  jQuery("#fieldCount").val(count);
  count++;
}
function removeField(button) {
  jQuery(button).parent('div').remove();
}
</script>
