Ext.define('PBPcm.controller.committee.Form', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref: 'main',
        selector: 'pcmReqMain'
    },{
        ref: 'mainForm',
        selector: 'pcmReqMainForm'
    },{
        ref: 'form',
        selector: 'pcmCommitteeDtlDlg'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'pcmDetailDtlDlg [action=ok]': {
				click : me.ok
			}
		});

	},
	
	MSG_KEY : 'SAVE_REQ_COMMITTEE',
    URL : ALF_CONTEXT+'/pcm',
    MSG_URL : ALF_CONTEXT+'/pcm/message',

    ok:function() {
		console.log("OK");
	}
	
});
