<@markup id="js">
   <#-- JavaScript Dependencies -->
	<@script type="text/javascript" src="${url.context}/res/js/common/combobox.js"></@script>
</@>
<#-- Renders a hidden form field for edit and create modes only -->
<#assign fieldValue = "">
<#if field.control.params.contextProperty??>
   <#if context.properties[field.control.params.contextProperty]??>
      <#assign fieldValue = context.properties[field.control.params.contextProperty]>
   <#elseif args[field.control.params.contextProperty]??>
      <#assign fieldValue = args[field.control.params.contextProperty]>
   </#if>
<#elseif context.properties[field.name]??>
   <#assign fieldValue = context.properties[field.name]>
<#else>
   <#assign fieldValue = field.value>
</#if>

<#if form.mode == "edit" || form.mode == "create">
	<div class="form-field">
		 
		 	 <div id="${field.control.params.fieldName}"></div>
		
	</div>
   <input type="hidden" name="${field.name}" 
          <#if field.value?is_number>value="${fieldValue?c}"<#else>value="${fieldValue?html}"</#if> />
</#if>



<script type="text/javascript">

	YAHOO.util.Event.onDOMReady(function(){

		renderCombobox("${field.control.params.fieldName}","${fieldValue?html}","${field.name}","${field.control.params.readOnly}","${field.control.params.url}","${field.control.params.type}","${field.control.params.label}","${field.control.params.mandatory}");	

		
	});

</script>
