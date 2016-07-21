Ext.define('PBExp.controller.attendee.Form', {
    extend: 'Ext.app.Controller',

    refs:[{
    	ref:'empGrid',
		selector:'expBrwAttendeeTab grid[itemId=empGrid]'
	},{
    	ref:'othGrid',
		selector:'expBrwAttendeeTab grid[itemId=othGrid]'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'expBrwAttendeeTab grid [action=addEmp]': {
				click : me.addEmp
			},
			'expBrwAttendeeTab grid [action=addOth]': {
				click : me.addOth
			},
			'expBrwAttendeeTab grid':{
				editEmp:me.editEmp,
				delEmp:me.delEmp,
				editOth:me.editOth,
				delOth:me.delOth
			}
		});

	},
	
	DEL_MSG_KEY : 'DELETE_EXP_BRW_ATTENDEE',
    URL : ALF_CONTEXT+'/exp',
    MSG_URL : ALF_CONTEXT+'/exp/message',

    dlgEmployeeUserCallBack:function(items) {
		var me = this;
		
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
			
			var rec = Ext.create('PBExp.model.AttendeeGridModel',{
				id : maxId+1,
				action : 'D' 
			});
			
			rec.set("code", items[a].data['code']);
			rec.set("fname", items[a].data['fname']);
			rec.set("lname", items[a].data['lname']);
			rec.set("unit_type", items[a].data['utype']);
			rec.set("position", items[a].data['position']);
			rec.set("position_id", items[a].data['position_id']);
			
			rec.commit();
			store.add(rec);
		}

	}, // method
	
	addEmp:function() {
		this.createEmpDlg('เพิ่ม',  null).show();
	},
	
	createEmpDlg:function(title, rec) {
		var me = this;
		
		var dlg = Ext.create('PB.view.common.SearchEmployeeUserDlg', {
		    title : title,
			targetPanel:me.getEmpGrid(),
			callback:me.dlgEmployeeUserCallBack,
			rec : rec,
			needFootPrint:true
		});
		
		return dlg;
	},
	
	editEmp:function(rec) {
		var me = this;
		
		me.getEmpGrid().getView().getSelectionModel().select(rec);
	
		me.createEmpDlg('แก้ไข',  rec).show();
	},
	
	delEmp:function(rec) {
		this.getEmpGrid().getView().getSelectionModel().select(rec);
		
		this.selectedRec = rec;
	
		PB.Dlg.confirm('CONFIRM_'+this.DEL_MSG_KEY,this,'doDelEmp', MODULE_EXP);
	},
	
	doDelEmp:function() {
		var me = this;
		me.getEmpGrid().getStore().remove(me.selectedRec);
	},
	
	dlgOtherUserCallBack:function(id, r) {
		var me = this;
	
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
			
			var rec = Ext.create('PBExp.model.AttendeeGridModel',{
	    		id : maxId+1,
	    		action : 'ED' 
	    	});
		
		} else {
			rec = me.rec;
		}
	
		rec.set("fname", r.data.fname); 		
		rec.set("lname", r.data.lname); 		
		rec.set("position", r.data.position);
		
		rec.set("dest_type", r.data['desttype']);
		rec.set("destination", r.data['destination']);
		rec.set("route", r.data['route']);
		rec.set("depart", r.data['depart']);
		rec.set("arrive", r.data['arrive']);
		rec.set("cls", r.data['cls']);
		rec.set("amount", r.data['amount']);
		
		if (!id) {
			rec.commit();
			store.add(rec);
		} else {
			grid.getView().refresh();
		}		
	},
	
	addOth:function() {
		this.createOthDlg('เพิ่ม',  null).show();
	},
	
	createOthDlg:function(title, rec) {
		var me = this;
		
		var dlg = Ext.create('PB.view.common.SearchOtherUserDlg', {
		    title : title,
			targetPanel:me.getOthGrid(),
			callback:me.dlgOtherUserCallBack,
			rec : rec,
			needPosition:true,
			needFootPrint:true
		});
		
		return dlg;
	},
	
	editOth:function(rec) {
		var me = this;
		
		me.getOthGrid().getView().getSelectionModel().select(rec);
	
		me.createOthDlg('แก้ไข',  rec).show();
	},
	
	delOth:function(rec) {
		this.getOthGrid().getView().getSelectionModel().select(rec);
		
		this.selectedRec = rec;
	
		PB.Dlg.confirm('CONFIRM_'+this.DEL_MSG_KEY,this,'doDelOth', MODULE_EXP);
	},
	
	doDelOth:function() {
		var me = this;
		me.getOthGrid().getStore().remove(me.selectedRec);
	}

});
