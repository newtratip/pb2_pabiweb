Ext.define('PBAdmin.store.main.GroupStore', {
    extend: 'Ext.data.Store',
    model: 'PBAdmin.model.main.GroupModel',
    autoLoad:true,
    pageSize:ADMIN_PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/admin/main/group/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});