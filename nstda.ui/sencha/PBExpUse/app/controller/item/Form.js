Ext.define('PBExpUse.controller.item.Form', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref: 'main',
        selector: 'expUseMain'
    },{
        ref: 'mainForm',
        selector: 'expUseMainForm'
    },{
        ref: 'dlg',
        selector: 'expUseItemDtlDlg'
    },{
        ref: 'form',
        selector: 'expUseItemDtlDlg [itemId=formDetail]'
	},{
    	ref:'uploadGrid',
		selector:'expUseFileTab uploadGrid'
	},{
    	ref:'grid',
		selector:'expUseItemTab grid'
    },{
        ref: 'hidId',     
        selector:'expUseItemDtlDlg field[name=id]'
    },{
        ref: 'cmbActivityId',
        selector:'expUseItemDtlDlg field[name=activityId]'
    },{
        ref: 'cmbCondition1',
        selector:'expUseItemDtlDlg field[name=condition1]'
    },{
        ref: 'txtAmount',     
        selector:'expUseItemDtlDlg field[name=amount]'
    },{
        ref: 'gridAct',
        selector:'expUseItemDtlDlg grid'
    },{
        ref: 'lblGrossAmt',
        selector:'expUseItemTab label[name=grossAmt]'
    },{
        ref: 'lblVatAmt',
        selector:'expUseItemTab label[name=vatAmt]'
    },{
        ref: 'lblNetAmt',
        selector:'expUseItemTab label[name=netAmt]'
    },{
        ref: 'hidVat',     
        selector:'expUseItemTab field[name=vat]'
    },{
        ref: 'hidTotal',     
        selector:'expUseItemTab field[name=total]'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'expUseItemDtlDlg [action=ok]': {
				click : me.ok
			},
			'expUseItemDtlDlg': {
				selectActivity : me.selectActivity,
				selectCond1 : me.selectCond1,
				cond1Load : me.cond1Load
			},
			'expUseItemTab grid [action=addItem]': {
				click : me.add
			},
			'expUseItemTab grid':{
				edit:me.edit,
				del:me.del
			},
			'expUseItemTab': {
				selectVat : me.selectVat,
				itemStoreLoad:me.calSummary
			}
		});

	},
	
	DEL_MSG_KEY : 'DELETE_EXP_USE_ITEM',
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
				
				rec = Ext.create('PBExpUse.model.ItemGridModel',{
		    		id : maxId+1,
		    		action : 'ED' 
		    	});
				
			} else {
				rec = me.selectedRec;
			}
			rec.set("activityId",me.getCmbActivityId().getValue());
			me.getCmbActivityId().getStore().each(function(r){
				if (r.get("id")==me.getCmbActivityId().getValue()) {
					rec.set("activityName", r.get("name"));
				}
			});
			rec.set("position", "");
			rec.set("condition2", "");
			rec.set("uom" ,"");
			rec.set("condition1",me.getCmbCondition1().getValue());
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
		
		var dialog = Ext.create('PBExpUse.view.item.DtlDlg', {
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
		
//		me.getHidId().setValue(rec.get("id"));
//		me.getCmbActivityId().setValue(rec.get("activityId"));
//		me.getCmbCondition1().setValue(rec.get("condition1"));
//		me.getTxtAmount().setValue(rec.get("amount"));
		
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
		
		var grossAmt = 0;
		
		var store = me.getGrid().getStore();
		store.each(function(rec){
			grossAmt += rec.data.amount;
		});
		
		var vat = parseFloat(me.getHidVat().getValue());
		var vatAmt = grossAmt * vat;
		var netAmt = grossAmt+vatAmt;
		
		me.getLblGrossAmt().setText(Ext.util.Format.number(grossAmt, DEFAULT_MONEY_FORMAT));
		me.getLblVatAmt().setText(Ext.util.Format.number(vatAmt, DEFAULT_MONEY_FORMAT));
		me.getLblNetAmt().setText(Ext.util.Format.number(netAmt, DEFAULT_MONEY_FORMAT));
		
		me.getHidTotal().setValue(netAmt);
	},
	
	selectVat:function(cmb, rec) {
		var me = this;
		
		if (rec[0].data.data.amount) {
			me.getHidVat().setValue(rec[0].data.data.amount);
		} else {
			me.getHidVat().setValue(0);
		}
		
		me.calSummary();
	},
	
	selectActivity:function(cmb, rec) {
		var me = this;
		
//		console.log("id:"+rec[0].data.id);

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
		
//		console.log("cond1:"+rec[0].data.id);

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
