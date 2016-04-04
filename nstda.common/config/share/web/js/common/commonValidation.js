function formValidation(form){
	
	var containIso = false;
	for(i=0;i<form.elements.length;i++){
	var id = form.elements[i].id;
	//console.log(form.elements[i].name + " : " + form.elements[i].value+ " : " + form.elements[i].id);
		if(id.indexOf('isowf')>-1){
		   containIso = true;
		   break;
		}	
	}
	
	if(form.elements["prop_isowf_approveRejectOutcome"]!=null){
		
		return validateIsoForm(form.elements["prop_isowf_approveRejectOutcome"]);
		
	}else if(containIso && form.elements["prop_isowf_approveRejectOutcome"]==undefined){
		return "ignoreValidate";
	}else if(form.elements["prop_cpwf_approveRejectOutcome"]!=null
			||form.elements["prop_cpwf_approveRejectCancelOutcome"]!=null
			||form.elements["prop_cpwf_reSubmitOutcome"]!=null
			||form.elements["prop_cpwf_completeOutcome"]!=null
			||form.elements["prop_cpwf_completeCancelOutcome"]!=null
			||form.elements["prop_cpwf_approveOutcome"]!=null){
		
		 var v = null;
		 v =validateCarParForm(form.elements["prop_cpwf_approveRejectOutcome"], v);
		 v =validateCarParForm(form.elements["prop_cpwf_approveRejectCancelOutcome"], v);
		 v =validateCarParForm(form.elements["prop_cpwf_reSubmitOutcome"], v);
		 v =validateCarParForm(form.elements["prop_cpwf_completeOutcome"], v);
		 v =validateCarParForm(form.elements["prop_cpwf_completeCancelOutcome"], v);
		 v =validateCarParForm(form.elements["prop_cpwf_approveOutcome"], v);
		 return v;
	}else{
		return null;
	}
	
	
	
}

function validateIsoForm(outcome){
	var isValidate = null;
	if(outcome.value == "Reject" || outcome.value == "Cancel"){
		isValidate = "ignoreValidate";
	}else{
		isValidate = null; //run default validate
	}
	return isValidate;
}

function validateCarParForm(outcome,v){
	
	if(outcome==null || outcome=== undefined) return v;
	var closeResult = Ext.ComponentQuery.query('#closeTask')[0];
	//var v = true;

	if(closeResult!=undefined){
		v = closeResult.validate();
		if(!v){
			Ext.ComponentQuery.query('#closeTask')[0].focus();
		}
	}
	return v;
	//alert('ss');
	

}


