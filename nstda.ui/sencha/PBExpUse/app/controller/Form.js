Ext.define('PBExpUse.controller.Form', {
    extend: 'Ext.app.Controller',

	refs:[{
	    ref: 'main',
	    selector: 'expUseMain'
	},{
	    ref: 'mainForm',
	    selector: 'expUseMainForm'
	},{
	    ref: 'mainGrid',
	    selector: 'expUseMainGrid'
	},{
	    ref: 'hidId',     
	    selector: 'expUseMainForm field[name=id]'
	},{
	    ref: 'hidStatus',     
	    selector: 'expUseMainForm field[name=status]'
	},{
	    ref: 'txtReqBy',     
	    selector: 'expUseMainForm field[name=reqBy]'
	},{
	    ref: 'txtReqOu',     
	    selector: 'expUseMainForm field[name=reqOu]'
	},{
	    ref: 'txtObjective',
	    selector: 'expUseMainForm field[name=objective]'
	},{
	    ref: 'txtReason',
	    selector: 'expUseMainForm field[name=reason]'
	},{
	    ref: 'txtNote',
	    selector: 'expUseMainForm field[name=note]'
	},{
	    ref: 'hidBudgetCcType',
	    selector: 'expUseMainForm field[name=budgetCcType]'
	},{
	    ref: 'hidBudgetCc',
	    selector: 'expUseMainForm field[name=budgetCc]'
	},{
	    ref: 'txtBudgetCcTypeName',
	    selector: 'expUseMainForm field[name=budgetCcTypeName]'
    },{
        ref: 'hidFundId',
        selector: 'expUseMainForm field[name=fundId]'
    },{
        ref: 'hidEmotion',
        selector: 'expUseMainForm field[name=emotion]'
	},{
	    ref: 'cmbBank',     
	    selector: 'expUseMainForm field[name=bank]'
	},{
	    ref: 'radBankType',     
	    selector: 'expUseMainForm field[name=bankType]'
	},{
	    ref: 'radBankType0',     
	    selector: 'expUseMainForm field[itemId=bankType0]'
	},{
	    ref: 'radBankType1',     
	    selector: 'expUseMainForm field[itemId=bankType1]'
	},{
	    ref: 'txtTotal',     
	    selector: 'expUseMainForm field[name=total]'
	},{
	    ref: 'hidCostControlTypeId',
	    selector: 'expUseMainForm field[name=costControlTypeId]'
	},{
	    ref: 'hidCostControlId',
	    selector: 'expUseMainForm field[name=costControlId]'
	},{
	    ref: 'radPayType',
	    selector: 'expUseMainForm field[name=payType]'
	},{
	    ref: 'txtSupName',
	    selector: 'expUseMainForm field[name=supName]'
	},{
	    ref: 'txtSupFeeName',
	    selector: 'expUseMainForm field[name=supFeeName]'
	},{
	    ref: 'cmbPoNo',
	    selector: 'expUseMainForm field[name=poNo]'
	},{
	    ref: 'cmbAssetNo',
	    selector: 'expUseMainForm field[name=assetNo]'
	},{
	    ref: 'cmbAvCode',
	    selector: 'expUseMainForm field[name=avCode]'
	},{
	    ref: 'hidIchargeCode',
	    selector: 'expUseMainForm field[name=ichargeCode]'
	},{
	    ref: 'hidIchargeType',
	    selector: 'expUseMainForm field[name=ichargeType]'
	},{
	    ref: 'txtIchargeTypeName',
	    selector: 'expUseMainForm field[name=ichargeTypeName]'
	},{
	    ref: 'txtIchargeName',
	    selector: 'expUseMainForm field[name=ichargeName]'
    },{
        ref: 'cmbVatId',
        selector: 'expUseMainForm field[name=vatId]'
    },{
        ref: 'hidVat',
        selector: 'expUseMainForm field[name=vat]'
	},{
		ref:'fileTab',
		selector:'expUseFileTab'
	},{
		ref:'uploadGrid',
		selector:'expUseFileTab uploadGrid'
	},{
	    ref:'btnApprovalMatrix',     
	    selector: 'expUseMainForm button[action=approvalMatrix]'
	},{
		ref:'attendeeEmpGrid',
		selector:'expUseAttendeeTab #empGrid'
	},{
		ref:'attendeeOthGrid',
		selector:'expUseAttendeeTab #othGrid'
	},{
    	ref:'itemGrid',
    	selector:'expUseItemTab grid'
	},{
    	ref:'itemTab',
    	selector:'expUseItemTab'
	},{
		ref:'infoTab',
		selector:'expUseInfoTab'
	},{
		ref:'userTab',
		selector:'expUseUserTab'
    },{
        ref: 'btnSend',
        selector:'expUseMainForm [action=send]'
    },{
        ref: 'btnAdd',
        selector:'expUseMain [action=add]'
	}],
	
	init:function() {
		var me = this;
		
		me.control({
			'expUseMainForm [action=finish]': {
				click : me.finish
			},
			'expUseMainForm [action=cancelEdit]': {
				click : me.cancelEdit
			},
			'expUseMainForm [action=send]': {
				click : me.send
			},
			'expUseMainForm [action=cancel]': {
				click : me.cancel
			},
			'expUseMainForm [action=saveDraft]': {
				click : me.saveDraft
			},
			'expUseMainForm [action=preview]': {
				click : me.preview
			},
			'expUseMainForm [action=paymentDoc]': {
				click : me.paymentDoc
			},
			'expUseMainForm [action=approvalMatrix]': {
				click : me.showApprovalMatrix
			},
			'expUseUserTab':{
				selectReqBy:me.selectReqBy
			},
			'expUseInfoTab [action=showBudget]':{
				click : me.showBudget
			},
			'expUseInfoTab':{
				selectBudgetCc:me.selectBudgetCc,
				selectMainBank:me.selectMainBank,
				selectPayType:me.selectPayType,
				selectCostControl:me.selectCostControl,
				clearCostControl:me.clearCostControl,
				selectIcharge:me.selectIcharge,
				selectOldAv:me.selectOldAv
			},
			'expUseAttendeeTab':{
//				selectCostControl:me.selectCostControl
			}
		});
	
	},
	
	REPLACE_BUDGET_SRC_MSG_KEY : 'REPLACE_BUDGET_SRC',
	CLEAR_ITEM_MSG_KEY : 'CLEAR_ITEM',
	PREVIEW_MSG_KEY : 'PREVIEW',
	SEND_MSG_KEY : 'SEND_EXP_USE',
	MSG_KEY : 'SAVE_EXP_USE',
	URL : ALF_CONTEXT+'/exp/use',
	AV_URL : ALF_CONTEXT+'/exp/brw',
	MSG_URL : ALF_CONTEXT+'/exp/message',
	BUDGET_URL : ALF_CONTEXT+'/admin/main/sectionProject',
	
	validForm:function(saveDraft) {
		var me = this;
		
		var form = me.getMainForm();
		
		var msg = "";

		if (!saveDraft) {
			var i = 1;
			if(!validForm(form)) {
				var r = me.listInvalidField(form);
				msg = r.msg;
				i = r.i;
			}
		
			if (me.getItemGrid().getStore().getCount() == 0) {
				if (msg) {
					msg += "<br/>";
				}
				
				msg += i+".รายละเอียดเบิกจ่าย";
				i++;
			}
		}
		
		return msg;	
	},
	
	listInvalidField:function(form) {
		var ifield = form.query("field{isValid()==false}");
		var msg = "";
		var i = 1;
		for(var a in ifield) {
			if (msg) {
				msg +="<br/>";
			}
			var lbl = ifield[a].errLabel;
			if (!lbl) {
				lbl = ifield[a].getFieldLabel();
			}
			var pos = lbl.indexOf("<font");
			if(pos<0) {
				pos = lbl.length;
			}
			msg += i+"."+lbl.substring(0,pos);
			i++;
		}

		return {msg:msg,i:i};
	},
	
	send:function() {
		var me = this;
		
		me.getBtnSend().disable();
		
		PB.Util.checkSession(this, me.MSG_URL+"/get", function() {
		
			var msg = me.validForm(false);
			if (!msg) {
				PB.Dlg.confirm('CONFIRM_'+me.SEND_MSG_KEY,me,'doSend', MODULE_EXP, 'enableSendBtn', me);
			} else {
				PB.Dlg.warn('INVALID_INPUT_'+me.MSG_KEY, MODULE_EXP, {msg:msg, scope:me});
				me.enableSendBtn(me);
				return;
			}
		
		});
	},
	
	enableSendBtn:function(me) {
		Ext.defer(function () {
			me.getBtnSend().enable();
	    }, 800);
	},
	
	enableAddBtn:function(me) {
		Ext.defer(function () {
			me.getBtnAdd().enable();
	    }, 800);
	},
	
	doSend: function(){
	
		var me = this;
		
		var grid = me.getMainGrid();
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params;
		
		try {
			params = me.prepareParams();
			} catch (err) {
				console.error(err);
				myMask.hide();
				return;
			}
		
		Ext.Ajax.request({
		    url:me.URL+"/send",
		    method: "POST",
		    params: params,
		    success: function(response){
		  	  
				me.enableSendBtn(me);

				var json = Ext.decode(response.responseText);
				  
			   	if (json.success) {
			   		PB.Dlg.info('SUCC_'+me.SEND_MSG_KEY, MODULE_EXP, {msg:'ID:'+json.data.id, fn:me.closeForm, scope:me});
	   				me.enableAddBtn(me);
			   	} else {
			   		if (json.data != undefined && json.data.valid != undefined && !json.data.valid) {
			   			
			   			var msg;
			   			
			   			if (json.data.msg) {
			   				msg = json.data.msg;
			   			}
			   			else {
			   				msg = "";
			   				
					   		if (json.data.users) {
				   				msg += "Invalid Users ==> " + json.data.users;
					   		}
					   		
					   		if (json.data.groups) {
					   			if (msg) {
					   				msg += "<br/><br/>";
					   			}
					   			
				   				msg += "Invalid Groups ==> " + json.data.groups;
					   		}
					   		
					   		if (msg) {
				   				msg += "<br/><br/>Please check whether group exists and has member.";
					   		}
				   		}
				   		
			   			PB.Dlg.warn(null, MODULE_EXP, {msg:msg});
			   		}
			   		else {
				   		PB.Dlg.error('ERR_'+me.SEND_MSG_KEY, MODULE_EXP,{msg:json.message});
			   		}
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.SEND_MSG_KEY+" ("+json.message+")", MODULE_EXP);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
				me.enableSendBtn(me);
		    },
		    headers: getAlfHeader()
		});
	},
	
	prepareParams : function() {
		var me = this;
		var params = {
			id:me.getHidId().getValue(),
			status:me.getHidStatus().getValue(),
			
			reqBy:me.getTxtReqBy().getValue(),
			reqOu:me.getTxtReqOu().getValue(),
				
			objective:me.getTxtObjective().getValue(),
			reason:me.getTxtReason().getValue(),
			note:me.getTxtNote().getValue(),
			lang:getLang()
		};
		params.budgetCcType = me.getHidBudgetCcType().getValue();
		params.budgetCc = me.getHidBudgetCc().getValue();
		
		params.fundId = me.getHidFundId().getValue();
		
		params.costControlTypeId = me.getHidCostControlTypeId().getValue();
		params.costControlId = me.getHidCostControlId().getValue();
		
		params.bankType = me.getRadBankType().getGroupValue();
		params.bank = me.getCmbBank().getValue();
		
//		params.vatId = me.getCmbVatId().getValue();
//		params.vat = me.getHidVat().getValue();
		
		var payType = me.getRadPayType().getGroupValue();
		params.payType = payType;
		if (payType==1) {
			params.payDtl1 = me.getTxtSupName().getValue();
		} 
		else
//		if (payType==2) {
//			params.payDtl1 = me.getTxtSupFeeName().getValue();
//			params.payDtl2 = me.getCmbPoNo().getValue();
//			params.payDtl3 = me.getCmbAssetNo().getValue();
//		} 
//		else
		if (payType==2) {
			params.payDtl1 = me.getCmbAvCode().getValue();
		}
		else
		if (payType==3) {
			params.payDtl1 = me.getHidIchargeCode().getValue();
			params.payDtl2 = me.getHidIchargeType().getValue();
		}
		
		params.total = me.getTxtTotal().getValue();
		
		params.attendees = me.getAttendees();
		params.items = me.getItems();
		params.files = me.listFiles();
		
		return params;
	},
	
	cancel:function() {
		PB.Dlg.confirm('CONFIRM_CANCEL_EXP_USE',this,'closeForm', MODULE_EXP);
	},
	
	closeForm:function() {
		this.getMainForm().destroy();
		this.refreshGrid();
		this.enableAddBtn(this);
	},
	
	refreshGrid:function() {
		this.getMainGrid().getStore().load();
	},
	
	saveDraft:function() {
		var me = this;
		
		PB.Util.checkSession(this, me.MSG_URL+"/get", function() {
	
//			var msg = me.validForm(true);
//			if (msg) {
//				PB.Dlg.warn('INVALID_INPUT_'+me.MSG_KEY, MODULE_EXP, {msg:msg});
//				return;
//			}
	
			var grid = me.getMainGrid();
			
			var myMask = new Ext.LoadMask({
				target:me.getMain(), 
				msg:"Please wait..."
			});
			
			myMask.show();
			
			var params;
			try {
				params = me.prepareParams();
			} catch (err) {
				console.error(err);
				myMask.hide();
				return;
			}
			
			Ext.Ajax.request({
			    url:me.URL+"/save",
			    method: "POST",
			    params: params,
			    success: function(response){
				
				  	var json = Ext.decode(response.responseText);
					  
				   	if (json.success) {
				   		var id = json.data.id;
				   		me.getHidId().setValue(id);
				   		me.getHidStatus().setValue(json.data.status);
				   		me.getMainForm().setTitle(PB.Label.m.edit+' : <font color="red">'+id+"</font>");
				   		
				   		var fileStore = me.getUploadGrid().getStore(); 
						fileStore.getProxy().api.read = ALF_CONTEXT + "/exp/use/file/list";
						fileStore.getProxy().extraParams = {
						    id:id
						}
						fileStore.load();
				   		
				   		me.refreshGrid();
				   		PB.Dlg.info('SUCC_'+me.MSG_KEY, MODULE_EXP, {msg:'ID:'+id, scope:me});
				   	} else {
				   		PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_EXP,{msg:json.message});
				   	}
				   	 
			   	 	myMask.hide();
			
			    },
			    failure: function(response, opts){
			    	try {
			    		var json = Ext.decode(response.responseText);
				    	PB.Dlg.error('ERR_'+me.MSG_KEY+" ("+json.message+")", MODULE_EXP);
			    	}
			    	catch (err) {
				    	alert(response.responseText);
			    	}
				    myMask.hide();
			    },
			    headers: getAlfHeader()
			});
		
		});
	},
	
	getHSaveFieldValue:function(saveField, req) {
		var field = this.getMainForm().down("field[hSaveField="+saveField+"]");
		var v;
		if(field) {
			v = field.getValue();
		}
		else {
			if (req) {
				alert("Field Not Found : "+saveField);
				throw "fnf";
			}
		}
		return v;
	},
	
	getAttendees:function() {
	
		var me = this;
	
		var data = [];
		me.getAttendeeEmpGrid().getStore().each(function(rec){
		   rec.data.type = "E";
		   data.push(rec.data);
		});
		
		me.getAttendeeOthGrid().getStore().each(function(rec){
		   rec.data.type = "O";
		   data.push(rec.data);
		});
		
		return Ext.JSON.encode(data);
	},
	
	getItems:function() {
	
		var me = this;
	
		var data = [];
		me.getItemGrid().getStore().each(function(rec){
		   if (!rec.data.condition1) {
			   rec.data.condition1 = "";
		   }
		   
		   console.log("sp:"+rec.data.specialWorkflow);
		   
		   data.push(rec.data);
		});
		
		return Ext.JSON.encode(data);
	},
	
	listFiles:function() {
		var values = [];
	
		var store = this.getFileTab().down("uploadGrid").getStore();
		
		store.each(function(rec){
			values.push({
				name:rec.get("name"),
				desc:rec.get("desc"),
				path:rec.get("path"),
				nodeRef:rec.get("nodeRef")
			});
		});
	
		return Ext.JSON.encode(values);
	},
	
	activateRptTab:function() {
		this.getMainForm().down("tabpanel").getLayout().setActiveItem(this.getRptTab());
	},
	
	finish:function() {
		var me = this;
		
		var msg = me.validForm(false);
		if (!msg) {
			PB.Dlg.confirm('CONFIRM_'+this.SEND_MSG_KEY,this,'doFinish', MODULE_EXP);
		} else {
			PB.Dlg.warn('INVALID_INPUT_'+this.MSG_KEY, MODULE_EXP, {msg:msg});
			return;
		}
	},
	
	doFinish: function(){
		var me = this;
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params;
		
		try {
			params = me.prepareParams();
			} catch (err) {
				myMask.hide();
				return;
			}
		
		Ext.Ajax.request({
		    url:me.URL+"/finish",
		    method: "POST",
		    params: params,
		    success: function(response){
		  	  
			  	var json = Ext.decode(response.responseText);
				  
			   	if (json.success) {
			   		 
			   		PB.Dlg.info('SUCC_'+me.MSG_KEY, MODULE_EXP, {msg:'ID:'+json.data.id, fn:me.backToWfForm, scope:me});
			   		
			   	} else {
			   		if (json.data != undefined && json.data.valid != undefined && !json.data.valid) {
			   			
						var msg;
						
						if (json.data.msg) {
							msg = json.data.msg;
						}
						else {
							msg = "";
							
							if (json.data.users) {
								msg += "Invalid Users ==> " + json.data.users;
							}
							
							if (json.data.groups) {
								if (msg) {
									msg += "<br/><br/>";
								}
								
								msg += "Invalid Groups ==> " + json.data.groups;
							}
							
							if (msg) {
								msg += "<br/><br/>Please check whether group exists and has member.";
							}
						}
						
						PB.Dlg.warn(null, MODULE_EXP, {msg:msg});
					}
					else {
				   		PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_EXP,{msg:json.message});
					}
	
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.MSG_KEY+" ("+json.message+")", MODULE_EXP);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
		    },
		    headers: getAlfHeader()
		});
	},
	
	cancelEdit:function() {
		PB.Dlg.confirm('CONFIRM_CANCEL_EXP_USE',this,'backToWfForm', MODULE_EXP);
	},
	
	backToWfForm:function() {
		window.location = appContext+"/page/task-edit?taskId="+TASK_ID+"&referrer=tasks";
	},
	
	showApprovalMatrix:function() {
		var me = this;
	
		var dlg = Ext.create("PBExpUse.view.approvalMatrix.DtlDlg");
		var id = me.getBtnApprovalMatrix().fid;
	
		var store = dlg.items.items[0].getStore(); 
		store.getProxy().extraParams = {
			id : id
		}
		store.load();
		
		dlg.show();
	},
	
	preview:function() {
		
		var me = this;
		
		PB.Util.checkSession(this, me.MSG_URL+"/get", function() {

			var msg = me.validForm(false);
			if (msg) {
				PB.Dlg.warn('INVALID_INPUT_'+me.MSG_KEY, MODULE_EXP, {msg:msg});
				return;
			}
			
			var grid = me.getMainGrid();
			
			var myMask = new Ext.LoadMask({
				target:me.getMain(), 
				msg:"Please wait..."
			});
			
			myMask.show();
			
			var params;
			
			try {
				params = me.prepareParams();
			} catch (err) {
				console.log(err);
				myMask.hide();
				return;
			}
				
			Ext.Ajax.request({
			    url:me.URL+"/preview",
			    method: "POST",
			    params: params,
			    success: function(response){
			  	  
				  	var json = Ext.decode(response.responseText);
					  
				   	if (json.success) {
						window.open(me.URL+"/preview?id="+json.data[0].id,"_blank");
				   	} else {
				   		PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY, MODULE_EXP,{msg:json.message});
				   	}
				   	 
			   	 	myMask.hide();
			
			    },
			    failure: function(response, opts){
			    	try {
			    		var json = Ext.decode(response.responseText);
				    	PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY+" ("+json.message+")", MODULE_EXP);
			    	}
			    	catch (err) {
				    	alert(response.responseText);
			    	}
				    myMask.hide();
			    },
			    headers: getAlfHeader()
			});
		
		});
	
	},
	
	selectBudgetCcCallBack:function(ids, type, typeName, fund, fundName) {
		var me = this.sc;
		var tab = this.targetPanel;
		
		me.targetPanel = this.targetPanel;
		me.ids = ids;
		me.type = type;
		me.typeName = typeName;
		me.fund = fund;
		me.fundName = fundName;
		
		if (tab.up("panel").down("grid").getStore().getCount() > 0) {
			PB.Dlg.confirm('CONFIRM_'+me.CLEAR_ITEM_MSG_KEY,me,'clearItem', MODULE_EXP,null,me);
		} else {
			me.updateBudgetCc(me);
		}
	},
	
	updateBudgetCc:function(sc) {
		var tab = sc.targetPanel;
		setValue(tab, 'budgetCc', sc.ids[0]);
		setValue(tab, 'budgetCcName', sc.ids[1]);
		setValue(tab, 'budgetCcType', sc.type);
		setValue(tab, 'budgetCcTypeName', sc.typeName);
		
		setValue(tab, 'fundId', sc.fund);
		setValue(tab, 'fundName', sc.fundName);
		
		setValue(tab, 'emotion', sc.ids[2] ? sc.ids[2] : "");
		
		tab.down("button[action='showBudget']").show();
	},
	
	clearItem:function(sc) {
		this.updateBudgetCc(sc);
		this.getItemGrid().getStore().removeAll();
	},
	
	selectBudgetCc:function() {
		var me = this;
	
		var dlg = Ext.create("PB.view.common.SearchBudgetSrcDlg",{
			title:PB.Label.m.search,
			targetPanel:this.getInfoTab(),
			callback:this.selectBudgetCcCallBack,
			sc:me
		});
		dlg.show();
	},
	
	selectIchargeCallBack:function(ids, type, typeName) {
		var tab = this.targetPanel;
		setValue(tab, 'ichargeCode', ids[0]);
		setValue(tab, 'ichargeName', ids[1]);
		setValue(tab, 'ichargeType', type);
		setValue(tab, 'ichargeTypeName', typeName);
	},
	
	selectIcharge:function() {
		var me = this;
	
		var dlg = Ext.create("PB.view.common.SearchBudgetSrcDlg",{
			title:'ค้นหา',
			targetPanel:this.getInfoTab(),
			callback:this.selectIchargeCallBack,
			onlySectProj:true
		});
		dlg.show();
	},
	
	selectReqByCallBack:function(id, rec) {
		var tab = this.targetPanel;
		setValue(tab, 'reqBy', rec.get('code'));
		setValue(tab, 'reqByName', rec.get('fname') + ' ' + rec.get('lname'));
		
		var mphone = rec.get("mphone")!=null ? rec.get("mphone") : "";
		var wphone = rec.get("wphone")!=null ? rec.get("wphone") : "";
		var comma = (mphone!="" && wphone!="") ? "," : "";
		
		setValue(tab, 'reqTelNo', wphone+comma+mphone);
		
		setValue(tab, 'reqByDept', rec.get('position'));
		setValue(tab, 'reqBu', rec.get('org_name'));
		setValue(tab, 'reqOuName', rec.get('utype'));
		
		var cmbOldAV = tab.up("tabpanel").down("combo[name=avCode]");
		var store = cmbOldAV.getStore();
		store.load({
			params:{r:rec.get('code')}
		});

	},	
	
	selectReqBy:function() {
		var me = this;
	
		var dlg = Ext.create("PB.view.common.SearchUserDlg",{
			title:'ค้นหา',
			targetPanel:this.getUserTab(),
			callback:this.selectReqByCallBack
		});
		dlg.show();
	},
	
	selectMainBank:function(rad,v) {
		var me = this;
		
		var vi = parseInt(v);
		
		var i=0;
		var b = vi!=1; 
		me.getCmbBank().setDisabled(b);
		if (b) {
			me.getCmbBank().setValue(null);
		}
	},
	
	selectCostControlCallBack:function(id, name, type, typeName) {
		var tab = this.targetPanel;
		setValue(tab, 'costControlId', id);
		setValue(tab, 'costControlName', name);
		setValue(tab, 'costControlTypeId', type);
		setValue(tab, 'costControlTypeName', typeName);
	},
	
	selectCostControl:function() {
		Ext.create("PB.view.common.SearchCostControlDlg",{
			title:PB.Label.m.search,
			targetPanel:this.getInfoTab(),
			sectionId:this.getInfoTab().down("field[name=budgetCc]").getValue(),
			callback:this.selectCostControlCallBack
		}).show();
	},
	
	clearCostControl:function() {
		var tab = this.getInfoTab();
		setValue(tab, 'costControlId', null);
		setValue(tab, 'costControlName', '');
		setValue(tab, 'costControlTypeId', null);
		setValue(tab, 'costControlTypeName', '');
	},
	
//	selectCostControl:function(rad,v) {
//		var me = this;
//		
//		var vi = parseInt(v);
//		
//		var i=0;
//		var b = vi!=i;
//		me.getCmbCostControlId().setDisabled(b);
//		me.getTxtCc1From().setDisabled(b);
//		me.getTxtCc1To().setDisabled(b);
//		if (b) {
//			me.getCmbCostControlId().setValue(null);
//			me.getTxtCc1From().setValue(null);
//			me.getTxtCc1To().setValue(null);
//		}
//		
//		i++;
//		b = vi!=i;
//		me.getTxtCostControl().setDisabled(b);
//		me.getTxtCc2From().setDisabled(b);
//		me.getTxtCc2To().setDisabled(b);
//		if (b) {
//			me.getTxtCostControl().setValue(null);
//			me.getTxtCc2From().setValue(null);
//			me.getTxtCc2To().setValue(null);
//		}
//	},
	
	selectPayType:function(rad,v) {
		var me = this;
		
		me.rad = rad;
		me.v = v;
		
		if (me.getInfoTab().oldPayType && me.getItemGrid()) {
			if (me.getItemGrid().getStore().getCount() > 0) {
				PB.Dlg.confirm('CONFIRM_'+me.CLEAR_ITEM_MSG_KEY,me,'clearItemForPayType', MODULE_EXP, 'restorePayType', me);
			} else {
				me.updatePayType(me);
			}
		} else {
			me.updatePayType(me);
		}

	},
	
	restorePayType:function() {
//		console.log("oldPayType:"+this.getInfoTab().oldPayType);
		var oldPayType = this.getInfoTab().oldPayType; 
		this.getInfoTab().oldPayType = null;
		this.getInfoTab().down("radio[inputValue="+oldPayType+"]").setValue(true);
	},
	
	updatePayType:function(sc) {
		var me = sc;
		
		var rad = me.rad;
		var v = me.v;
		
		me.getTxtBudgetCcTypeName().setDisabled(sc.v == "2");
		
		this.getInfoTab().oldPayType = v;
		
		var vi = parseInt(v);
	//	console.log(vi);
		
		me.getRadBankType0().setDisabled(vi!=0 && vi!=2);
		me.getRadBankType1().setDisabled(me.getRadBankType0().disabled);
		if (me.getRadBankType0().disabled) {
			me.getRadBankType0().setValue(false);
			me.getCmbBank().setValue(null);
			me.getRadBankType1().setValue(false);
		} else {
			if(!me.getRadBankType0().getValue() && !me.getRadBankType1().getValue()) {
				me.getRadBankType0().setValue(true);
			}
		}
		
	//	console.log("v="+me.getRadBankType0().disabled +","+ me.getRadBankType0().getValue() +","+ !me.getRadBankType1().getValue());
		me.getCmbBank().setDisabled(me.getRadBankType1().disabled || (me.getRadBankType0().getValue() && !me.getRadBankType1().getValue()));
		
		var i=1;
		b = vi!=i;
		me.getTxtSupName().setDisabled(b);
		if (b) {
			me.getTxtSupName().setValue(null);
		}
		
	//	i++;
	//	b = vi!=i;
	//	me.getTxtSupFeeName().setDisabled(b);
	////	me.getCmbPoNo().setDisabled(b);
	////	me.getCmbAssetNo().setDisabled(b);
	//	if (b) {
	//		me.getTxtSupFeeName().setValue(null);
	//		me.getCmbPoNo().setValue(null);
	//		me.getCmbAssetNo().setValue(null);
	//	}
		
		i++;
		b = vi!=i;
		me.getCmbAvCode().setDisabled(b);
		if (b) {
			me.getCmbAvCode().setValue(null);
		}
		
		i++;
		b = vi!=i;
		me.getTxtIchargeTypeName().setDisabled(b);
		if (b) {
			me.getTxtIchargeName().setValue(null);
			me.getTxtIchargeTypeName().setValue(null);
		}
		
	},
	
	clearItemForPayType:function(sc) {
		this.updatePayType(sc);
		this.getItemGrid().getStore().removeAll();
	},
	
	showBudget:function() {
		var me = this;
		
		Ext.Ajax.request({
		    url:ALF_CONTEXT+"/admin/main/totalPreBudget",
		    method: "GET",
		    params: {
				budgetCc:me.getHidBudgetCc().getValue(),
				budgetCcType:me.getHidBudgetCcType().getValue(),
				fundId:me.getHidFundId().getValue()
			},
		    async:false,
		    success: function(response){
			
				var json = Ext.decode(response.responseText);
				
				var tab = me.getInfoTab();
				var rec = {
						name : tab.down("field[name=budgetCcTypeName]").getValue()+" "+tab.down("field[name=budgetCcName]").getValue()+" ("+tab.down("field[name=fundName]").getValue()+")",
						balance : json.data.balance,
						preAppBudget : json.data.pre,
						expBalance : json.data.ebalance
				}
			
				Ext.create("PB.view.common.CheckBudgetDlg",{
					title:'งบประมาณ',
					rec:rec
				}).show();
		    }
		});		
	},
	
	paymentDoc:function() {
		var me = this;

		var msg = me.validForm(false);
		if (msg) {
			PB.Dlg.warn('INVALID_INPUT_'+me.MSG_KEY, MODULE_EXP, {msg:msg});
			return;
		}
		
		var grid = me.getMainGrid();
		
		var myMask = new Ext.LoadMask({
			target:me.getMain(), 
			msg:"Please wait..."
		});
		
		myMask.show();
		
		var params;
		
		try {
			params = me.prepareParams();
		} catch (err) {
			console.log(err);
			myMask.hide();
			return;
		}
			
		Ext.Ajax.request({
		    url:me.URL+"/paymentDoc",
		    method: "POST",
		    params: params,
		    success: function(response){
		  	  
			  	var json = Ext.decode(response.responseText);
				  
			   	if (json.success) {
					window.open(me.URL+"/paymentDoc?id="+json.data[0].id,"_blank");
			   	} else {
			   		PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY, MODULE_EXP);
			   	}
			   	 
		   	 	myMask.hide();
		
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.PREVIEW_MSG_KEY+" ("+json.message+")", MODULE_EXP);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
			    myMask.hide();
		    },
		    headers: getAlfHeader()
		});
	},
	
	selectOldAv:function(cmb, newV, oldV) {
		if (newV) {
			var me = this;
			
//			newV = "AV17001032";
			
			var estore = me.getAttendeeEmpGrid().getStore();
			estore.getProxy().api.read = ALF_CONTEXT+'/exp/brw/attendee/list';
			estore.getProxy().extraParams = {
				id:newV,
				type:'E',
				lang:getLang()
			}
			estore.load();
			
			var ostore = me.getAttendeeOthGrid().getStore();
			ostore.getProxy().api.read = ALF_CONTEXT+'/exp/brw/attendee/list';
			ostore.getProxy().extraParams = {
				id:newV,
				type:'O',
				lang:getLang()
			}
			ostore.load();

			var istore = me.getItemGrid().getStore();
			istore.getProxy().api.read = ALF_CONTEXT+'/exp/brw/item/list';
			istore.getProxy().extraParams = {
				id:newV
			}
			istore.load({
				callback:function() {
					me.getItemTab().fireEvent("itemStoreLoad");
				}
			});
			
			var params = {
				id:newV,
				lang:getLang()
			}
			
			Ext.Ajax.request({
			    url:me.AV_URL+"/get",
			    method: "GET",
			    params: params,
			    success: function(response){
			  	  
				  	var json = Ext.decode(response.responseText);
					
				  	if (json.success) {
				  		if (json.data.length > 0) {
						  	var data = json.data[0];
						  	me.getTxtReason().setValue(data.reason);
						  	
						  	me.getTxtBudgetCcTypeName().setDisabled(true);
						  	
						  	if (!me.getHidBudgetCcType().getValue()) {
						  		me.doReplaceBudgetSrcYes(data);
						  	}
						  	else
						  	if ((me.getHidBudgetCcType().getValue() != data.budget_cc_type) 
						  		|| (me.getHidBudgetCc().getValue() != data.budget_cc)) {
						  		PB.Dlg.confirm('CONFIRM_'+me.REPLACE_BUDGET_SRC_MSG_KEY,me,'doReplaceBudgetSrcYes', MODULE_EXP, 'doReplaceBudgetSrcNo',data);
						  	}
					  	}
				  	}
			    },
			    failure: function(response, opts){
			    	try {
			    		var json = Ext.decode(response.responseText);
				    	PB.Dlg.error('ERR_'+me.SEND_MSG_KEY+" ("+json.message+")", MODULE_EXP);
			    	}
			    	catch (err) {
				    	alert(response.responseText);
			    	}
				    myMask.hide();
			    },
			    headers: getAlfHeader()
			});
		}
	},
	
	doReplaceBudgetSrcYes:function(data) {
		var me = this;
		me.getHidBudgetCcType().setValue(data.budget_cc_type);
		me.getHidBudgetCc().setValue(data.budget_cc);
		
		var params = {
			v:data.budget_cc_type+','+data.budget_cc,
			lang:getLang()
		}

		Ext.Ajax.request({
		    url:me.BUDGET_URL+"/getDtl",
		    method: "GET",
		    params: params,
		    success: function(response){
			  	var json = Ext.decode(response.responseText);
				
			  	var dat = json.data;
			  	var tab = me.getInfoTab();
			  	
				setValue(tab, 'budgetCcName', dat.budget_name);
				setValue(tab, 'budgetCcTypeName', dat.budget_type_name);
				
				setValue(tab, 'fundId', dat.fund_id);
				setValue(tab, 'fundName', dat.fund_name);
				
				setValue(tab, 'emotion', "");
				
				tab.down("button[action='showBudget']").show();
				
				//me.getItemGrid().getStore().removeAll();
		    },
		    failure: function(response, opts){
		    	try {
		    		var json = Ext.decode(response.responseText);
			    	PB.Dlg.error('ERR_'+me.SEND_MSG_KEY+" ("+json.message+")", MODULE_EXP);
		    	}
		    	catch (err) {
			    	alert(response.responseText);
		    	}
		    },
		    headers: getAlfHeader()
		});
	},
	
	doReplaceBudgetSrcNo:function() {
		var me = this;
		me.getCmbAvCode().setValue(null);
	}
	
});
