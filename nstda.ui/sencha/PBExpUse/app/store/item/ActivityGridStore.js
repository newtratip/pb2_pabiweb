Ext.define('PBExpUse.store.item.ActivityGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBExpUse.model.item.ActivityGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/admin/main/expenserule/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});