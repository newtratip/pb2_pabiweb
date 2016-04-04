Ext.define('PB.store.common.ComboBoxStore', {
    extend: 'Ext.data.Store',
    model: 'PB.model.common.ComboBoxModel',
    autoLoad:true,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ''
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});