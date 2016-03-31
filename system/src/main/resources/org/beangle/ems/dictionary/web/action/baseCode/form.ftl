[#ftl/][@b.head/]
[#assign labInfo][#if locale.language?index_of("en")!=-1]${coder.enName}[#else]${coder.name}[/#if]&nbsp;&nbsp;${b.text("baseCode")}[/#assign]  
[@b.form action="!save" theme="list" title="${labInfo}"]
  [@b.textfield name="baseCode.code" required="true" maxlength="25" label="common.code" value="${baseCode.code!}"/]
  [@b.textfield name="baseCode.name" required="true" maxlength="25" label="common.name" value="${baseCode.name!}"/]
  [@b.textfield name="baseCode.enName" maxlength="50" label="common.enName" style="width:200px" value="${baseCode.enName!}"/]
  [@b.datepicker readOnly="readOnly" name="baseCode.effectAt" required="true"  label="生效日期" value="${(baseCode.effectAt?string('yyyy-MM-dd'))!}"/]
  [@b.datepicker readOnly="readOnly" name="baseCode.invalidAt" label="失效日期" value="${(baseCode.invalidAt?string('yyyy-MM-dd'))!}"/]
  [@b.formfoot]
  <input type="hidden" name="baseCode.id" value="${baseCode.id?if_exists}" />
    <input type="hidden" name="baseCodeId" value="${baseCode.id?if_exists}"/>
    <input type="hidden" name="codeName" value="${Parameters['codeName']}"/>
    [@b.submit value="system.button.submit"/]
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" onclick='reset()' value="${b.text("system.button.reset")}" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;       
  [/@]
[/@]
[@b.foot/]