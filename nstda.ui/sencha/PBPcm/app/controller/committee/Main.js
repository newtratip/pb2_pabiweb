Ext.define('PBPcm.controller.committee.Main', {
    extend: 'Ext.app.Controller',
    
	refs:[{
	    ref: 'main',
	    selector:'pcmReqMain'
	},{
	    ref: 'cmtMain',
	    selector:'pcmReqCmtTab'
	},{
	    ref: 'cmtTab',
	    selector:'pcmReqCmtTab tabpanel'
	}],
	
	init:function() {
		var me = this;
		
		me.control({
			'tabpanel [action=addCmt]': {
				click : me.add
			},
			'tabpanel field[name=method]': {
				select : me.methodSelect
			}
		});
	
	},

	MSG_KEY : 'DELETE_REQ_COMMITTEE',
	URL : ALF_CONTEXT+'/pcm/req/cmt',
	ADMIN_URL : ALF_CONTEXT+'/admin/pcm/cmt',
	MSG_URL : ALF_CONTEXT+'/pcm/message',
	
	add:function() {
		this.createDlg('เพิ่ม').show();
	},
	
	createDlg:function(title) {
		
		var me = this;
		
		var dialog = Ext.create('PBPcm.view.committee.DtlDlg', {
		    title : title
		});
		
		return dialog;
	},
	
	methodSelect:function(cmb, rec) {
		var me = this;
		var cmtTab = me.getCmtTab();
//		var tabCnt = parseInt(rec[0].data.data.FLAG1);
		
		while(cmtTab.items.items.length > 0) {
			cmtTab.remove(cmtTab.items.items[0], true);
		}
		
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
			  { text: 'ชื่อ',  dataIndex: 'name', flex:1},        
			  { text: 'นามสกุล',  dataIndex: 'surname', flex:1},         
			  { text: 'ประธาน',  dataIndex: 'chairman', width:100}
		);
		
		var data = rec[0].data.data;

		for(var i=1;i <= 4; i++) {
			var cmtTitle = data["committee"+i];
			if (cmtTitle) {
				cmtTab.add({
					xtype:'grid',
					title:cmtTitle,
					columns : columns,
					tbar:[{
						xtype : 'tbfill'
				    },{
		        		xtype: 'button',
		                text: "Add",
		                iconCls: "icon_add",
		                action:'addCmt',
		                listeners: {
		//                    click: function(){
		//		    			alert("Add");
		//                    }
		                }
		        	}]
				});
			}
		}
		
		if (data.cond2) {
			
			var store = Ext.create('PB.store.common.ComboBoxStore',{autoLoad:false});
			
			var conds = data.cond2.split("\n");
			
			var cond2Title = conds[0];
			
			for(var i=1; i<conds.length; i++) {
				if ("0123456789".indexOf(conds[i].substring(0,1)) >= 0) { // start with Number
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
				preCond2:cond2Title
			});
		}
		
	}

});
