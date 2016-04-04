(function(){var f=YAHOO.util.Dom,j=YAHOO.util.Event;var d=Alfresco.util.encodeHTML;Alfresco.ActivitiTransitions=function(k){Alfresco.ActivitiTransitions.superclass.constructor.call(this,"Alfresco.ActivitiTransitions",k,["button","container"]);return this};YAHOO.extend(Alfresco.ActivitiTransitions,Alfresco.component.Base,{options:{currentValue:"",hiddenFieldName:"",transitions:null},onReady:function a(){this._processTransitions();this._generateTransitionButtons()}
,onClick:function c(n,o){
if (!o.valid()) {
	return;
};
o.set("disabled",true);var m=o.get("id");var l=m.substring(this.id.length+1);var k=this._getHiddenField();f.setAttribute(k,"value",l);if(Alfresco.logger.isDebugEnabled()){Alfresco.logger.debug("Set transitions hidden field to: "+l)}this._generateTransitionsHiddenField();Alfresco.util.submitForm(o.getForm())},_processTransitions:function i(){this.options.transitions=[];if(Alfresco.logger.isDebugEnabled()){Alfresco.logger.debug("Processing transitions for field '"+this.id+"': "+this.options.currentValue)}if(this.options.currentValue!==null&&this.options.currentValue.length>0){var n=this.options.currentValue.split("#alf#");for(var l=0,m=n.length;l<m;l++){var k=n[l].split("|");this.options.transitions.push({id:k[0],label:k[1]})}}if(Alfresco.logger.isDebugEnabled()){Alfresco.logger.debug("Built transitions list: "+YAHOO.lang.dump(this.options.transitions))}},_generateTransitionButtons:function g(){for(var k=0,l=this.options.transitions.length;k<l;k++){this._generateTransitionButton(this.options.transitions[k])}},_generateTransitionButton:function e(l){var k=document.createElement("input");k.setAttribute("id",this.id+"-"+l.id);k.setAttribute("value",l.label);k.setAttribute("type","button");f.get(this.id+"-buttons").appendChild(k);var k=Alfresco.util.createYUIButton(this,l.id,this.onClick);
if(l.id=="Approve"){k.valid=this.validA}else{k.valid=this.validR};
f.get(this.id+"-"+l.id+"-button").setAttribute("name",l.id);YAHOO.Bubbling.fire("addSubmitElement",k)}
,validA:function() {
	var p=document.getElementsByName("prop_isowf_docNumber");
	var q=document.getElementsByName("-");
	
	var r,s;
	for(i=0; i<q.length; i++) {
		if (q[i].tagName=="INPUT" && q[i].id.indexOf("effectiveDate")>=0){
			r = q[i];
		}
		else
		if (q[i].tagName=="INPUT" && q[i].id.indexOf("isowf_publishDocument")>=0){
			s = q[i];
		}
	}
	
	if ((p.length>0 && p[0].value=="") || (r!=null && r.value=="") || (s!=null && s.value=="")) {
	  alert("Please fill in every mandatory fields");
	  return false;
    }
    return true;}
,validR:function() {return true;}
,_getHiddenField:function b(){var k=f.get(this.id+"-hidden");if(k===null){k=document.createElement("input");k.setAttribute("id",this.id+"-hidden");k.setAttribute("type","hidden");k.setAttribute("name",this.options.hiddenFieldName);f.get(this.id).appendChild(k)}return k},_generateTransitionsHiddenField:function h(){var k=f.get(this.id+"-transitions-hidden");if(k===null){k=document.createElement("input");k.setAttribute("id",this.id+"-transitions-hidden");k.setAttribute("type","hidden");k.setAttribute("name","prop_transitions");k.setAttribute("value","Next");f.get(this.id).appendChild(k)}}})})();