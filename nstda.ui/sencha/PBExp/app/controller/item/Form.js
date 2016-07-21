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
		selector:'expBrwInfoTab grid[itemId=itemGrid]'
    },{
        ref: 'txtTotal',     
        selector:'expBrwInfoTab field[name=total]'
    },{
        ref: 'lblTotal',     
        selector:'expBrwInfoTab label[name=total]'
    },{
        ref: 'hidId',     
        selector:'expBrwItemDtlDlg field[name=id]'
    },{
        ref: 'txtActivity',
        selector:'expBrwItemDtlDlg field[name=activity]'
    },{
        ref: 'txtAmount',     
        selector:'expBrwItemDtlDlg field[name=amount]'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'expBrwItemDtlDlg [action=ok]': {
				click : me.ok
			},
			'expBrwInfoTab [action=addItem]': {
				click : me.add
			},
			'expBrwInfoTab grid[itemId=itemGrid]':{
				edit:me.edit,
				del:me.del
			},
			'expBrwInfoTab': {
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
		
		me.getLblTotal().setText(Ext.util.Format.number(total, DEFAULT_MONEY_FORMAT));
		me.getTxtTotal().setValue(total);
	}
	
});
