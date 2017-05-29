Ext.define('PB.controller.common.OtherUser', {
    extend: 'Ext.app.Controller',

    refs:[{
    	ref:'dlg',
    	selector:'searchOtherUserDlg'
    },{
    	ref:'form',
		selector:'#formDetail'
	},{
    	ref:'txtTitle',
    	selector:'searchOtherUserDlg field[name=title]'
	},{
    	ref:'txtFName',
    	selector:'searchOtherUserDlg field[name=fname]'
	},{
    	ref:'txtLName',
    	selector:'searchOtherUserDlg field[name=lname]'
	},{
    	ref:'txtSection',
    	selector:'searchOtherUserDlg field[name=section]'
    },{
    	ref:'txtPosition',
    	selector:'searchOtherUserDlg field[name=position]'
    },{
    	ref:'radDestType',
    	selector:'searchOtherUserDlg field[name=destType]'
    },{
    	ref:'cmbDestination',
    	selector:'searchOtherUserDlg field[itemId=destination]'
    },{
    	ref:'txtRoute',
    	selector:'searchOtherUserDlg field[name=route]'
    },{
    	ref:'txtDepart',
    	selector:'searchOtherUserDlg field[name=depart]'
    },{
    	ref:'txtArrive',
    	selector:'searchOtherUserDlg field[name=arrive]'
    },{
    	ref:'txtCls',
    	selector:'searchOtherUserDlg field[name=cls]'
    },{
    	ref:'txtAmount',
    	selector:'searchOtherUserDlg field[name=amount]'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'searchOtherUserDlg': {
				selectDestination : me.selectDestination
			},
			'searchOtherUserDlg button[action=ok]':{
				confirm:me.ok
			}
		});
	},
	
	ok:function(btn) {
		var me = this;
		
		if (validForm(me.getForm())) {
			var r = Ext.create("PB.model.common.EmployeeUserModel");
			
			r.data.title = me.getTxtTitle().getValue();
			r.data.fname = me.getTxtFName().getValue();
			r.data.lname = me.getTxtLName().getValue();
			
			if (me.getDlg().needPosition) {
				r.data.utype = me.getTxtSection().getValue();
				r.data.position = me.getTxtPosition().getValue();
			}
			
			if (me.getDlg().needFootPrint) {
				r.data.desttype = me.getRadDestType().getGroupValue();
				r.data.destination = me.getCmbDestination().getValue();
				r.data.route = me.getTxtRoute().getValue();
				r.data.depart = me.getTxtDepart().getValue();
				r.data.arrive = me.getTxtArrive().getValue();
				r.data.cls = me.getTxtCls().getValue();
				r.data.amount = me.getTxtAmount().getValue();
			}
			
			if (!me.getDlg().validateCallback || me.getDlg().validateCallback(r)) {
				me.getDlg().callback(me.getDlg().rec ? me.getDlg().rec.id : null, r);
				me.getDlg().close();
			}
		}
	},
	
	selectDestination:function(rad, v) {
		var me = this;
		var type = v ? "D" : "I";

		var store = me.getCmbDestination(rad).getStore();
		store.getProxy().extraParams = {
			t:type
		}
		store.load();
	}	

});