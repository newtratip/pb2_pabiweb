function renderComboboxLookup(Id,v,n,r,url,type,width){
	var store = Ext.create('BG.store.common.ComboBoxStore',{autoLoad:false});
	proxy  = store.getProxy();
	proxy.api.read = ALF_CONTEXT+url;
	proxy.extraParams = {
		t : type
    };
	store.load();
	
	var w = parseInt(width);
	
	r = (r=='true');
	
	var field = document.getElementsByName(n)[0];
	Ext.create('Ext.form.field.ComboBox',{
			allowBlank : false,
			name : Id,
			itemId : Id,
			editable : false,
			emptyText : EMTY_TEXT_COMBO,
			store: store,
			queryMode: 'local',
			displayField: 'name',
			valueField: 'code',
			value : v,
			height:23,
			width:w,
			matchFieldWidth:true,
			readOnly : r,
			listeners: {
				select : function(combo, records, eOpts){
					field.value = combo.getValue();
				},
				boxready:function(cmb, e) {
					cmb.inputEl.dom.style.width = (w-23)+"px";
				}
			},
			renderTo: Ext.Element.get(Id)
	});

}