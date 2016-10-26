<#if field.control.params.dsUrl?exists><#assign dsUrl=field.control.params.dsUrl><#else><#assign dsUrl=''></#if>

<#if field.value == "">
   <#assign displayValue=msg("form.control.novalue") />
<#else>   
   <#assign displayValue=field.value?html />
</#if>

<div class="form-field">
   <div class="viewmode-field">
      <span class="viewmode-label">${field.label?html}:</span>
      <span class="viewmode-value" id="${fieldHtmlId}-l">${displayValue}</span>
   </div>
</div>

<script type="text/javascript">
	YAHOO.util.Event.onDOMReady(function(){
		 var url = Alfresco.constants.PROXY_URI_RELATIVE+"${dsUrl}"+"${displayValue}"+"&lang="+getLang();
         var myCallback = {
           success: function(o) {var r=JSON.parse(o.responseText);if(r.data){
           		document.getElementById("${fieldHtmlId}-l").innerHTML = r.data[0].name;
    	   }},
           failure: function(o) {alert("failure")},
         };
         var transaction = YAHOO.util.Connect.asyncRequest('GET', url, myCallback);
	});
</script>