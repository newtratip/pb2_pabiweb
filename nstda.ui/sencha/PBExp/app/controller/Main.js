Ext.define('PBExp.controller.Main', {
    extend: 'Ext.app.Controller',
    
	refs:[{
	    ref: 'main',
	    selector:'expBrwMain'
	},{
	    ref: 'mainGrid',
	    selector:'expBrwMainGrid'
	},{
		ref:'txtSearch',
		selector:'expBrwMainGrid [itemId=txtSearch]'
	},{
		ref:'mainForm',
		selector:'expBrwMainForm'
	},{
    	ref:'userTab',
		selector:'expBrwUserTab'
	},{
    	ref:'infoTab',
		selector:'expBrwInfoTab'
	},{
    	ref:'fileTab',
		selector:'expBrwFileTab'
	},{
    	ref:'rptTab',
		selector:'expBrwRptTab'
    },{
        ref: 'hidId',     
        selector: 'expBrwMainForm field[name=id]'
    },{
        ref: 'hidHId',     
        selector: 'expBrwMainForm field[name=hId]'
    },{
        ref: 'hidStatus',     
        selector: 'expBrwMainForm field[name=status]'
    },{
        ref: 'btnApprovalMatrix',     
        selector: 'expBrwMainForm button[action=approvalMatrix]'
	}],
	
	init:function() {
		var me = this;

		me.control({
			'expBrwMain [action=add]': {
				click : me.add
			},
			'expBrwMain [action=search]': {
				click : me.search
			},
			'expBrwMainGrid':{
				copy : me.copy,
				edit : me.edit,
				del : me.del,
				search : me.search,
   				showDiagram : me.showDiagram,
   				gotoFolder : me.gotoFolder,
   				viewDetail : me.viewDetail,
   				viewHistory : me.viewHistory
			}
		});
	
	},
    
	COPY_MSG_KEY : 'COPY_EXP_BRW',
	MSG_KEY : 'DELETE_EXP_BRW',
	URL : ALF_CONTEXT+'/exp/brw',
	ADMIN_URL : ALF_CONTEXT+'/admin/exp/brw',
	MSG_URL : ALF_CONTEXT+'/exp/message',
	
	onLaunch:function(app) {
		var me = this;
	
		var mainGrid = me.getMainGrid();
		if (mainGrid) {
			var tbar = me.getMainGrid().getDockedComponent(1);
			
			Ext.Ajax.request({
			      url:me.URL+"/criteria/list",
			      method: "GET",
			      success: function(response){
			    	  
			    	var data = Ext.decode(response.responseText).data;
			    	
					for(var i=data.length-1; i>=0; i--) {
						
						var d = data[i];
						
						var store = Ext.create('PB.store.common.ComboBoxStore');
						
						store.getProxy().api.read = ALF_CONTEXT+'/srcUrl/'+PB.Util.getSrcUrl(d.url);
						if (d.param) {
							var params = d.param.split(",");
							
							store.getProxy().extraParams = {
								p1 : params[0],
								orderBy : 'code',
								all : true
							}
							if (params.length>1) {
								store.getProxy().extraParams.p2 = params[1];
							}
							
						}
						store.load();
						
						var c = {
							xtype:'combo',
							itemId:'cri'+i,
							bgData:d,
							hideLabel:true,
					    	displayField:'name',
					    	valueField:'id',
					        emptyText : d.emptyText,
					        store: store,
					        queryMode: 'local',
					        typeAhead:true,
					        multiSelect:false,
					        forceSelection:true,
					        listConfig : {
//						    	resizable:true,
							    getInnerTpl: function () {
									return '<div>{name}</div>';
							        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
							    }
							},
					        listeners:{
								beforequery : function(qe) {
									qe.query = new RegExp(qe.query, 'i');
					//				qe.forceAll = true;
								}
							}
						}
						
						if (d.listWidth) {
					        c.matchFieldWidth = false;
							c.listConfig.width = d.listWidth;
						}
						
						if (d.width) {
							c.width = d.width;
						}
						
						tbar.insert(0, c);
	
					}
					
			      },
			      failure: function(response, opts){
			          alert("failed");
			      },
			      headers: getAlfHeader()
			}); 
		}
		
		if (ID) {
			
			Ext.Ajax.request({
			      url:me.URL+"/get",
			      method: "GET",
			      params: {
			    	  id : ID
			      },
			      success: function(response){
			    	  
			    	var data = Ext.decode(response.responseText).data;
			    	
			    	var rec = Ext.create("PBExp.model.GridModel",data[0]);
	
					me.edit(rec);
					
			      },
			      failure: function(response, opts){
			          alert("failed");
			      },
			      headers: getAlfHeader()
			});
	
		}
	},
	
	search:function() {
		var me = this;
		var store = me.getMainGrid().getStore();
		
		var params = {
			s : me.getTxtSearch().getValue()
		}
		
		var fields = {};
		
		var tbar = me.getMainGrid().getDockedComponent(1);
		for(var i=0; i<10; i++) {
			var c = tbar.down("[itemId=cri"+i+"]");
			if (c) {
				var d = c.bgData;
				fields[d.field] = c.getValue();
			}
		}
		
		params.fields = Ext.JSON.encode(fields);
		
		store.getProxy().extraParams = params;
		store.currentPage = 1;
		store.load();
	},
	
	activateForm:function() {
		this.getMain().getLayout().setActiveItem(1);
	},
	
	createForm:function(title, rec) {
		var me = this;
	
		me.getMain().insert(1, {
			xtype:'expBrwMainForm',
			title:title
		})
	
		var form = me.getMainForm().down("tabpanel");
	
		form.removeAll(true);
		
	//	form.down("field[name=hId]").setValue(header.id);
		
		Ext.Ajax.request({
		      url:me.MSG_URL+"/list",
		      method: "GET",
		      params: {
		    	  keys : "TAB_TITLE_USER,TAB_TITLE_INFO,TAB_TITLE_ATTENDEE,TAB_TITLE_FILE"
		      },
		      success: function(response){
		    	  
		    	var data = Ext.decode(response.responseText).data;
		    	
		    	var firstTab = form.add({ xtype:'expBrwUserTab', title:data[0].message, rec:rec });
		    	form.setActiveTab(firstTab);
		    	
		    	form.add({ xtype:'expBrwInfoTab', title:data[1].message, rec:rec });
				form.add({ xtype:'expBrwAttendeeTab', title:data[2].message, rec:rec });
				form.add({ xtype:'expBrwFileTab', title:data[3].message, rec:rec });
		      },
		      failure: function(response, opts){
		          alert("failed");
		      },
		      headers: getAlfHeader()
		}); 
	
	},
	
	add:function() {
		var me = this;
		
		if (me.getMainForm()) {
			this.getMainForm().destroy();
		}
		
		delete me.data; // Form Add Mode
	
		Ext.Ajax.request({
		      url:me.URL+"/userDtl",
		      method: "GET",
		      success: function(response){
		    	  
		    	var json = Ext.decode(response.responseText);
		    	
		    	var data = json.data[0];
		    	data.created_time = new Date();
		    	
				me.createForm("Create", data);
				
				me.activateForm();
				
				me.getHidId().setValue(null);
				me.getHidStatus().setValue(null);
		      },
		      failure: function(response, opts){
		          alert("failed");
		      },
		      headers: getAlfHeader()
		});		
	
	},
	
	edit: function(rec) {
		var me = this;
	
		if (!ID) {
			if (me.getMainForm()) {
				me.activateForm();
				return;
			}
	
			me.getMainGrid().getView().getSelectionModel().select(rec);
		}
		
		Ext.Ajax.request({
		      url:me.URL+"/get",
		      method: "GET",
		      params: {
		    	  id : rec.get("id")
		      },
		      success: function(response){
		    	  
		    	var json = Ext.decode(response.responseText);
		    	
		    	me.data = json.data[0]; // Form Edit Mode
		    	me.data.items = json.items;
		    	
		    	Ext.Ajax.request({
			      url:me.URL+"/userDtl",
			      method: "GET",
			      params:{
		    		 r:me.data.req_by,
		    	     c:me.data.created_by
		    	  },
			      success: function(response){
			    	  
			    	var json = Ext.decode(response.responseText);
			    	
			    	var data = json.data[0];
			    	
			    	Ext.merge(me.data, data);
			    	
					me.createForm('Edit : <font color="red">'+rec.get("id")+"</font>", me.data);
					if (ID) {
						var form = me.getMainForm(); 
						form.down("button[action=send]").hide();
						form.down("button[action=saveDraft]").hide();
						form.down("button[action=cancel]").hide();
						form.down("button[action=finish]").show();
						form.down("button[action=cancelEdit]").show();
					}
					else {
						me.activateForm();
					}
					
					me.getHidId().setValue(rec.get("id"));
					me.getHidStatus().setValue(rec.get("status"));					
			      },
			      failure: function(response, opts){
			          alert("failed");
			      },
			      headers: getAlfHeader()
		    	});	
				
		      },
		      failure: function(response, opts){
		          alert("failed");
		      },
		      headers: getAlfHeader()
		});
		
	},
	
	isEditMode : function() {
		return (this.data != null);
	},
	
	showDiagram : function(r){
		if (r.data.status.indexOf('-ปิดงาน-') >= 0) {
			Ext.Msg.alert("Information","ปิดงานไปแล้ว  ไม่สามารถดู workflow ได้");
		}else if (r.data.status.indexOf('-ยกเลิก-') >= 0) {
			Ext.Msg.alert("Information","ยกเลิกไปแล้ว  ไม่สามารถดู workflow ได้");
		} else {
			window.open(Alfresco.constants.PROXY_URI + "api/workflow-instances/" + r.get('workflow_ins_id') + '/diagram',"_new");
		}
	},
	
	gotoFolder : function(r){
		Ext.Ajax.request({
	        url:ALF_CONTEXT+"/util/getFolderName",
	        async : false,
	        method: "POST",
	        params: {
	        	n : r.get('folder_ref')
	        },
	        success: function(response){
	        	
	        	var json = Ext.decode(response.responseText);  
	        	
	        	if(json.success){
	        		
	//            	window.open(MAIN_CONTEXT+"/page/repository#filter=path%7C%2FSites%2F"+CURRENT_SITE+"%2FdocumentLibrary%2F"+json.folderName+"%7C", "_new");
	//            	window.open(MAIN_CONTEXT+"/page/site/"+CURRENT_SITE+"/documentlibrary#filter=path%7C"+ json.data.name + "%7C", "_new");
	            	window.open(MAIN_CONTEXT+"/page/repository#filter=path%7C"+json.data.name+"%7C", "_new");
	        		
	        	}
	        	
	        },
	        failure: function(response, opts){
	        	
	        	//console.log("fail");
	        },
	        headers: getAlfHeader()
	    });
	
	},
	
	viewDetail : function(r){
	//    window.open(MAIN_CONTEXT+"/page/document-details?nodeRef="+r.get("doc_ref"),"_new");
	    window.open(Alfresco.constants.PROXY_URI_RELATIVE+"api/node/content/"+nodeRef2Url(r.get("doc_ref")),"_new");
	},
	
	viewHistory : function(r){
		var dlg = Ext.create("PBExp.view.workflow.DtlDlg");
		var id = r.get("id");
	
		// Current Task
		Ext.Ajax.request({
		      url:ALF_CONTEXT+'/exp/wf/task/list',
		      method: "GET",
		      params: {
		    	  id : id
		      },
		      success: function(response) {
				  var data = Ext.decode(response.responseText).data[0];
				  var curTask;
				  if (data) {
					  curTask = data.type+(data.assignedTo ? " : " : "")+data.assignedTo;
				  } else {
					  curTask = "-";
				  }
				  dlg.items.items[0].items.items[0].items.items[0].setText('<font color="blue">'+curTask+'</font>',false);
				  
			  },
		      failure: function(response, opts){
		          alert("failed");
		      },
		      headers: getAlfHeader()
		});
		
		
		// Path
		var store = dlg.items.items[0].items.items[1].getStore(); 
		store.getProxy().extraParams = {
			id : id
		}
		store.load();
		
		// History
		store = dlg.items.items[1].getStore();
		store.getProxy().extraParams = {
		   	id : id
		};
		store.load();
		
		// Show
		dlg.show();
	},
	
	del : function(r) {
		
		this.getMainGrid().getView().getSelectionModel().select(r);
		
		this.selectedRec = r;
		PB.Dlg.confirm('CONFIRM_'+this.MSG_KEY,this,'doDel', MODULE_EXP);
	},
	
	doDel : function(){
		var me = this;
		
		Ext.Ajax.request({
		     url:me.URL+"/delete",
		     method: "POST",
		     params: {
		         id: me.selectedRec.get("id")
		     },
		     success: function(res){
		    	 
		    	 var json = Ext.decode(res.responseText);
		      	  
		    	 if(json.success){
		    	  
		    		PB.Dlg.alert('SUCC_'+me.MSG_KEY, MODULE_EXP);
		    		
		        	var store = me.getMainGrid().getStore();
		  	    	store.load();
		    		 
		    	 }else{
		    		 PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_EXP);
		    	 }
		    	 
		     },
		     failure: function(response, opts){
		    	 PB.Dlg.error('ERR_'+me.MSG_KEY, MODULE_EXP);
		     },
		     headers: getAlfHeader()
		});        	
	
	},
	
	copy : function(r) {
		
		this.getMainGrid().getView().getSelectionModel().select(r);
		
		this.selectedRec = r;
		PB.Dlg.confirm('CONFIRM_'+this.COPY_MSG_KEY,this,'doCopy', MODULE_EXP);
	},
	
	doCopy : function(){
		var me = this;
		
		Ext.Ajax.request({
		     url:me.URL+"/copy",
		     method: "POST",
		     params: {
		         id: me.selectedRec.get("id")
		     },
		     success: function(res){
		    	 
		    	 var json = Ext.decode(res.responseText);
		      	  
		    	 if(json.success){
			   		var id = json.data;
			   		PB.Dlg.info('SUCC_'+me.COPY_MSG_KEY, MODULE_EXP, {msg:'ID:'+id, fn:me.closeForm, scope:me});
		    	 }else{
		    		PB.Dlg.error('ERR_'+me.COPY_MSG_KEY, MODULE_EXP);
		    	 }
		    	 
		     },
		     failure: function(response, opts){
		    	 PB.Dlg.error('ERR_'+me.COPY_MSG_KEY, MODULE_EXP);
		     },
		     headers: getAlfHeader()
		});        	
	
	},
	
	closeForm:function() {
		this.getMainGrid().getStore().load();
	}	

});
