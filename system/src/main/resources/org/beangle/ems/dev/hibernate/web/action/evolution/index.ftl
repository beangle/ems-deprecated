[#ftl]
[@b.head/]
<h2>欢迎${databaseName}使用数据库升级程序</h2>
[@b.form action="!index"]
	[@b.textfield name="version"  label="输入版本号进行过滤" value=Parameters['version']! /]
	[@b.submit value="action.submit"/]
[/@]
<p>可以升级执行的sql文件如下</p>
[#list resources?sort as resource]
<li>${resource_index+1} [@b.a href="!display?sqlfile=${resource.URL?url('utf-8')}"]${resource.URL}[/@]</li>
[/#list]
[@b.foot/]