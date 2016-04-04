Ext.define('PBPcm.store.ReportStore', {
    extend: 'Ext.data.Store',
    model: 'PBPcm.model.ReportModel',
    autoLoad:true,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read:''
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});