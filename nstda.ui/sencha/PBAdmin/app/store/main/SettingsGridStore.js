Ext.define('PBAdmin.store.main.SettingsGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBAdmin.model.main.MasterGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/admin/main/master/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});