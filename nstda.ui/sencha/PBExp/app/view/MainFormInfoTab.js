Ext.define('PBExp.view.MainFormInfoTab', {
    extend: 'Ext.form.Panel',
    alias:'widget.expBrwInfoTab',
    
    autoScroll:true,

	initComponent: function(config) {
		var me = this;
		
		var store = Ext.create('PB.store.common.ComboBoxStore');
		store.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
		store.getProxy().extraParams = {
			p1 : "type='BRW_TYPE'",
			orderBy : 'flag1',
			all : true,
			lang : getLang()
		}
		store.load();
		
//		var typeStore = Ext.create('PB.store.common.ComboBoxStore');
//		typeStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/activity/list';
//		typeStore.getProxy().extraParams = {
//		    name : '',
//			all : true
//		}
//		typeStore.load();
		
		var bankStore = Ext.create('PB.store.common.ComboBoxStore');
		bankStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/bank/list';
		bankStore.getProxy().extraParams = {
			all : true
		}
		bankStore.load();
		
		var ogridStore = Ext.create('PBExp.store.OldGridStore',{storeId:'oldExpBrwGridStore',autoLoad:false});
		
		var lbw = 160;
		
		var ocolumns = [{
        	dataIndex: 'name',
        	text: '', 
        	flex:1
	    },{
	        dataIndex: 'number',
        	text: PBExp.Label.n.oweDocNo, 
        	flex:1,
        	align:'center'
	    },{
	    	xtype:'numbercolumn',
	        dataIndex: 'waitamt',
        	text: PBExp.Label.n.oweWait, 
        	flex:1,
        	align:'right',
        	format:DEFAULT_MONEY_FORMAT        		
	    },{
	    	xtype:'numbercolumn',
	        dataIndex: 'balance',
        	text: PBExp.Label.n.oweBalance, 
        	flex:1,
        	align:'right',
        	format:DEFAULT_MONEY_FORMAT
	    }];
		
		var gridStore = Ext.create('PBExp.store.ItemGridStore',{storeId:'expBrwItemGridStore',autoLoad:false});
		gridStore.getProxy().extraParams = {
			id : me.rec.id
		}

		var columns = [{
	        		xtype: 'actioncolumn',
		        	dataIndex: 'action',
		        	text: '',
		        	align:'center',
		            width: 80,
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
				{ text: PBExp.Label.n.item,  dataIndex: 'activity', flex:1},
				{ text: PBExp.Label.n.amount,  dataIndex: 'amount', width:180, align:'right', xtype: 'numbercolumn', format:'0,000.00'}
		];		
		
		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'hbox',
				margin:"5 0 0 10",
				anchor:"-10",
				items:[{
					xtype:'combo',
					name:'objectiveType',
					fieldLabel:mandatoryLabel(PBExp.Label.n.objType),
					errLabel:PBExp.Label.n.err_objType,
			    	displayField:'name',
			    	valueField:'id',
			        emptyText : "โปรดเลือก",
			        store: store,
			        queryMode: 'local',
			        typeAhead:true,
			        multiSelect:false,
			        forceSelection:true,
					width:lbw+180,
					labelWidth:lbw,
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
	    	       	    change : function(combo, newValue, oldValue, e){
	    	       		   me.fireEvent("selectObjective",combo, newValue, oldValue);
	    	       	    }
					},
					value:replaceIfNull(me.rec.objective_type, null)
				},{
					xtype:'textfield',
					name:'objective',
					fieldLabel:mandatoryLabel(PBExp.Label.n.obj),
					labelWidth:160,
					margin:"0 0 0 15",
					flex:1,
					allowBlank:false,
					value:replaceIfNull(me.rec.objective, null)
				}]
			},{
				xtype:'container',
				layout:'hbox',
				anchor:'-10',
				items:[{
					xtype:'hidden',
					name:'budgetCcType',
					value:replaceIfNull(me.rec.budget_cc_type, null)
				},{
					xtype:'hidden',
					name:'budgetCc',
					value:replaceIfNull(me.rec.budget_cc, null)
				},{
					xtype:'trigger',
					name:'budgetCcTypeName',
					fieldLabel:mandatoryLabel(PBExp.Label.n.budgetSrc),
					width:lbw+180,
					labelWidth:lbw,
					margin:"5 0 0 10",
					triggerCls:'x-form-search-trigger',
					editable:false,
					onTriggerClick:function(evt) {
						me.fireEvent("selectBudgetCc");
					},
					value:replaceIfNull(me.rec.budget_cc_type_name, ''),
					allowBlank:false
				},{
					xtype:'textfield',
					name:'budgetCcName',
					hideLabel:true,
					flex:1,
					margin:'5 0 0 10',
					value:replaceIfNull(me.rec.budget_cc_name, ''),
					readOnly:true,
					fieldStyle:READ_ONLY
				},{
					xtype:'hidden',
					name:'total',
					value:replaceIfNull(me.rec.total, 0)
				}]
			},{
				xtype:'panel',
				title:PBExp.Label.n.oweTitle,
				layout:'border',
				margin:'5 0 0 0',
				height:140,
				items:[{
					region:'center',
					xtype:'grid',
					columns:ocolumns,
					store:ogridStore
				},{
					region:'east',
					xtype:'panel',
					itemId:'oldAV',
					width:500,
					layout:'fit',
					split:true,
					collapsible:true,
					title:mandatoryLabel(PBExp.Label.n.reason),
					items:[{
						xtype:'textarea',
						name:'reason',
						hideLabel:true,
						fieldLabel:PBExp.Label.n.reason,
						margin:"0 0 0 0",
//						fieldLabel:,
//						labelWidth:130,
//						margin:"5 5 5 10",
						value:replaceIfNull(me.rec.reason, null),
						allowBlank:false,
						maxLength:255
					}]
				}]
			},{
				title:PBExp.Label.n.itemTitle,
				margin:'0 0 0 0',
				xtype:'grid',
				itemId:'itemGrid',
				columns:columns,
				store:gridStore,
				height:HEIGHT-445,
			    header:{
					titlePosition:0,
					items:[{
				    	xtype:'tbfill'
				    },{
		        		xtype: 'button',
		                text: PB.Label.m.add,
		                iconCls: "icon_add",
		                action:'addItem'
		        	}]
			    },
			    bbar:{
			    	items:[{
			    		xtype:'tbfill'
			    	},{
			    		xtype:'label',
			    		text:PBExp.Label.n.total
			    	},{
			    		xtype:'label',
			    		name:'total',
			    		text:'0.00',
			    		style:'text-align:right;',
			    		margin:'0 5 0 170'
			    	}]
			    }
			},{
				xtype:'panel',
				title:PBExp.Label.n.methodTitle,
				margin:'0 0 0 0',
				items:[{
					xtype:'radio',
					name:'bankType',
					boxLabel:PBExp.Label.n.bankBbl,
					inputValue:'0',
					margin:'5 0 0 10',
					checked:replaceIfNull(me.rec.bank_type, "0") == "0", 
					listeners:{
						change:function(rad, newV, oldV) {
							if (newV) {
								me.fireEvent("selectMainBank",rad, "0");
							}
						}
					}
				},{
					xtype:'container',
					layout:'hbox',
					margin:"0 0 5 5",
					anchor:"-10",
					items:[{
						xtype:'radio',
						name:'bankType',
						boxLabel:PBExp.Label.n.bank,
						inputValue:'1',
						margin:'5 0 0 5',
						checked:replaceIfNull(me.rec.bank_type, "0") == "1",
						listeners:{
							change:function(rad, newV, oldV) {
								if (newV) {
									me.fireEvent("selectMainBank",rad, "1");
								}
							}
						}
					},{
						xtype:'combo',
						name:'bank',
						margin:'5 0 0 5',
						hideLabel:true,
				    	displayField:'name',
				    	valueField:'id',
				        emptyText : "โปรดเลือก",
				        store: bankStore,
				        queryMode: 'local',
				        typeAhead:true,
				        multiSelect:false,
				        forceSelection:true,
						flex:1,
						labelWidth:lbw,
						allowBlank:true,
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
						},
						value:replaceIfNull(me.rec.bank, null)						
					},{
						xtype:'label',
						html:'<font color="red">'+PBExp.Label.n.bankWarn+'</font>',
						margin:'5 10 0 5'
					}]
				}]
			}]
		});		
		
	    this.callParent(arguments);
	    
		Ext.apply(gridStore, {pageSize:PAGE_SIZE});
		gridStore.load({
			params:{id:me.rec.id},
			callback:function() {
				me.fireEvent("itemStoreLoad");
			}
		});
		
		Ext.apply(ogridStore, {pageSize:PAGE_SIZE});
		ogridStore.load({
			callback:function(r) {
				me.fireEvent("oldStoreLoad",r);
			}
		});
	},
	
	listeners:{
		added:function() {
			var me = this;
			
			me.fireEvent("selectMainBank", me.down("radio[name=bankType]"), replaceIfNull(me.rec.bank_type, "0"));
		}
	}	
    
});
