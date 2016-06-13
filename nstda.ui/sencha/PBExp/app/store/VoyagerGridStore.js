Ext.define('PBExp.store.VoyagerGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBExp.model.VoyagerGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/exp/brw/voyager/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});