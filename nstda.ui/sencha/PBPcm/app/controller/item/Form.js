Ext.define('PBPcm.controller.item.Form', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref: 'main',
        selector: 'pcmReqMain'
    },{
        ref: 'mainForm',
        selector: 'pcmReqMainForm'
    },{
        ref: 'dlg',
        selector: 'pcmItemDtlDlg'
    },{
        ref: 'form',
        selector: 'pcmItemDtlDlg [itemId=formDetail]'
	},{
    	ref:'uploadGrid',
		selector:'pcmReqFileTab uploadGrid'
	},{
    	ref:'grid',
		selector:'pcmReqItemTab grid'
    },{
        ref: 'hidId',     
        selector:'pcmItemDtlDlg field[name=id]'
//	},{
//        ref: 'raIsEqmt',
//        selector:'pcmItemDtlDlg [itemId=isEqmt]'
	},{
        ref: 'hidIsEqmt',
        selector:'pcmItemDtlDlg [name=isEqmt]'
    },{
        ref: 'cmbFiscalYear',
        selector:'pcmItemDtlDlg field[name=fiscalYear]'
    },{
        ref: 'cmbActGrp',
        selector:'pcmItemDtlDlg field[name=actGrp]'
    },{
        ref: 'txtDesc',
        selector:'pcmItemDtlDlg field[name=desc]'
    },{
        ref: 'cmbUnit',
        selector:'pcmItemDtlDlg field[name=unit]'
    },{
        ref: 'txtQty',
        selector:'pcmItemDtlDlg field[name=qty]'
    },{
        ref: 'txtPrc',
        selector:'pcmItemDtlDlg field[name=prc]'
    },{
        ref: 'txtCurrencyRate',
        selector:'pcmReqInfoTab field[name=currencyRate]'
    },{
        ref: 'lblGrossAmt',
        selector:'pcmReqItemTab label[name=grossAmt]'
    },{
        ref: 'lblVatAmt',
        selector:'pcmReqItemTab label[name=vatAmt]'
    },{
        ref: 'lblNetAmt',
        selector:'pcmReqItemTab label[name=netAmt]'
    },{
        ref: 'lblNetAmtCnv',
        selector:'pcmReqItemTab label[name=netAmtCnv]'
    },{
        ref: 'hidVat',     
        selector:'pcmReqItemTab field[name=vat]'
    },{
        ref: 'hidTotal',     
        selector:'pcmReqItemTab field[name=total]'
    },{
        ref: 'hidTotalCnv',     
        selector:'pcmReqItemTab field[name=totalCnv]'
    },{
        ref: 'cmbObjectiveType',     
        selector: 'pcmReqMainForm field[name=objectiveType]'
    },{
        ref: 'cmbMethod',     
        selector: 'pcmReqMainForm field[name=method]'
	},{
	    ref: 'cmtTab',
	    selector:'pcmReqCmtTab [itemId=cmtTab]'
    },{
        ref: 'chkAcrossBudget',
        selector: 'pcmReqMainForm field[name=isAcrossBudget]'
    },{
        ref: 'txtAcrossBudget',
        selector: 'pcmReqMainForm field[name=acrossBudget]'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'pcmItemDtlDlg [action=ok]': {
				click : me.ok
			},
			'pcmReqItemTab grid [action=addItem]': {
				click : me.add
			},
			'pcmReqItemTab grid':{
				edit:me.edit,
				del:me.del
			},
			'pcmReqItemTab': {
				selectVat : me.selectVat,
				itemStoreLoad:me.calSummary
			}
		});

	},
	
	DEL_MSG_KEY : 'DELETE_PCM_REQ_ITEM',
    URL : ALF_CONTEXT+'/pcm',
    MSG_URL : ALF_CONTEXT+'/pcm/message',

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
				
				rec = Ext.create('PBPcm.model.ItemGridModel',{
		    		id : maxId+1,
		    		action : 'ED' 
		    	});
				
			} else {
				rec = me.selectedRec;
			}
			
			var curRate = parseFloat(me.getTxtCurrencyRate().getValue());

			var prc = parseFloat(me.getTxtPrc().getValue());
//			var prcCnv = prc * curRate;
			var qty = parseFloat(me.getTxtQty().getValue());
			var total = prc * qty;
			
			rec.set("actGrp",me.getCmbActGrp().getRawValue());
			rec.set("actGrpId",me.getCmbActGrp().getValue()); 
			rec.set("description",me.getTxtDesc().getValue()); 
			rec.set("quantity",qty); 
			rec.set("unit",me.getCmbUnit().getRawValue());
			rec.set("unitId",me.getCmbUnit().getValue());
			rec.set("price",prc);
			rec.set("fiscalYear",me.getCmbFiscalYear().getValue() ? me.getCmbFiscalYear().getValue() : 0);
			rec.set("total",total);
			
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
	
		this.createDlg(PBPcm.Label.m.add).show();

	},
	
	createDlg:function(title, rec) {
		
		var me = this;
		
		var dialog = Ext.create('PBPcm.view.item.DtlDlg', {
		    title : title,
		    rec : rec,
		    acrossBudget : me.getChkAcrossBudget().getValue()
		});
		
		return dialog;
	},
	
	edit:function(rec) {
		var me = this;
		
		me.getGrid().getView().getSelectionModel().select(rec);
		me.selectedRec = rec;		
	
		var dialog = me.createDlg(PBPcm.Label.m.edit, rec);
		
		me.getHidId().setValue(rec.get("id"));
		me.getCmbFiscalYear().setValue(rec.get("fiscalYear").toString());
		me.getCmbActGrp().setValue(rec.get("actGrpId"));
		me.getTxtDesc().setValue(rec.get("description"));
		me.getTxtQty().setValue(rec.get("quantity"));
		me.getCmbUnit().setValue(rec.get("unitId"));
		me.getTxtPrc().setValue(rec.get("price"));
		
		dialog.show();
	},
	
	del:function(rec) {
		this.getGrid().getView().getSelectionModel().select(rec);
		
		this.selectedRec = rec;
	
		PB.Dlg.confirm('CONFIRM_'+this.DEL_MSG_KEY,this,'doDel', MODULE_PCM);
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
			grossAmt += rec.data.total;
		});
		
		var curRate = parseFloat(me.getTxtCurrencyRate().getValue());
		
		var vat = parseFloat(me.getHidVat().getValue());
		var vatAmt = grossAmt * vat;
		var netAmt = grossAmt+vatAmt;
		var netAmtCnv = netAmt * curRate;
		
		me.getLblGrossAmt().setText(Ext.util.Format.number(grossAmt, DEFAULT_MONEY_FORMAT));
		me.getLblVatAmt().setText(Ext.util.Format.number(vatAmt, DEFAULT_MONEY_FORMAT));
		me.getLblNetAmt().setText(Ext.util.Format.number(netAmt, DEFAULT_MONEY_FORMAT));
		me.getLblNetAmtCnv().setText(Ext.util.Format.number(netAmtCnv, DEFAULT_MONEY_FORMAT));
		
		me.getHidTotal().setValue(netAmt);
		me.getHidTotalCnv().setValue(netAmtCnv);
		
//		if (!me.getChkAcrossBudget().getValue()) { // not across budget
			var methodStore = me.getCmbMethod().getStore();
			methodStore.getProxy().extraParams = {
				objType : me.getCmbObjectiveType().getValue()
			}
			if (me.getHidTotal().getValue()) {
				methodStore.getProxy().extraParams.total = me.getHidTotal().getValue(); 
			}
			methodStore.load(function(s, recs) {
				var rec = methodStore.getById(parseInt(me.getCmbMethod().getValue()));
				if (!rec) {
					me.getCmbMethod().setValue(null);
					me.getCmtTab().removeAll(true);
				}
			});
//		}
		
		if (netAmt >= 100000) {
			var tbar = me.getUploadGrid().getDockedComponent(1);
			var dlBtn = tbar.down("button[action=download]");

			if (!dlBtn) {
						
				var store = Ext.create('PB.store.common.ComboBoxStore');
				store.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master2?all=false';
				store.getProxy().extraParams = {
					p1 : "type='MP'",
					orderBy : 'code',
					all : true
				}
				store.load();
				
				tbar.add({
					xtype:'tbfill'
				},{
					xtype:'combo',
					name:'mp',
					fieldLabel:'แบบฟอร์มราคากลาง',
			    	displayField:'name',
			    	valueField:'id',
			        emptyText : PB.Label.m.select,
			        store: store,
			        queryMode: 'local',
			        typeAhead:true,
			        multiSelect:false,
			        forceSelection:true,
					width:420,
					labelWidth:130,
			        listConfig : {
				    	resizable:true,
					    getInnerTpl: function () {
							return '<div>{name}</div>';
					        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
					    }
					},
			        listeners:{
						beforequery : function(qe) {
							qe.query = new RegExp(qe.query, 'i');
			//				qe.forceAll = true;
						},
			       	    select : function(combo, records, e){
			       		   	combo.fireEvent("selectMiddlePrice",combo, records, e);
			       	    }
					}			
				},{
					xtype:'button',
					text:'Download',
					iconCls:'icon_download',
					action:'download',
					margin:'0 0 0 5'
				});
			}
		}
	},
	
	selectVat:function(cmb, rec) {
		var me = this;
		
		if (rec[0].data.data.amount) {
			me.getHidVat().setValue(rec[0].data.data.amount);
		} else {
			me.getHidVat().setValue(0);
		}
		
		me.calSummary();
	}

});
