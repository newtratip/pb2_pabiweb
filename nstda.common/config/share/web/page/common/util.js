function getActionIcon(v, a, i) { 
	return v.indexOf(a) < 0 ? HIDE_CSS : 'icon_'+i
}

function getAlfHeader() { 
	return { 'Alfresco-CSRFToken': Ext.util.Cookies.get(ALF_TOKEN) }
}

function validForm(form){
	
	if(form.getForm().isValid()){
		return true;
	}else{
		return false;
	}
	
}

function validateFormAdmin(form) {
	
	var fields = form.getForm().getFields();
	
	var fieldCount = fields.getCount();
	
	var valid = true;
	
	for(var index=0; index<fieldCount; index++){
		field = fields.get(index);
		if(field.getXType() == "datefield"){
			value = field.getRawValue();
		}
		else{
			value = field.getValue();
		}
		
		if (SEARCH_RE_ADMIN.test(value)) {
			valid = false;
	
			if(field.getXType()=="combobox" || field.getXType()=="combo"){
				field.reset();
			}
			
			break;
		}
		
	}
	
	return valid;	
}

Date.prototype.format = function(format) //author: meizz
{
  var o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(),    //day
    "h+" : this.getHours(),   //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
    "S" : this.getMilliseconds() //millisecond
  }

  if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
      RegExp.$1.length==1 ? o[k] :
        ("00"+ o[k]).substr((""+ o[k]).length));
  return format;
}

function loadingStore(store,fn){
	if(store!=null&&store!=undefined){
		var callback ;
		if(fn!=null && fn!='' && fn!="" && fn!=undefined){
			callback = {
					callback : fn
			}
		}
		
		store.load(callback);
	}
}

function htmlToLineSeparator(value){
	if(value!=null&&value!=undefined){
		
		while(value.indexOf("<br/>") != -1){
			value = value.replace("<br/>","\n");
		}
		
		return value;
	}
}

function compareDate(from,to){
	
	var dateFrom = new Date(from.getValue());
	var dateTo = new Date(to.getValue());
	
    if(to.getValue() == null){
		
		to.setMinValue(dateFrom);
		to.validate();
		
	}else{
		
		to.setMinValue(dateFrom);
		to.validate();
		
	}
	
}

function compareTime(from,to){
	
	var timeFrom = Ext.Date.format(from.getValue(), 'H:i A');
	var timeTo = Ext.Date.format(to.getValue(), 'H:i A');
	
	var min =  parseInt(Ext.Date.format(from.getValue(), 'i'));
	var hour =  parseInt(Ext.Date.format(from.getValue(), 'H'));
	var duration = Ext.Date.format(from.getValue(), 'A');
	var increase = parseInt(MEETING_INCREMENT_TIME);
	
	if(min == 0){
		min = min + increase;
	}else if((min+increase) >= 60){
		min = 0;
		hour = hour+=1;
	}else{
		min = min + increase;
	}
	
	if(hour < 10){
		hour = '0'+hour;
	}
	if(min < 10){
		min = '0'+min;
	}
	
	var minDate = Ext.String.format('{0}:{1} {2}',hour, min, duration);

    if(to.getValue() == null){
		
		to.setMinValue(minDate);
		to.validate();
		
	}else{
		
		to.setMinValue(minDate);
		to.validate();
		
	}
	

}

function getLanguage() {
	var req = new XMLHttpRequest();
	req.open('GET', document.location, false);
	req.send(null);
	var headers = req.getAllResponseHeaders().toLowerCase();
	var contentLanguage = headers.match( /^content-language\:(.*)$/gm );
	if(contentLanguage[0]) {
	    return contentLanguage[0].split(":")[1].trim().toUpperCase();
	}	    
}