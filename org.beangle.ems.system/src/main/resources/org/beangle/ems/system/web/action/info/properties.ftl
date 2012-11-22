[#ftl]
[@b.head]
<link rel="stylesheet" href="${base}/static/themes/${b.theme.name}/system-info.css" type="text/css"/>
[/@]
[#include "../nav.ftl"/]
<style>
.ui-widget {
    font-family: Verdana,Arial,sans-serif;
    font-size: 1 em;
}
</style>
<div style="margin:5px 5px;padding-bottom: 5px;width: 90%;">
[@b.tabs style="width: 90%"]
	[@b.tab label="操作系统和java"]
	<h3>操作系统</h3>
	<hr>
	<table width="100%" style="font-size:13px">
		<tr>
			<td>名称:</td><td>${os.name}</td><td>版本:</td><td>${os.version}</td>
		</tr>
		<tr>
			<td>体系结构:</td><td>${os.arch}</td><td>文件分割符:</td><td>${os.fileSeparator}</td>
		</tr>
		<tr>
			<td>路径分割符:</td><td>${os.pathSeparator}</td><td>换行符:</td><td>${os.lineSeparator?js_string}</td>
		</tr>
		<tr>
			<td>机器名:</td><td>${host.hostname}</td><td>IP:</td><td>[#list host.addresses?keys as a]${a}:${host.addresses[a]}[#if a_has_next]<br/>[/#if][/#list]</td>
		</tr>
		<tr>
			<td>用户:</td><td>${user.name}</td><td>主目录:</td><td>${user.home}</td>
		</tr>
		<tr>
			<td>国家地区:</td><td>${user.country}</td><td>语言:</td><td>${user.language!}</td>
		</tr>
	</table>
	<h3>Java</h3>
	<hr>
	<table width="100%" style="font-size:13px">
		<tr>
			<td>Java版本:</td><td>${java.version}</td><td>Java开发商:</td><td>${java.vendor}</td>
		</tr>
		<tr>
			<td>Java规范版本:</td><td>${javaSpec.version}</td><td>Java规范开发商:</td><td>${javaSpec.vendor}</td>
		</tr>
		<tr>
			<td>Jvm名称:</td><td>${jvm.name}</td><td>Jvm信息:</td><td>${jvm.info}</td>
		</tr>
		<tr>
			<td>Jvm版本:</td><td>${jvm.version}</td><td>Jvm开发商:</td><td>${jvm.vendor}</td>
		</tr>
		<tr>
			<td>Jvm规范:</td><td>${jvmSpec.name}</td><td>Jvm规范开发商:</td><td>${jvmSpec.vendor}</td>
		</tr>
	</table>
	<h3>Java 运行时</h3>
	<hr>
	<table width="100%" style="font-size:13px">
		<tr>
			<td>运行时:</td><td>${javaRuntime.name}</td><td>版本:</td><td>${javaRuntime.version}</td>
		</tr>
		<tr>
			<td>Class文件版本:</td><td>${javaRuntime.classVersion}</td><td>目录:</td><td>${javaRuntime.home}</td>
		</tr>
		<tr>
			<td>临时目录:</td><td>${javaRuntime.tmpDir}</td><td>文件编码:</td><td>${javaRuntime.fileEncoding}</td>
		</tr>
		<tr>
			<td>扩展目录:</td><td>[#list javaRuntime.extDirs?split(os.pathSeparator) as dir]${dir}[#if dir_has_next]<br/>[/#if][/#list]</td><td>签名目录:</td><td>[#list javaRuntime.endorsedDirs?split(os.pathSeparator) as dir]${dir}[#if dir_has_next]<br/>[/#if][/#list]</td>
		</tr>
		<tr>
			<td>Classpath:</td><td>[#list javaRuntime.classpath?split(os.pathSeparator) as p]${p}[#if p_has_next]<br/>[/#if][/#list]</td>
			<td>库路径:</td><td>[#list javaRuntime.libraryPath?split(os.pathSeparator) as p]${p}[#if p_has_next]<br/>[/#if][/#list]</td>
		</tr>
	</table>
	[/@]
	[@b.tab label="其他属性"]
		<table class="kv-table">
			[#list extra?keys?sort as key]
			<tr>
				<td class="kv-key">${b.text(key)}</td>
				<td>${extra[key]}</td>
			</tr>
			[/#list]
		</table>
	[/@]
	[@b.tab label="环境变量"]
		<table class="kv-table">
			[#list env?keys?sort as key]
			<tr>
				<td class="kv-key">${b.text(key)}</td>
				<td>${env[key]}</td>
			</tr>
			[/#list]
		</table>	
	[/@]
[/@]
</div>
[@b.foot/]