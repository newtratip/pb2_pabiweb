Ext.define('PBPcm.controller.committee.Form', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref: 'main',
        selector: 'pcmReqMain'
    },{
        ref: 'mainForm',
        selector: 'pcmReqMainForm'
    },{
        ref: 'dlg',
        selector: 'pcmCommitteeDtlDlg'
    },{
        ref: 'form',
        selector: 'pcmCommitteeDtlDlg [itemId=formDetail]'
    },{
        ref: 'hidId',     
        selector:'pcmCommitteeDtlDlg field[name=id]'
    },{
        ref: 'txtFirstName',
        selector:'pcmCommitteeDtlDlg field[name=first_name]'
    },{
        ref: 'txtLastName',
        selector:'pcmCommitteeDtlDlg field[name=last_name]'
    },{
        ref: 'userGrid',
        selector:'pcmCommitteeDtlDlg grid'
    },{
        ref: 'txtSearchTerm',
        selector:'pcmCommitteeDtlDlg field[itemId=searchTerm]'
	},{
	    ref: 'cmtMain',
	    selector:'pcmReqCmtTab'
	},{
	    ref: 'cmtTab',
	    selector:'pcmReqCmtTab [itemId=cmtTab]'
    },{
        ref: 'hidTotal',
        selector:'pcmReqItemTab field[name=total]'
	}],
    
    init:function() {
		var me = this;
		
		me.control({
//			'pcmCommitteeDtlDlg': {
//				selectSrcType : me.selectSrcType
//			},
//			'pcmCommitteeDtlDlg grid': {
//				select : me.selectUser
//			},
//			'pcmCommitteeDtlDlg [action=ok]': {
//				click : me.ok
//			},
//			'pcmCommitteeDtlDlg button': {
//				searchUser : me.searchUser
//			},
//			'pcmCommitteeDtlDlg textfield': {
//				searchUser : me.searchUser
//			},
			'pcmReqCmtTab [action=addCmtEmployee]': {
				click : me.addEmployee
			},
			'pcmReqCmtTab [action=addCmtNonEmployee]': {
				click : me.addNonEmployee
			},
			'pcmReqCmtTab grid':{
				edit:me.edit,
				del:me.del
			},
			'pcmReqCmtTab field[name=method]': {
				select : me.methodSelect
			},	
			'pcmReqCmtTab': {
				selectCmb : me.methodSelect
			}
		});

	},
	
	DEL_MSG_KEY : 'DELETE_PCM_REQ_CMT',
    URL : ALF_CONTEXT+'/pcm/req/cmt',
    MSG_URL : ALF_CONTEXT+'/pcm/message',
    

//    ok:function() {
//		var me = this;
//		
//		if (validForm(me.getForm())) {
//		
//			var rec; 
//			
//			var id = me.getHidId().getValue();
//			if (!id) {
//		
//				var store = me.grid.getStore();
//		
//				var maxId = 0;
//				if (store.getCount() > 0)
//				{
//				  maxId = store.getAt(0).get('id');
//				  store.each(function(rec)
//				  {
//				    maxId = Math.max(maxId, rec.get('id'));
//				  });
//				}
//				
//				rec = Ext.create('PBPcm.model.CmtGridModel',{
//		    		id : maxId+1,
//		    		action : 'ED' 
//		    	});
//			} else {
//				rec = me.selectedRec;
//			}
//			
//			rec.set("first_name",me.getTxtFirstName().getValue()); 
//			rec.set("last_name",me.getTxtLastName().getValue());
//			
//			if (!id) {
//				rec.set("position", store.getCount() == 0 ? "ประธาน" : "กรรมการ");
//
//				rec.commit();
//				store.add(rec);
//			} else {
//				me.grid.getView().refresh();
//			}
//			
//			me.getDlg().destroy();
//		} // validForm
//	},
	
//	add:function(btn) {
//		var me = this;
//		me.grid = me.getCmtTab().getActiveTab();
//		me.createDlg('เพิ่ม').show();
//	},
//	
	addEmployee:function(btn) {
		var me = this;
//		me.grid = me.getCmtTab().getActiveTab();
		me.createEmployeeDlg(PB.Label.m.add + (getLang().startsWith("th") ? "" : " ") + PB.Label.m.emp).show();
	},

	addNonEmployee:function(btn) {
		var me = this;
//		me.grid = me.getCmtTab().getActiveTab();
		me.createNonEmployeeDlg(PB.Label.m.add + (getLang().startsWith("th") ? "" : " ") + PB.Label.m.nonemp).show();
	},
	
//	createDlg:function(title) {
//		
//		var me = this;
//		
//		var dialog = Ext.create('PBPcm.view.committee.DtlDlg', {
//		    title : title
//		});
//		
//		return dialog;
//	},
	
    dlgEmployeeUserCallBack:function(items) {
		var me = this;
		
		var CHAIRMAN = "ประธานกรรมการ";
		var COMMITTEE = "กรรมการ";
		
		var grid = this.targetPanel;
		
		var store = grid.getStore();
		
		var maxId = 0;
		
		for(var a in items) {
			if (store.getCount() > 0)
			{
			  maxId = store.getAt(0).get('id');
			  store.each(function(rec)
			  {
			    maxId = Math.max(maxId, rec.get('id'));
			  });
			}
			
			var rec = Ext.create('PBPcm.model.CmtGridModel',{
				id : maxId+1,
				action : 'D' 
			});
			
			rec.set("code", items[a].data['code']);
			rec.set("fname", items[a].data['fname']);
			rec.set("lname", items[a].data['lname']);
//			rec.set("unit_type", items[a].data['utype']);
//			rec.set("position", items[a].data['position']);
//			rec.set("position_id", items[a].data['position_id']);
			
			rec.set("position", COMMITTEE);

			rec.commit();
			store.add(rec);
		}

		if (store.getCount() > 1) {
			store.getAt(0).set("position", CHAIRMAN);
			store.getAt(0).commit();
		}

	},
	
	employeeValidate:function(items) {
		var result = true;
		
		var panel = this.targetPanel.up("tabpanel");
		
		for(var a in items) {
			var nv = items[a].data['fname']+' '+items[a].data['lname'];
			
			panel.items.each(function(grid){
				if (result) {
					if (grid.xtype == 'grid') {
					
						var store = grid.getStore();
						
						for(var i=0;i < store.getCount(); i++) {
							var it = store.getAt(i);
							if (nv == it.get('fname')+' '+it.get('lname')) {
								result = false;
								break;
							}
						}
					}
						
				}
			});
			
			if (!result) {
				break;
			}
		}
		
		if (!result) {
			PB.Dlg.error('',MODULE_PCM,{msg:'เลือกกรรมการซ้ำ กรุณาเลือกใหม่'});
		}
		
		return result;
	},

	createEmployeeDlg:function(title, rec) {
		
		var me = this;
		
		var dialog = Ext.create('PB.view.common.SearchEmployeeUserDlg', {
		    title : title,
			targetPanel:me.getCmtTab().getActiveTab(),
			validateCallback:me.employeeValidate,
			callback:me.dlgEmployeeUserCallBack,
			rec : rec,
		    needFootPrint : false
		});
		
		return dialog;
	},
	
	dlgNonEmployeeUserCallBack:function(id, r) {
		var me = this;
	
		var CHAIRMAN = "ประธานกรรมการ";
		var COMMITTEE = "กรรมการ";
		
		var grid = this.targetPanel;
		
		var store = grid.getStore();
		if (!id) {
		
			var maxId = 0;
			if (store.getCount() > 0)
			{
			  maxId = store.getAt(0).get('id');
			  store.each(function(rec)
			  {
			    maxId = Math.max(maxId, rec.get('id'));
			  });
			}
			
			var rec = Ext.create('PBPcm.model.CmtGridModel',{
	    		id : maxId+1,
	    		action : 'ED' 
	    	});
		
		} else {
			rec = me.rec;
		}
	
		rec.set("fname", r.data.fname); 		
		rec.set("lname", r.data.lname); 		
		rec.set("position", COMMITTEE);
		
		if (!id) {
			rec.commit();
			store.add(rec);
			
			if (store.getCount() > 1) {
				store.getAt(0).set("position", CHAIRMAN);
				store.getAt(0).commit();
			}
			
		} else {
			grid.getView().refresh();
		}		
	},
	
	nonEmployeeValidate:function(r) {
		var result = true;
		
		var panel = this.targetPanel.up("tabpanel");
		
		var nv = r.data['fname']+' '+r.data['lname'];
		
		panel.items.each(function(grid){
			if (result) {
				if (grid.xtype == 'grid') {
				
					var store = grid.getStore();
					
					for(var i=0;i < store.getCount(); i++) {
						var it = store.getAt(i);
						if (nv == it.get('fname')+' '+it.get('lname')) {
							result = false;
							break;
						}
					}
				}
					
			}
		});
		
		if (!result) {
			PB.Dlg.error('',MODULE_PCM,{msg:'เลือกกรรมการซ้ำ กรุณาเลือกใหม่'});
		}
		
		return result;
	},	

	createNonEmployeeDlg:function(title, rec) {
		
		var me = this;
		
		var dialog = Ext.create('PB.view.common.SearchOtherUserDlg', {
		    title : title,
			targetPanel:me.getCmtTab().getActiveTab(),
			validateCallback:me.nonEmployeeValidate,
			callback:me.dlgNonEmployeeUserCallBack,
			rec : rec,
			needPosition : false,
		    needFootPrint : false
		});
		
		return dialog;
	},
	
	edit:function(rec) {
		var me = this;
		if (!me.getDlg()) {

			me.grid = me.getCmtTab().getActiveTab();
			me.grid.getView().getSelectionModel().select(rec);
			me.selectedRec = rec;		
		
			var dialog = me.createNonEmployeeDlg(PB.Label.m.edit, rec);
			
//			me.getHidId().setValue(rec.get("id"));
//			me.getTxtFirstName().setValue(rec.get("fname"));
//			me.getTxtLastName().setValue(rec.get("lname"));
			
			dialog.show();
		}
		
	},
	
	del:function(rec) {
		var me = this;
		me.grid = me.getCmtTab().getActiveTab();
		me.grid.getView().getSelectionModel().select(rec);
		
		me.selectedRec = rec;
	
		PB.Dlg.confirm('CONFIRM_'+me.DEL_MSG_KEY, me,'doDel', MODULE_PCM);
	},
	
	doDel:function() {
		var me = this;
		
		var store = me.grid.getStore(); 
		store.remove(me.selectedRec);
		
		var CHAIRMAN = "ประธานกรรมการ";

		if (store.getCount()>1) {
			store.getAt(0).set('position', CHAIRMAN);
			store.getAt(0).commit();
		}
	},	
	
	methodSelect:function(cmb, rec, mainRec) {
	
		var me = this;
		var cmtTab = me.getCmtTab();

		cmtTab.removeAll(true);
		
		var columns = []
		columns.push(
			{
	        	xtype: 'actioncolumn',
	        	dataIndex: 'action',
	        	text: '', 
	            width: 100,
	            align:'center',
	            items: [{
	                tooltip: 'Edit', 
	                action : 'edit',
	        	    getClass: function(v) {
	        	    	return getActionIcon(v, "E", 'edit');
	                }
	            }, {
	                tooltip: 'Delete', 
	                action : 'del',
	        	    getClass: function(v) {
	        	    	return getActionIcon(v, "D", 'delete');
	        	    }
	            }]
	          },
			  { text: PB.Label.m.ecode,  dataIndex: 'code', width:100},
			  { text: PB.Label.m.fullname,  dataIndex: 'fname', flex:1, renderer:function(v,m,r){return r.get('fname')+' '+r.get('lname')}},
			  { text: PB.Label.m.pos,  dataIndex: 'position', width:150}
		);
		
		var data = rec[0].data.data;
		
		var id = me.getMainForm().down("field[name=id]");

		var cmts = data["cmts"];
		for(var i=0;i < cmts.length; i++) {
			var cmt = cmts[i];

			var store = Ext.create("PBPcm.store.CmtGridStore", {autoLoad:false});
			if (id.getValue()) {
				store.getProxy().extraParams = {
    				id:id.getValue(),
    				cmt:cmt.id
    			}
				store.load();
			}
			
			var tab = cmtTab.add({
							xtype:'grid',
							title:cmt.title,
							cmtId:cmt.id,
							columns : columns,
							store:store,
							tbar:[{
								xtype : 'tbfill'
						    },{
				        		xtype: 'button',
				                text: PB.Label.m.add + (getLang().startsWith("th") ? "" : " ") + PB.Label.m.emp,
				                iconCls: "icon_add",
				                action:'addCmtEmployee'
						    },{
				        		xtype: 'button',
				                text: PB.Label.m.add + (getLang().startsWith("th") ? "" : " ") + PB.Label.m.nonemp,
				                iconCls: "icon_add",
				                action:'addCmtNonEmployee'
				        	}],
				        	cmt:cmt
						});
			
			if (i==0) {
				cmtTab.setActiveTab(tab);
			}
		}
		
		if (data.cond2) {
			
			var store = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
			store.getProxy().api.read = ALF_CONTEXT+'/admin/main/purchase/condition/list';
			store.getProxy().extraParams = {
				id:data.condition_id
			}
			store.load();
			
			cmtTab.add({
				xtype:'pcmReqCmtCond2Tab',
				title:'เงื่อนไข',
				store:store,
				preCond2:data.cond2,
				rec:mainRec
			});
		}
		
	}
	
//	selectSrcType:function(rad, v) {
//		var me = this;
//		var type = v ? "U" : "P";
//	
//		var store = me.getCmbCode().getStore();
//		store.getProxy().extraParams = {
//			t:type
//		}
//		store.load();
//	},
//
//	searchUser : function(sender) {
//		var me = this;
//		
//		var store = me.getUserGrid().getStore();
//		
//		store.getProxy().extraParams = {
//			s:me.getTxtSearchTerm().getValue()
//		}
//		
//		store.load();
//	},
//	
//	selectUser : function(sender, rec, index) {
//		var me = this;
//		
//		me.getTxtFirstName().setValue(rec.get("first_name"));
//		me.getTxtLastName().setValue(rec.get("last_name"));
//	}
	
});
