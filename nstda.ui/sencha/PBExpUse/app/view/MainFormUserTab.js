Ext.define('PBExpUse.view.MainFormUserTab', {
    extend: 'Ext.form.Panel',
    alias:'widget.expUseUserTab',
    
    autoScroll:true,

	initComponent: function(config) {
		var me = this;
	
		var lbw = parseInt(PBExpUse.Label.u.lbw);

		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'hbox',
				anchor:"-10",
				items:[{
					xtype:'trigger',
					name:'reqBy',
					fieldLabel:PBExpUse.Label.u.reqBy,
					labelWidth:lbw,
					margin:"5 0 0 10",
					width:250,
					triggerCls:'x-form-search-trigger',
					onTriggerClick:function(evt) {
						me.fireEvent("selectReqBy");
					},
					value:replaceIfNull(me.rec.req_by, null),
					editable:false
				},{
					xtype:'textfield',
					name:'reqByName',
					hideLabel:true,
					margin:"5 0 0 10",
					flex:1,
					value:replaceIfNull(me.rec.req_by_name, null),
					readOnly:true,
					fieldStyle:READ_ONLY
				},{
					xtype:'textfield',
					name:'reqTelNo',
					hideLabel:true,
//					fieldLabel:'โทรศัพท์',
//					labelWidth:lbw,
					margin:"5 0 0 10",
					width:300,
					value:replaceIfNull(me.rec.req_tel_no, null),
					readOnly:true,
					fieldStyle:READ_ONLY
				},{
					xtype:'textfield',
					name:'reqByDept',
					hideLabel:true,
					margin:"5 0 0 10",
					flex:1,
					value:replaceIfNull(me.rec.req_by_dept, null),
					readOnly:true,
					fieldStyle:READ_ONLY
				}]
			},{
				xtype:'container',
				layout:'hbox',
				anchor:"-10",
				items:[{
					xtype:'textfield',
					name:'reqBu',
					fieldLabel:PBExpUse.Label.u.reqBu,
					labelWidth:lbw,
					margin:"5 0 0 10",
					flex:1,
					value:replaceIfNull(me.rec.req_bu, null),
					readOnly:true,
					fieldStyle:READ_ONLY
				}]
			},{
				xtype:'container',
				layout:'hbox',
				anchor:"-10",
				items:[{
					xtype:'textfield',
					name:'reqOuName',
					fieldLabel:PBExpUse.Label.u.reqOu,
					labelWidth:lbw,
					margin:"5 0 0 10",
					flex:1,
					value:replaceIfNull(me.rec.req_ou_name, null),
					readOnly:true,
					fieldStyle:READ_ONLY
				},{
					xtype:'hidden',
					name:'reqOu',
					value:replaceIfNull(me.rec.req_ou, null)
				}]
			},{
				xtype:'datefield',
				name:'createdTime',
				fieldLabel:PBExpUse.Label.u.createdTime,
				labelWidth:lbw,
				margin:"5 0 0 10",
				width:250,
				format:'d/m/Y',
				value:me.rec.created_time ? new Date(me.rec.created_time) : new Date(),
				readOnly:true,
				fieldStyle:READ_ONLY
			},{
				xtype:'hidden',
				name:'createdBy',
				value:replaceIfNull(me.rec.created_by, null)
			},{
				xtype:'textfield',
				name:'createdByShow',
				fieldLabel:PBExpUse.Label.u.createdBy,
				labelWidth:lbw,
				margin:"5 0 0 10",
				width:600,
				value:replaceIfNull(me.rec.created_by_show, null),
				readOnly:true,
				fieldStyle:READ_ONLY
			},{
				xtype:'textfield',
				name:'telNo',
				fieldLabel:PBExpUse.Label.u.telNo,
				labelWidth:lbw,
				margin:"5 0 0 10",
				width:600,
				value:replaceIfNull(me.rec.tel_no, null),
				readOnly:true,
				fieldStyle:READ_ONLY
			}]
		});		
		
	    this.callParent(arguments);
	}
    
});
