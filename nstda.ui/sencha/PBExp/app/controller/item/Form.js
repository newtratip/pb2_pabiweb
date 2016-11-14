Ext.define('PBExp.controller.item.Form', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref: 'main',
        selector: 'expBrwMain'
    },{
        ref: 'mainForm',
        selector: 'expBrwMainForm'
    },{
        ref: 'dlg',
        selector: 'expBrwItemDtlDlg'
    },{
        ref: 'form',
        selector: 'expBrwItemDtlDlg [itemId=formDetail]'
	},{
    	ref:'grid',
		selector:'expBrwItemTab grid[itemId=itemGrid]'
    },{
        ref: 'hidTotal',     
        selector:'expBrwItemTab field[name=total]'
    },{
        ref: 'lblNetAmt',     
        selector:'expBrwItemTab label[name=netAmt]'
    },{
        ref: 'hidId',     
        selector:'expBrwItemDtlDlg field[name=id]'
    },{
        ref: 'cmbActGrpId',
        selector:'expBrwItemDtlDlg field[name=actGrpId]'
    },{
        ref: 'cmbActId',
        selector:'expBrwItemDtlDlg field[name=actId]'
    },{
        ref: 'cmbCondition1',
        selector:'expBrwItemDtlDlg field[name=condition1]'
    },{
        ref: 'txtActivity',
        selector:'expBrwItemDtlDlg field[name=activity]'
    },{
        ref: 'txtAmount',     
        selector:'expBrwItemDtlDlg field[name=amount]'
    },{
        ref: 'gridAct',
        selector:'expBrwItemDtlDlg grid'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'expBrwItemDtlDlg [action=ok]': {
				click : me.ok
			},
			'expBrwItemDtlDlg': {
				selectActivityGroup : me.selectActivityGroup,
				selectActivity : me.selectActivity,
				selectCond1 : me.selectCond1,
				cond1Load : me.cond1Load
			},
			'expBrwItemTab [action=addItem]': {
				click : me.add
			},
			'expBrwItemTab grid[itemId=itemGrid]':{
				edit:me.edit,
				del:me.del
			},
			'expBrwItemTab': {
				itemStoreLoad:me.calSummary
			}
		});

	},
	
	DEL_MSG_KEY : 'DELETE_EXP_BRW_ITEM',
    URL : ALF_CONTEXT+'/exp',
    MSG_URL : ALF_CONTEXT+'/exp/message',

    ok:function() {
	
		var me = this;
		
		if (validForm(me.getForm())) {
			
			var rec; 
			
			var id = me.getHidId().getValue();
			if (!id) {

				var store = me.getGrid().getStore();
		
				var maxId = 0;
				if (store.getCount() > 0)
				{
				  maxId = store.getAt(0).get('id');
				  store.each(function(rec)
				  {
				    maxId = Math.max(maxId, rec.get('id'));
				  });
				}
				
				rec = Ext.create('PBExp.model.ItemGridModel',{
		    		id : maxId+1,
		    		action : 'ED' 
		    	});
				
			} else {
				rec = me.selectedRec;
			}
			rec.set("actGrpId",me.getCmbActGrpId().getValue());
			me.getCmbActGrpId().getStore().each(function(r){
				if (r.get("id")==me.getCmbActGrpId().getValue()) {
					rec.set("actGrpName", r.get("name"));
				}
			});
			rec.set("actId",me.getCmbActId().getValue());
			me.getCmbActId().getStore().each(function(r){
				if (r.get("id")==me.getCmbActId().getValue()) {
					rec.set("actName", r.get("name"));
				}
			});
			rec.set("condition1",me.getCmbCondition1().getValue() ? me.getCmbCondition1().getValue() : "");
			rec.set("activity",me.getTxtActivity().getValue());
			rec.set("amount",me.getTxtAmount().getValue());
			
			if (!id) {
				rec.commit();
				store.add(rec);
			} else {
				me.getGrid().getView().refresh();
			}
			
			me.getDlg().destroy();
			
			me.calSummary();
		} // validForm
	}, // method
	
	add:function() {
		var me = this;
	
		this.createDlg('เพิ่ม').show();
	},
	
	createDlg:function(title, rec) {
		
		var me = this;
		
		var dialog = Ext.create('PBExp.view.item.DtlDlg', {
		    title : title,
		    rec : rec
		});
		
		return dialog;
	},
	
	edit:function(rec) {
		var me = this;
		
		me.getGrid().getView().getSelectionModel().select(rec);
		me.selectedRec = rec;		
	
		var dialog = me.createDlg('แก้ไข',rec);
		
		dialog.show();
	},
	
	del:function(rec) {
		this.getGrid().getView().getSelectionModel().select(rec);
		
		this.selectedRec = rec;
	
		PB.Dlg.confirm('CONFIRM_'+this.DEL_MSG_KEY,this,'doDel', MODULE_EXP);
	},
	
	doDel:function() {
		var me = this;
		me.getGrid().getStore().remove(this.selectedRec);
		me.calSummary();
	},
	
	calSummary:function() {
		var me = this;
		
		var total = 0;
		
		var store = me.getGrid().getStore();
		store.each(function(rec){
			total += rec.data.amount;
		});
		
		me.getLblNetAmt().setText(Ext.util.Format.number(total, DEFAULT_MONEY_FORMAT));
		me.getHidTotal().setValue(total);
	},
	
	selectActivityGroup:function(cmb, rec) {
		var me = this;
		
	//	console.log("id:"+rec[0].data.id);
	
		var store = me.getCmbActId().getStore();
		store.getProxy().extraParams = {
			actGrpId:rec[0].data.id,
			query:getLang()+" "
		}
		store.load();
	},
	
	selectActivity:function(cmb, rec) {
		var me = this;
		
	//	console.log("id:"+rec[0].data.id);
	
		var store = me.getCmbCondition1().getStore();
		store.getProxy().extraParams = {
			id:rec[0].data.id
		}
		store.load();
		
		store = me.getGridAct().getStore();
		store.getProxy().extraParams = {
			id:0,
			cond:0
		}
		store.load();		
	},
	
	selectCond1:function(cmb, rec) {
		var me = this;
		
	//	console.log("cond1:"+rec[0].data.id);
	
		var store = me.getGridAct().getStore();
		store.getProxy().extraParams = {
			id:rec[0].data.data.activity_id,
			cond:rec[0].data.data.condition_1
		}
		store.load();
	},
	
	cond1Load:function(recs) {
		var me = this;
		
		var cmbCond1 = me.getCmbCondition1(); 
		
		cmbCond1.setDisabled(recs.length<=0);
	}
	
});
