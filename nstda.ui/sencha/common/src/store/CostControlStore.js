Ext.define('PB.store.common.CostControlStore', {
    extend: 'Ext.data.Store',
    model: 'PB.model.common.CostControlModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read : ALF_CONTEXT + "/admin/main/costcontrol/list"
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});