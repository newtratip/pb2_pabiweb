Ext.define('PBAdmin.store.main.AuthMasterTypeCmbStore', {
    extend: 'Ext.data.Store',
    model: 'PB.model.common.ComboBoxModel',
    autoLoad:false,
    pageSize:ADMIN_PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/admin/main/master/listType'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});