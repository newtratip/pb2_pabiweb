Ext.define('PB.view.common.SearchOtherUserDlg', {
	extend : 'Ext.window.Window',
	alias : 'widget.searchOtherUserDlg',
	
	initComponent: function(config) {
		var me = this;
		
		var lbw = 100;
		
		Ext.applyIf(me, {
		        renderTo : Ext.getBody(),
	            modal: true,
	            width: 420,
	            height: 170,
	            layout: 'fit',
	            resizable: false,
	            items : [{
		        	xtype : 'form',
			        itemId : 'formDetail',
			        border : 0,
			        items:[{
					    xtype: 'textfield',
					    fieldLabel : mandatoryLabel('ชื่อ - นามสกุล'), 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'name',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false,
					    listeners:{
							afterrender:function(txt) {
								Ext.defer(function(){
									txt.focus();
								},100);
							}
						},
					    value:me.rec ? replaceIfNull(me.rec.get("name"), null) : null 
					},{
					    xtype: 'textfield',
					    fieldLabel : mandatoryLabel('ตำแหน่ง'), 
					    labelWidth: lbw,
					    anchor:"-10",
					    hideTrigger:true,
					    name : 'position',
					    msgTarget: 'side',
					    margin: '10 0 0 10',
					    allowBlank:false,
					    value:me.rec ? replaceIfNull(me.rec.get("position"), null) : null
					}],
			        buttons : [{
			          text: 'ยืนยัน', 
			          action : 'ok',
			          iconCls:'icon_ok',
			          listeners: {
			               click: function(){
			                	this.fireEvent("confirm", this);
			               }
			          }
			        },{
			          text: 'ยกเลิก',
			          handler:this.destroy,
			          scope:this,
			          iconCls:'icon_no'
			        }]
	            }]
		});
		
        this.callParent(arguments);
	}
});		