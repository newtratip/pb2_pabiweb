Ext.define('PBPcm.controller.file.Main', {
    extend: 'Ext.app.Controller',
    
	refs:[{
	    ref: 'main',
	    selector:'pcmReqMain'
	}],
	
	init:function() {
		var me = this;
		
		me.control({
			'grid [action=download]': {
				click : me.download
			}
		});
	
	},

	MSG_KEY : 'DELETE_REQ_DETAIL',
	URL : ALF_CONTEXT+'/pcm/req/dtl',
	ADMIN_URL : ALF_CONTEXT+'/admin/pcm/dtl',
	MSG_URL : ALF_CONTEXT+'/pcm/message',
	
	download:function() {
		var me = this;
	
		alert("download");
	}

});
