Ext.define('PB.store.common.FileStore', {
    extend: 'Ext.data.Store',
    model: 'PB.model.common.FileModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	//read: CONTEXT+'/addDocument'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});