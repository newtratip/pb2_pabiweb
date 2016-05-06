Ext.define('PBPcm.store.ItemGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBPcm.model.ItemGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/pcm/req/item/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});