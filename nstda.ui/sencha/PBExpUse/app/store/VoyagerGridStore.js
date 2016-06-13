Ext.define('PBExpUse.store.VoyagerGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBExpUse.model.VoyagerGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/exp/use/voyager/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});