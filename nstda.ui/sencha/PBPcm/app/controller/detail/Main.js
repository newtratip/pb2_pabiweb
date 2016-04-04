Ext.define('PBPcm.controller.detail.Main', {
    extend: 'Ext.app.Controller',
    
	refs:[{
	    ref: 'main',
	    selector:'pcmReqMain'
	},{
    	ref:'uploadGrid',
		selector:'pcmReqFileTab uploadGrid'
	}],
	
	init:function() {
		var me = this;
		
		me.control({
			'grid [action=addDtl]': {
				click : me.add
			}
		});
	
	},

	MSG_KEY : 'DELETE_REQ_DETAIL',
	URL : ALF_CONTEXT+'/pcm/req/dtl',
	ADMIN_URL : ALF_CONTEXT+'/admin/pcm/dtl',
	MSG_URL : ALF_CONTEXT+'/pcm/message',
	
	add:function() {
		var me = this;
	
		this.createDlg('เพิ่ม').show();
		
		var tbar = me.getUploadGrid().getDockedComponent(1);
		var dlBtn = tbar.down("button[action=download]");

		if (!dlBtn) {
					
			var store = Ext.create('PB.store.common.ComboBoxStore');
			store.getProxy().api.read = ALF_CONTEXT+'/srcUrl/main/master?all=false';
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
				fieldLabel:'แบบฟอร์มราคากลาง',
		    	displayField:'name',
		    	valueField:'id',
		        emptyText : "โปรดเลือก",
		        store: store,
		        queryMode: 'local',
		        typeAhead:true,
		        multiSelect:false,
		        forceSelection:true,
				width:400,
				labelWidth:120,
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
		       	    change : function(combo, newValue, oldValue, e){
		       		   me.fireEvent("selectObjective",combo, newValue, oldValue);
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
	},
	
	createDlg:function(title) {
		
		var me = this;
		
		var dialog = Ext.create('PBPcm.view.detail.DtlDlg', {
		    title : title
		});
		
		return dialog;
	}

});
