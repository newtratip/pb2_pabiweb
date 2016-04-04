function renderCombobox(Id,v,n,r,url,type,label,m){
	var store = Ext.create('BG.store.common.ComboBoxStore',{storeId:'store',autoLoad:false});
	proxy  = store.getProxy();
	proxy.api.read = ALF_CONTEXT+url;
	proxy.extraParams = {
		type : type,
		t : type
    };
	store.load();	
	

	if(r=='true'){
		r = true;
	}else{
		r= false;
	}
	
	if(m=='true'){
		m = false;
	}else{
		m= true;
	}
	
	
	var field = document.getElementsByName(n)[0];
	Ext.create('Ext.container.Container',{
		items : [{
			xtype : 'form',
			border : 0,
			itemId : 'formInfo',
			//layout: {
	       //     type: 'vbox',
	       //     align: 'stretch'  // Child items are stretched to full width
	       // },
			fieldDefaults: {
		        labelWidth: 160,
		        labelAlign : 'left',
		        height: 42,
		        anchor: '97%'
		    },
			items : [{
			    	xtype : 'combo',
			    	labelAlign : 'top',
			    	allowBlank : m,
			    	fieldLabel : label,
			    	readOnly : r,
			    	//flex : 1,
			    	name : Id,
			    	itemId : Id,
					editable : false,
					emptyText : EMTY_TEXT_COMBO,
					store: store,
					queryMode: 'local',
					displayField: 'name',
					valueField: 'code',
			    	//height:23,
					//width:100,
			    	//matchFieldWidth:true,
					value : v,
					listeners: {
						select : function(combo, records, eOpts){
							field.value = combo.getValue();
						},
						boxready:function(cmb, e) {
							//cmb.inputEl.dom.style.width = (100-23)+"px";
						}
					}
				}]
			}],
	    renderTo: Ext.Element.get(Id)
	});
}