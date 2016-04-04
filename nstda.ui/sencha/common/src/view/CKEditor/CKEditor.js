Ext.define('Ext.ux.CKEditor', {    
	extend : 'Ext.form.field.TextArea',
    alias : 'widget.ckeditor',
    xtype: 'ckeditor',
    initComponent : function(){
        this.callParent(arguments);
        this.on('afterrender', function(){
            Ext.apply(this.CKConfig ,{
                    height : this.getHeight()
            });
            this.editor = CKEDITOR.replace(this.inputEl.id,this.CKConfig);
            this.editorId =this.editor.id;
            if (this.tmpValue) {
            	this.editor.setData(this.tmpValue);
            	this.tmpValue = null;
            }
        },this);
    },
    onRender : function(ct, position){
        if(!this.el){
            this.defaultAutoCreate ={
                tag : 'textarea',
                autocomplete : 'off'
            };
        }
        this.callParent(arguments)
    },
    setValue  : function(value){
        this.callParent(arguments);
        if(this.editor){
            this.editor.setData(value);
        } else {
        	this.tmpValue = value;
        }
        	
    },
    getRawValue: function(){
        if(this.editor){
            return this.editor.getData()
        }else{
        	if (this.tmpValue) {
        		return this.tmpValue;
        	}
        	else {
        		return ''
        	}
        }
    },
    CkConfig: {}
});

CKEDITOR.on('instanceReady',function(e){
    var o = Ext.ComponentQuery.query('ckeditor[editorId="'+ e.editor.id +'"]'),
    comp = o[0];
    e.editor.resize(comp.getWidth(), comp.getHeight())
    comp.on('resize',function(c,adjWidth,adjHeight){
        c.editor.resize(adjWidth, adjHeight)
    })
});
