Ext.define('PBPcmOrd.store.workflow.DtlGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBPcmOrd.model.workflow.DtlGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/pcm/ord/wf/dtl/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});