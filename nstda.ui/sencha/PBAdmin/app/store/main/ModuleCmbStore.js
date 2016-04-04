Ext.define('PBAdmin.store.main.ModuleCmbStore', {
    extend: 'Ext.data.Store',
    model: 'PB.model.common.ComboBoxModel',
    autoLoad:true,
    pageSize:ADMIN_PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/admin/module/listForCmb'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});
