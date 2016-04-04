Ext.define('PBPcm.store.DtlGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBPcm.model.DtlGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/pcm/req/dtl/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});