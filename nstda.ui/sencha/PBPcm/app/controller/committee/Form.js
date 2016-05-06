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
        ref: 'txtPosition',
        selector:'pcmCommitteeDtlDlg field[name=position]'
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
			'pcmCommitteeDtlDlg [action=ok]': {
				click : me.ok
			},
			'pcmReqCmtTab [action=addCmt]': {
				click : me.add
			},
			'pcmReqCmtTab grid':{
				edit:me.edit,
				del:me.del
			},
			'pcmReqCmtTab field[name=method]': {
				select : me.methodSelect
			}			
		});

	},
	
	DEL_MSG_KEY : 'DELETE_PCM_REQ_CMT',
    URL : ALF_CONTEXT+'/pcm/req/cmt',
    MSG_URL : ALF_CONTEXT+'/pcm/message',

    ok:function() {
		var me = this;
		
		if (validForm(me.getForm())) {
		
			var rec; 
			
			var id = me.getHidId().getValue();
			if (!id) {
		
				var store = me.grid.getStore();
		
				var maxId = 0;
				if (store.getCount() > 0)
				{
				  maxId = store.getAt(0).get('id');
				  store.each(function(rec)
				  {
				    maxId = Math.max(maxId, rec.get('id'));
				  });
				}
				
				rec = Ext.create('PBPcm.model.CmtGridModel',{
		    		id : maxId+1,
		    		action : 'ED' 
		    	});
			} else {
				rec = me.selectedRec;
			}
			
			rec.set("first_name",me.getTxtFirstName().getValue()); 
			rec.set("last_name",me.getTxtLastName().getValue()); 
			rec.set("position",me.getTxtPosition().getValue());
			
			if (!id) {
				rec.commit();
				store.add(rec);
			} else {
				me.grid.getView().refresh();
			}
			
			me.getDlg().destroy();
		} // validForm
	},
	
	add:function(btn) {
		var me = this;
		me.grid = me.getCmtTab().getActiveTab();
		me.createDlg('เพิ่ม').show();
	},
	
	createDlg:function(title) {
		
		var me = this;
		
		var dialog = Ext.create('PBPcm.view.committee.DtlDlg', {
		    title : title
		});
		
		return dialog;
	},
	
	edit:function(rec) {
		var me = this;
		if (!me.getDlg()) {

			me.grid = me.getCmtTab().getActiveTab();
			me.grid.getView().getSelectionModel().select(rec);
			me.selectedRec = rec;		
		
			var dialog = me.createDlg('แก้ไข');
			
			me.getHidId().setValue(rec.get("id"));
			me.getTxtFirstName().setValue(rec.get("first_name"));
			me.getTxtLastName().setValue(rec.get("last_name"));
			me.getTxtPosition().setValue(rec.get("position"));
			
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
		me.grid.getStore().remove(me.selectedRec);
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
			  { text: 'ชื่อ',  dataIndex: 'first_name', flex:1},
			  { text: 'นามสกุล',  dataIndex: 'last_name', flex:1},
			  { text: 'ตำแหน่ง',  dataIndex: 'position', width:100}
		);
		
		var data = rec[0].data.data;
		
		var id = me.getMainForm().down("field[name=id]");

		for(var i=1;i <= 4; i++) {
			var cmtTitle = data["committee"+i];
			if (cmtTitle) {
				var store = Ext.create("PBPcm.store.CmtGridStore", {autoLoad:false});
				if (id.getValue()) {
					store.getProxy().extraParams = {
	    				id:id.getValue(),
	    				cmt:cmtTitle
	    			}
					store.load();
				}
				
				var tab = cmtTab.add({
								xtype:'grid',
								title:cmtTitle,
								columns : columns,
								store:store,
								tbar:[{
									xtype : 'tbfill'
							    },{
					        		xtype: 'button',
					                text: "Add",
					                iconCls: "icon_add",
					                action:'addCmt'
					        	}]
							});
				
				if (i==1) {
					cmtTab.setActiveTab(tab);
				}
			}
		}
		
		if (data.cond2) {
			
			var store = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
			
			var conds = data.cond2.split("\r\n");
			
			var cond2Title = conds[0];
			
			for(var i=1; i<conds.length; i++) {
				if ("0123456789".indexOf(conds[i].substring(0,1)) >= 0) { // start with Number
					console.log("'"+conds[i]+"'");
					var item = Ext.create('PB.model.common.ComboBoxModel',{
						id:conds[i],
						name:conds[i]
					});
					store.add(item);
				}
			}
			
			cmtTab.add({
				xtype:'pcmReqCmtCond2Tab',
				title:'เงื่อนไข',
				store:store,
				preCond2:cond2Title,
				rec:mainRec
			});
		}
		
	}
	
});
