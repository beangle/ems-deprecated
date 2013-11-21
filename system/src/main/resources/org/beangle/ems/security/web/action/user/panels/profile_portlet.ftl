[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
  <div class="ui-widget-header ui-corner-all"><span class="title">默认数据权限</span></div>
  <div class="portlet-content">
  [#if (profiles?size==0)]没有设置[/#if]
   [#list profiles as profile]
    <fieldSet  align="center">
    <legend>数据权限${profile_index+1}</legend>
    [#list profile.properties as property]
    [#assign field=property.field/]
    <li>${field.title}</li>
      [#if property.value??]
      [#if field.multiple && field.properties?? && property.value!='*']
      [#list fieldMaps[profile.id?string][field.name]! as value][#list field.properties?split(",") as pName]${value[pName]!} [/#list][#if value_has_next],[/#if][/#list]</td>
      [#else]
      ${fieldMaps[profile.id?string][field.name]!}
      [/#if]
      [/#if]
    [/#list]
    </fieldSet>
  [/#list]
  </div>
</div>
