Ext.define('PB.store.common.UserGroupStore', {
    extend: 'Ext.data.Store',
    model: 'PB.model.common.UserGroupModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read : ALF_CONTEXT + "/admin/main/userGroup/list"
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});