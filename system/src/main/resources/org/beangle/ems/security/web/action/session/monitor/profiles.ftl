[#ftl]
[@b.head/]
[#include "nav.ftl"/]
[@b.messages slash="true"/]
[@b.form action="!saveProfile"]
<table width="70%">
  <tr style="background-color:#e1e8f5">
    <td>角色</td>
    <td>上限</td>
    <td>过期时间(分)</td>
    <td>单用户最大会话数</td>
  </tr>
  [#list profiles as profile]
  <tr style="background-color:${(profile_index%2!=0)?string("#e1e8f5","#FFFFFF")}">
    <td>${profile.role.indexno} ${profile.role.name}</td>
    <td><input name="max_${profile.role.id}" value="${profile.capacity}" style="width:50px" maxlength="5"/></td>
    <td><input name="inactiveInterval_${profile.role.id}" value="${profile.inactiveInterval}" style="width:50px" maxlength="5"/>分</td>
    <td><input name="maxSessions_${profile.role.id}" value="${profile.userMaxSessions}" style="width:35px" maxlength="2"/></td>
  </tr>
  [/#list]
  <tr style="background-color:#e1e8f5;[#if roles?size==0]display:none[/#if]" >
    <td>新增:[@b.select name="roleId_new" items=roles option=r"${item.indexno} ${item.name}" empty="..."/]</td>
    <td><input name="max_new" value="" style="width:50px" maxlength="5"/></td>
    <td><input name="inactiveInterval_new" value="" style="width:50px" maxlength="5"/>分</td>
    <td><input name="maxSessions_new" value="" style="width:35px" maxlength="2"/></td>
  </tr>
  <tr>
    <td  colspan="5">注:最大会话数指单个用户同时在线数量&nbsp;&nbsp;
    &nbsp;
    [@b.reset/]&nbsp;&nbsp;[@b.submit value="提交" onsubmit="validateProfile"/]
    </td>
  </tr>
</table>
[/@]
<script type="text/javascript">
  function validateProfile(form){
    [#list profiles as profile]
    if(!(/^\d+$/.test(form['max_${profile.role.id}'].value))){alert("${profile.role.name}最大用户数限制应为0或正整数");return false;}
    if(!(/^\d+$/.test(form['maxSessions_${profile.role.id}'].value))){alert("${profile.role.name}最大会话数应为0或正整数");return false;}
    if(!(/^\d+$/.test(form['inactiveInterval_${profile.role.id}'].value))){alert("${profile.role.name}过期时间应为0或正整数");return false;}
    [/#list]
    if(form['roleId_new'].value!=""){
      if(!(/^\d+$/.test(form['max_new'].value))){alert("新增配置最大用户数限制应为0或正整数");return false;}
      if(!(/^\d+$/.test(form['maxSessions_new'].value))){alert("新增配置最大会话数应为0或正整数");return false;}
      if(!(/^\d+$/.test(form['inactiveInterval_new'].value))){alert("新增配置过期时间应为0或正整数");return false;}
    }
    return true;
  }
</script>
[@b.foot/]