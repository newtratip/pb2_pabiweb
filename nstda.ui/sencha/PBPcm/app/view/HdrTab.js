Ext.define('PBPcm.view.HdrTab', {
    extend: 'Ext.form.Panel',
    alias:'widget.pcmReqHdrTab',
    
    autoScroll:true,

	initComponent: function(config) {
		var me = this;
	
		var lbw = 120;

		Ext.applyIf(me, {
			items:[{
				xtype:'container',
				layout:'hbox',
				anchor:"-10",
				items:[{
					xtype:'trigger',
					name:'reqBy',
					fieldLabel:'พนักงานผู้ขอเบิก',
					labelWidth:lbw,
					margin:"5 0 0 10",
					width:250,
					triggerCls:'x-form-search-trigger',
					onTriggerClick:function(evt) {
						Ext.Msg.alert("Status","Search");
					}
				},{
					xtype:'textfield',
					hideLabel:true,
					margin:"5 0 0 10",
					flex:1
				},{
					xtype:'textfield',
					hideLabel:true,
					margin:"5 0 0 10",
					flex:1
				}]
			},{
				xtype:'container',
				layout:'hbox',
				anchor:"-10",
				items:[,{
					xtype:'textfield',
					name:'reqBu',
					fieldLabel:'ศูนย์',
					labelWidth:lbw,
					margin:"5 0 0 10",
					width:250
				},{
					xtype:'textfield',
					hideLabel:true,
					margin:"5 0 0 10",
					flex:1
				}]
			},{
				xtype:'container',
				layout:'hbox',
				anchor:"-10",
				items:[,{
					xtype:'textfield',
					name:'reqOu',
					fieldLabel:'หน่วยงาน',
					labelWidth:lbw,
					margin:"5 0 0 10",
					width:250
				},{
					xtype:'textfield',
					hideLabel:true,
					margin:"5 0 0 10",
					flex:1
				}]
			},{
				xtype:'textfield',
				name:'createdBy',
				fieldLabel:'ผู้บันทึกรายการ',
				labelWidth:lbw,
				margin:"5 0 0 10",
				width:600
			},{
				xtype:'datefield',
				name:'createdTime',
				fieldLabel:'วันที่บันทึกรายการ',
				labelWidth:lbw,
				margin:"5 0 0 10",
				width:600,
				value:new Date(),
				readOnly:true,
				format:'d/m/Y'
			},{
				xtype:'textfield',
				fieldLabel:'โทรศัพท์',
				labelWidth:lbw,
				margin:"5 0 0 10",
				width:600
			}]
		});		
		
	    this.callParent(arguments);
	}
    
});
