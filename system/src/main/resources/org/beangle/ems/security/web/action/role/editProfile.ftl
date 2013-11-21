[#ftl]
[@b.head/]
[@b.toolbar title="角色数据配置"]
  function save(){if(confirm("确定设置?")){bg.form.submit(document.profileForm);}}
  function cancelEdit(){bg.form.submit(document.profileForm,'${b.url("!profile")}')}
  bar.addItem("${b.text("action.save")}",save,'${b.theme.iconurl("actions/save.png")}');
  bar.addItem("取消",cancelEdit,'${b.theme.iconurl("actions/close.png")}');
[/@]
[@b.form name="profileForm" action="!saveProfile"]
  <input type="hidden" name="role.id" value="${role.id}"/>
  <input type="hidden" name="params" value="&role.id=${role.id}"/>
  [@b.tabs]
    [#list fields?sort_by("title") as field]
    [@b.tab label="${field.name}(${field.title})"]
    [#if ignoreFields?seq_contains(field)]
    <div>
      <input name="ignoreField${field.id}" type="radio" value="1" [#if holderIgnoreFields?seq_contains(field)]checked="checked"[/#if] id="ignoreField${field.id}_1"><label for="ignoreField${field.id}_1">使用通配符*</label>
      <input name="ignoreField${field.id}" type="radio" value="0" [#if !holderIgnoreFields?seq_contains(field)]checked="checked"[/#if] id="ignoreField${field.id}_2"><label for="ignoreField${field.id}_2">选择或填写具体值</label>
    </div>
    [/#if]
    [#if field.multiple && field.keyName?exists]
      [@b.grid items=mngFields[field.name] var="value"]
        [@b.row]
          [@b.boxcol property=field.keyName boxname=field.name checked=(aoFields[field.name]?seq_contains(value))!false /]
          [#if field.properties??]
          [#list field.properties?split(",") as pName][@b.col title=pName]${value[pName]!}[/@][/#list]
          [#else]
          [@b.col title="可选值"]${value}[/@]
          [/#if]
        [/@]
      [/@]
    [#else]
    <table class="grid" width="100%">
      <tr><td colspan="2"><input type="text" name="${field.name}" value="${aoFields[field.name]!}"/>[#if field.multiple]多个值请用,格开[/#if]</td></tr>
    </table>
    [/#if]
    [/@]
    [/#list]
  [/@]
[/@]
[@b.foot/]