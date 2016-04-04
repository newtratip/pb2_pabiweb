<@markup id="css" >
   <#-- CSS Dependencies -->
	<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/ext/resources/css/ext-all-gray.css" />
	<@link rel="stylesheet" type="text/css" href="${page.url.context}/res/css/icon.css" />
</@>

<@markup id="js">
   <#-- JavaScript Dependencies -->
   	<script type="text/javascript">
		var TASKS = ''
			,CURRENT_SITE = '';
	</script>
	<@script type="text/javascript" src="${page.url.context}/res/page/common/constant.js"></@script>
	
	<@script type="text/javascript" src="${page.url.context}/res/page/common.js"></@script>
	<@script type="text/javascript" src="${page.url.context}/res/page/common/util.js"></@script>
	<@script type="text/javascript" src="${page.url.context}/res/page/common/messageUtil.js"></@script>
	<@script type="text/javascript" src="${page.url.context}/res/page/common/viewDetail.js"></@script>
	<@script type="text/javascript" src="${page.url.context}/res/page/murakami/a.js"></@script>

</@>

<#if field.value?is_number>
   <#assign displayValue=field.value?c />
<#elseif field.value?is_boolean>
   <#if field.value>
      <#assign displayValue=msg("form.control.checkbox.yes") />
   <#else>
      <#assign displayValue=msg("form.control.checkbox.no") />
   </#if>
<#else>
   <#if field.value == "">
      <#assign displayValue=msg("form.control.novalue") />
   <#else>   
      <#if field.dataType == "date">
         <#assign displayValue=field.value?datetime("yyyy-MM-dd'T'HH:mm:ss")?string(msg("form.control.date-picker.view.date.format")) />
      <#elseif field.dataType == "datetime">
         <#assign displayValue=field.value?datetime("yyyy-MM-dd'T'HH:mm:ss")?string(msg("form.control.date-picker.view.time.format")) />
      <#else>
         <#assign displayValue=field.value?html />
      </#if>
   </#if>
</#if>

<div class="form-field">
   
     <label onclick="viewDetail(false)" style="cursor:pointer">View Full Details</label>
   
</div>

