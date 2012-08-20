[#ftl]
[#macro enableInfo enabled]
[#if enabled]<span class="toolbar-icon action-activate"></span>${b.text("action.activate")}[#else]<span class="toolbar-icon action-freeze"></span><em>${b.text("action.freeze")}</em>[/#if]
[/#macro]

[#macro shortEnableInfo enabled]
[#if enabled]<span class="toolbar-icon action-activate"></span[#else]<span class="toolbar-icon action-freeze"></span>[/#if]
[/#macro]