Ext.define('PBPcm.store.CmtGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBPcm.model.CmtGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/pcm/req/cmt/dtl/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});