Ext.define('PBPcm.view.item.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.pcmItemDtlDlg',
	
	initComponent: function(config) {
		var me = this;
		
		var fstore = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
		fstore.getProxy().api.read = ALF_CONTEXT+'/admin/main/fiscalyear/list';
		fstore.load();		
		
		var astore = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
		astore.getProxy().api.read = ALF_CONTEXT+'/admin/main/activity/group/list';
		if (me.rec) {
			astore.getProxy().extraParams = {
				query : getLang()+' '+me.rec.get('actGrp')
			}
		}
		astore.load();		
		
		var store = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
		store.getProxy().api.read = ALF_CONTEXT+'/admin/main/product/uom/list';
		store.getProxy().extraParams = {
		}
		store.load();
		
		var lbw = 170;
		
		Ext.applyIf(me, {
		        renderTo : Ext.getBody(),
	            modal: true,
	            width: 520,
	            height: 370,
	            layout: 'fit',
	            resizable: false,
	            items : [{
		        	xtype : 'form',
			        itemId : 'formDetail',
			        border : 0,
			        items:[{
						xtype: 'hidden',
						name: 'id'
					 },{
						 xtype:'hidden',
						 name:'isEqmt',
						 value:'0'
//					 },{
//					    xtype: 'radiogroup',
//					    itemId:'isEqmt',
//					    fieldLabel : 'ครุภัณฑ์',
//					    columns:2,
//					    labelWidth: lbw,
//					    msgTarget: 'side',
//					    margin: '10 0 0 10',
//					    anchor:"-10",
//					    items:[
//					           {name:'isEqmt',boxLabel:'ไม่ใช่',inputValue:'0',checked:true},
//					           {name:'isEqmt',boxLabel:'ใช่',inputValue:'1'}
//					    ]
					},{
						xtype:'combo',
						name:'fiscalYear',
						fieldLabel:mandatoryLabel(PBPcm.Label.t.fiscalYear),
				    	displayField:'name',
				    	valueField:'name',
				        emptyText : PB.Label.m.select,
				        store: fstore,
//				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
						allowBlank:false,
				        listConfig : {
						    getInnerTpl: function () {
								return '<div>{name}</div>';
						        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
						    }
						},
				        listeners:{
							beforequery : function(qe) {
								qe.query = getLang()+" "+qe.query;
							},
							afterrender:function(cmb) {
								Ext.defer(function(){
									cmb.focus();
								},300);
							}
						},
					    hidden:!me.acrossBudget,
					    disabled:!me.acrossBudget
					},{
						xtype:'combo',
						name:'actGrp',
						fieldLabel:mandatoryLabel(PBPcm.Label.t.actGrp),
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : PB.Label.m.select,
				        store: astore,
//				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
						allowBlank:false,
				        listConfig : {
						    getInnerTpl: function () {
								return '<div>{name}</div>';
						        //return '<div>{name}<tpl if="id != \'\'"> ({id})</tpl></div>';
						    }
						},
				        listeners:{
							beforequery : function(qe) {
								qe.query = getLang()+" "+qe.query;
							},
							select : function(combo, rec){
		    	       		    me.fireEvent("selectActGrp",combo, rec);
		    	       	    },
							afterrender:function(cmb) {
								Ext.defer(function(){
									cmb.focus();
								},100);
							}
						}
//						,value:me.rec ? me.rec.get("actGrpId") : null
					},{
					    xtype: 'textfield',
					    fieldLabel : mandatoryLabel(PBPcm.Label.t.name), 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'desc',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false
					},{
					    xtype: 'numericfield',
					    fieldLabel : mandatoryLabel(PBPcm.Label.t.qty), 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'qty',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false
					},{
						xtype:'combo',
						name:'unit',
						fieldLabel:mandatoryLabel(PBPcm.Label.t.uom),
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : PB.Label.m.select,
				        store: store,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
						allowBlank:false,
				        listConfig : {
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
					},{
					    xtype: 'numericfield',
					    fieldLabel : mandatoryLabel(PBPcm.Label.t.dlg_prc), 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'prc',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false
					}],
			        buttons : [{
			          text: PBPcm.Label.m.save, 
//			          disabled : true,
			          action : 'ok',
			          itemId: 'okButton',
			          iconCls:'icon_ok'
			        },{
			          text: PBPcm.Label.m.cancel,
			          handler:this.destroy,
			          scope:this,
			          iconCls:'icon_no'
			        }]
	            }]
		});
		
        this.callParent(arguments);
	}
});		