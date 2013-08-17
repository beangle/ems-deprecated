[#ftl]
<h2>欢迎${databaseName}使用数据库升级程序</h2>
<p>可以升级执行的sql文件如下</p>
[#list resources?sort as resource]
<li>${resource_index+1} [@b.a href="!display?sqlfile=${resource.URL?url('utf-8')}"]${resource.URL}[/@]</li>
[/#list]