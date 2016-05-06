/*
 * 	- CmtTab
 * 		- committee.DtlDlg
 * 		- committee.Cond2Tab
 */
Ext.define('PBPcm.view.MainFormCmtTab', {
    extend: 'Ext.panel.Panel',
    alias:'widget.pcmReqCmtTab',

    layout:'fit',
    autoScroll:true,

	initComponent: function(config) {
		var me = this;
		
		var methodStore = Ext.create('PB.store.common.ComboBoxStore');
		methodStore.getProxy().api.read = ALF_CONTEXT+'/pcm/req/cmt/list';
		methodStore.getProxy().extraParams = {
			objType : me.rec.objective_type ? me.rec.objective_type : "blank" 
		}
		methodStore.load();		
	
		Ext.applyIf(me, {
			tbar : [{
				xtype:'combo',
				name:'method',
				fieldLabel:'วิธีการจัดหา',
		    	displayField:'name',
		    	valueField:'id',
		        emptyText : "โปรดเลือก",
		        store: methodStore,
		        queryMode: 'local',
		        typeAhead:true,
		        multiSelect:false,
		        forceSelection:true,
				labelWidth:80,
				width:800,
				margin:'0 0 0 10',
		        listConfig : {
			    	resizable:true,
			    	minWidth:800,
			    	width:800,
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
					afterrender:function(cmb) {
						if (me.rec.method) {
							var rec = methodStore.getById(me.rec.method);
							
							cmb.fireEvent("select", cmb, [rec], me.rec);
						}
					}
				},
				value:replaceIfNull(me.rec.method, null)
		    }],
			items:[{
				xtype:'tabpanel',
				itemId:'cmtTab'
			}]			
		});		
		
	    this.callParent(arguments);
	}
    
});
