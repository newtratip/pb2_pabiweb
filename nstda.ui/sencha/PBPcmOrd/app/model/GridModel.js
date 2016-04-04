Ext.define('PBPcmOrd.model.GridModel', {
    extend: 'Ext.data.Model',
    fields : [ {name : 'id'}
    		 , {name : 'workflow_id'}
    		 , {name : 'workflow_ins_id'}
    		 , {name : 'approval_matrix_id'}
    		 , {name : 'requested_time'}
    		 , {name : 'reqTimeShow'}
    		 , {name : 'doc_ref'}
    		 , {name : 'folder_ref'}
    		 , {name : 'status'}
    		 , {name : 'wfStatus'}
    		 , {name : 'created_by'}
    		 , {name : 'updated_by'}
    		 , {name : 'updated_time'}
    		 , {name : 'overDue'}
    		 , {name : 'action'}
    ]
});