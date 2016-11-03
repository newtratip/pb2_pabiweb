Ext.define('PBExp.view.item.DtlDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.expBrwItemDtlDlg',
	
	initComponent: function(config) {
		var me = this;
		
		var actGrpId = me.rec ? me.rec.get("actGrpId") : null;
		
		var agstore = Ext.create('PB.store.common.ComboBoxStore',{
			autoLoad:false,
			sorters: [{
		         property: 'name',
		         direction: 'ASC'
		    }]
		});
		agstore.getProxy().api.read = ALF_CONTEXT+'/admin/main/activity/group/list';
		agstore.getProxy().extraParams = {
			query:getLang()+' '
		}
		agstore.load({params:{id:actGrpId}});
		
		var astore = Ext.create('PB.store.common.ComboBoxStore',{
			autoLoad:false,
			sorters: [{
		         property: 'name',
		         direction: 'ASC'
		    }]
		});
		astore.getProxy().api.read = ALF_CONTEXT+'/admin/main/activity/list';
		astore.getProxy().extraParams = {
			query:getLang()+' ',
			actGrpId:actGrpId
		}
		astore.load({params:{actGrpId:actGrpId}});
		
		var cstore = Ext.create('PB.store.common.ComboBoxStore',{
			autoLoad:false,
			listeners:{
				load:function(st, recs) {
					me.fireEvent("cond1Load", recs);
				}
			}
		});
		cstore.getProxy().api.read = ALF_CONTEXT+'/admin/main/expenserule/listDistinct';
		if (me.rec) {
			cstore.getProxy().extraParams = {
				id:me.rec.get("actId")
			}
			cstore.load();
		}

		var store = Ext.create('PBExp.store.item.ActivityGridStore',{autoLoad:false});
		if (me.rec) {
			store.getProxy().extraParams = {
				id:me.rec.get("actId"),
				cond:me.rec.get("condition1")
			}
			store.load();
		}
		
		var lbw = 140;
		
		Ext.applyIf(me, {
		        renderTo : Ext.getBody(),
	            modal: true,
	            width: 800,
	            height: 500,
	            layout: 'border',
	            resizable: false,
		        items : [{
		        	region:'north',
		        	xtype : 'form',
			        itemId : 'formDetail',
			        border : 0,
			        height:170,
			        items:[{
						xtype: 'hidden',
						name: 'id',
						value : me.rec ? me.rec.get("id") : null
					},{
						xtype:'combo',
						name:'actGrpId',
						fieldLabel:mandatoryLabel(PBExp.Label.i.actGrp),
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : PB.Label.m.select,
				        store: agstore,
		//		        queryMode: 'local',
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
		    	       		    me.fireEvent("selectActivityGroup",combo, rec);
		    	       	    }
						},
						value:me.rec ? me.rec.get("actGrpId") : null
					},{
						xtype:'combo',
						name:'actId',
						fieldLabel:mandatoryLabel(PBExp.Label.i.act),
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : PB.Label.m.select,
				        store: astore,
		//		        queryMode: 'local',
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
		    	       		    me.fireEvent("selectActivity",combo, rec);
		    	       	    }
						},
						value:me.rec ? me.rec.get("actId") : null
					},{
						xtype:'textfield',
						name:'activity',
						fieldLabel:mandatoryLabel(PBExp.Label.i.desc),
				        anchor:"-10",
						labelWidth:lbw,
						margin: '10 0 0 10',
						allowBlank:false,
						value:me.rec ? me.rec.get("activity") : null
					},{
						xtype:'combo',
						name:'condition1',
						fieldLabel:mandatoryLabel(PBExp.Label.i.cond),
				    	displayField:'name',
				    	valueField:'name',
				        emptyText : PB.Label.m.select,
				        store: cstore,
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
							},
							select : function(combo, rec){
		    	       		    me.fireEvent("selectCond1",combo, rec);
		    	       	    }
						},
						value:me.rec ? me.rec.get("condition1") : null
					},{
					    xtype: 'numericfield',
					    fieldLabel : mandatoryLabel(PBExp.Label.i.amt), 
					    labelWidth: lbw,
		//			    anchor:"-10",
						width:300,
					    hideTrigger:true,
					    name : 'amount',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false,
						value : me.rec ? me.rec.get("amount") : null
					}]
		        },{
		        	region:'center',
		        	xtype:'grid',
		        	margin:'5 0 0 0',
		        	columns:[
		        	     { text:PBExp.Label.i.desc, dataIndex: 'activity_name', flex:1 },
		        	     { text:PBExp.Label.i.cond, dataIndex: 'condition_1', width:120},
		        	     { text:PBExp.Label.i.pos, dataIndex: 'position', flex: 1 },
		        	     { text:PBExp.Label.i.amtAllow, dataIndex: 'amount', width:110, align:'right', xtype:"numbercolumn", format:DEFAULT_MONEY_FORMAT }
		        	],
		        	store:store
		        }],
		        buttons : [{
		          text: PB.Label.m.ok, 
	//	          disabled : true,
		          action : 'ok',
		          itemId: 'okButton',
		          iconCls:'icon_ok'
		        },{
		          text: PB.Label.m.cancel,
		          handler:this.destroy,
		          scope:this,
		          iconCls:'icon_no'
		        }]
		});
		
        this.callParent(arguments);
	}
});		