Ext.define('PBPcmOrd.model.GridModel', {
    extend: 'Ext.data.Model',
    fields : [ {name : 'id'}
    		 , {name : 'objective'}
    		 , {name : 'total'}
    		 , {name : 'pr_id'}
    		 , {name : 'section_id'}
    		 , {name : 'doc_type'}
    		 , {name : 'workflow_ins_id'}
    		 , {name : 'created_time_show'}
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