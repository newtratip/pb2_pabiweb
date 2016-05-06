<#if field.value?is_number>
   <#assign displayValue=field.value?string['#,##0.00'] />
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
         <#if field.control.params.format??>
                <#assign displayValue=field.value?datetime("yyyy-MM-dd'T'HH:mm:ss")?string("${field.control.params.format}") />
         <#else>
                <#assign displayValue=field.value?datetime("yyyy-MM-dd'T'HH:mm:ss")?string(msg("form.control.date-picker.view.time.format")) />
         </#if>
      <#else>
         <#assign displayValue=field.value?html />
      </#if>
   </#if>
</#if>

<div class="form-field">
   <div class="viewmode-field">
      <span class="viewmode-label">${field.label?html}:</span>
      <span class="viewmode-value">${displayValue}</span>
   </div>
</div>