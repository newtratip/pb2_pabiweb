Ext.define('PB.store.common.SectionProjectStore', {
    extend: 'Ext.data.Store',
    model: 'PB.model.common.SectionProjectModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read : ALF_CONTEXT + "/admin/main/sectionProject/list"
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});