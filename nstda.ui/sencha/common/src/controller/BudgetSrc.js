Ext.define('PB.controller.common.BudgetSrc', {
    extend: 'Ext.app.Controller',

    refs:[{
    	ref:'dlg',
    	selector:'searchBudgetSrcDlg'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'searchBudgetSrcDlg button': {
				searchBudgetSrc : me.search
			},
			'searchBudgetSrcDlg textfield': {
				searchBudgetSrc : me.search
			},
			'searchBudgetSrcDlg button[action=ok]':{
				confirmBudgetSrc:me.ok
			},
			'searchBudgetSrcDlg':{
				selectRadio:me.selectRadio
			}
		});
		

	},
	
	getGrid:function(sender) {
		return sender.up("window").down("grid");
	},
	
	getSearchTerm:function(sender) {
		return sender.up("window").down("field[itemId=searchTerm]");
	},
	
	getType:function(sender) {
		return sender.up("window").down("field[name=type]");
	},
	
    search : function(sender, v) {
		var me = this;
	
		var store = me.getGrid(sender).getStore();
		
		store.getProxy().extraParams = {
			s:me.getSearchTerm(sender).getValue(),
			t:v ? v : me.getType(sender).getGroupValue()
		}
		
		store.load();
	},
	
	ok:function(btn) {
		var me = this;
		
		var ids = getRadioValue("id").split("|");
		var type = me.getType(btn).getGroupValue();
		var t = {"U":PB.Label.m.section,"P":PB.Label.m.project,"A":PB.Label.b.invAsset,"B":PB.Label.b.constr};
		var typeName = t[type];
		
		me.getDlg().callback(ids, type, typeName);
		me.getDlg().close();
	},
	
	selectRadio:function(rad, v) {
		var grid = this.getDlg().down("grid");
		var columns;
		if(v=="U") {
//			grid.headerCt.getHeaderAtIndex(1).setText("ศูนย์");
//			grid.headerCt.getHeaderAtIndex(2).setText("หน่วยงาน");
			
			columns = [
	        	     { text:'', dataIndex: 'id', width: 50, align:'center', renderer:
	        	    	 function(v) {
	        	    		return '<input type="radio" name="id" value="'+v+'"/>'; 
	        	    	 }
	        	     },
	        	     { text:PB.Label.m.org, dataIndex: 'type', width: 150 },
	        	     { text:PB.Label.b.sectName, dataIndex: 'name', flex:1 },
	        	     { text:'Cost Center', dataIndex: 'cc', flex:1 }
	        ];

	    }
	    else
		if (v=="P") {
//			grid.headerCt.getHeaderAtIndex(1).setText("รหัส");
//			grid.headerCt.getHeaderAtIndex(2).setText("ชื่อ");
			
			columns = [
	        	     { text:'', dataIndex: 'id', width: 50, align:'center', renderer:
	        	    	 function(v) {
	        	    		return '<input type="radio" name="id" value="'+v+'"/>'; 
	        	    	 }
	        	     },
	        	     { text:PB.Label.m.org, dataIndex: 'org', width: 100 },
	        	     { text:PB.Label.b.projName, dataIndex: 'name', flex:1 },
	        	     { text:PB.Label.b.pm, dataIndex: 'cc', flex:1 }
	        ];
		}
	    else
		if (v=="A") {
			columns = [
	        	     { text:'', dataIndex: 'id', width: 50, align:'center', renderer:
	        	    	 function(v) {
	        	    		return '<input type="radio" name="id" value="'+v+'"/>'; 
	        	    	 }
	        	     },
	        	     { text:PB.Label.m.org, dataIndex: 'org', width: 100 },
	        	     { text:PB.Label.b.asset, dataIndex: 'name', flex:1 },
	        	     { text:PB.Label.b.sectLabName, dataIndex: 'type', width: 100 },
	        	     { text:PB.Label.b.researchU, dataIndex: 'a', width: 100 },
	        	     { text:PB.Label.b.bamt, dataIndex: 'cc', flex:1 }
	        ];
		}
	    else
		if (v=="B") {
			columns = [
	        	     { text:'', dataIndex: 'id', width: 50, align:'center', renderer:
	        	    	 function(v) {
	        	    		return '<input type="radio" name="id" value="'+v+'"/>'; 
	        	    	 }
	        	     },
	        	     { text:PB.Label.m.org, dataIndex: 'org', width: 100 },
	        	     { text:PB.Label.b.cpnp, dataIndex: 'name', flex:1 },
	        	     { text:PB.Label.b.sectName, dataIndex: 'cc', flex:1 }
	        ];
		}
		
		grid.reconfigure(undefined,columns);
		this.search(grid, "Z");
	}

});