[#ftl]
[@b.head/]
[@b.toolbar title="数据配置项"]
  bar.addItem("${b.text('action.new')}","add()");
[/@]
[#if (profiles?size==0)]没有设置[/#if]
 [#list profiles as profile]
  <fieldSet  align="center">
  <legend><a href="javascript:void(0)" onclick="edit('${profile.id}')">修改</a>  <a href="javascript:void(0)" onclick="removeProfile('${profile.id}')">删除</a></legend>
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
  <input type="hidden" name="user.id" value="${Parameters['user.id']}"/>
  <input type="hidden" name="params" value="&user.id=${Parameters['user.id']}"/>
[/@]
<script type="text/javascript">
  function add(){
    var form = document.profileForm;
    form.action="${b.url('!edit')}";
    bg.form.submit(form);
  }
  function edit(profileId){
    var form = document.profileForm;
    bg.form.addInput(form,"profile.id",profileId);
    form.action="${b.url('!edit')}";
    bg.form.submit(form);
  }
  function removeProfile(profileId){
    if(!confirm("确定删除?")) return;
    var form =document.profileForm;
    bg.form.addInput(form,"profile.id",profileId);
    form.action="${b.url('!remove')}";
    bg.form.submit(form);
  }
</script>
[@b.foot/]