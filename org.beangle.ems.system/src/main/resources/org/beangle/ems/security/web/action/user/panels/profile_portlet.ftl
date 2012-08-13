[#ftl]
<div class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	<div class="ui-widget-header ui-corner-all"><span class="title">默认数据权限</span></div>
	<div class="portlet-content">
	[#if (profiles?size==0)]没有设置[/#if]
		 [#list profiles as profile]
			<fieldSet  align="center">
			<legend>${profile.pattern.remark}(${profile.enabled?string("启用","禁用")})</legend>
			[#list profile.pattern.entity.fields as field]
				<li>${field.remark}</li>
				[#if field.multiple]
				[#list fieldMaps[profile.id?string][field.name]! as value][#list field.propertyNames?split(",") as pName]${value[pName]!} [/#list][#if value_has_next],[/#if][/#list]</td>
				[#else]
				${fieldMaps[profile.id?string][field.name]!}
				[/#if]
			[/#list]
			</fieldSet>
		[/#list]
	</div>
</div>
