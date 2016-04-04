Ext.define('PBPcm.store.workflow.AssigneeGridStore', {
    extend: 'Ext.data.Store',
    model: 'PBPcm.model.workflow.AssigneeGridModel',
    autoLoad:false,
    pageSize:PAGE_SIZE,

    proxy: {
        type: 'ajax',
        timeout: GRID_TIME_OUT,
        api: {
        	read: ALF_CONTEXT+'/pcm/wf/assignee/list'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});