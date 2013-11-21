[#ftl]
[@b.head/]
[@b.toolbar title="数据配置项"]
  [#if role.properties?size>0]
  bar.addItem("${b.text('action.edit')}","edit()");
  bar.addItem("${b.text('action.delete')}","removeProfile()");
  [#else]
  bar.addItem("${b.text('action.add')}","edit()");
  [/#if]
[/@]
[#if (profiles?size==0)]没有设置[/#if]
 [#list profiles as profile]
  <fieldSet  align="center">
  <legend>角色配置项</legend>
  [#list profile.properties as property]
  [#assign field=property.field/]
  <li>${field.title}</li>
    [#if property.value??]
    [#if field.multiple && field.properties?? && property.value!='*']
    [#list fieldMaps[profile.id?string][field.name]! as value][#list field.properties?split(",") as pName]${value[pName]!} [/#list][#if value_has_next],[/#if][/#list]
    [#else]
    ${fieldMaps[profile.id?string][field.name]!}
    [/#if]
    [/#if]
  [/#list]
  </fieldSet>
[/#list]
<br/>

[@b.form name="profileForm" ]
  <input type="hidden" name="role.id" value="${Parameters['role.id']}"/>
  <input type="hidden" name="params" value="&role.id=${Parameters['role.id']}"/>
[/@]
<script type="text/javascript">
  function edit(){
    var form = document.profileForm;
    form.action="${b.url('!editProfile')}";
    bg.form.submit(form);
  }
  function removeProfile(){
    if(!confirm("确定删除?")) return;
    var form =document.profileForm;
    form.action="${b.url('!removeProfile')}";
    bg.form.submit(form);
  }
</script>
[@b.foot/]