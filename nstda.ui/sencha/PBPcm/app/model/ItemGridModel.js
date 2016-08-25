Ext.define('PBPcm.model.ItemGridModel', {
    extend: 'Ext.data.Model',
    fields : [ {name : 'id'}
    		 , {name : 'isEquipment'}
    		 , {name : 'actGrp'}
    		 , {name : 'actGrpId'}
    		 , {name : 'description'}
    		 , {name : 'quantity'}
    		 , {name : 'unit'}
    		 , {name : 'unitId'}
    		 , {name : 'price'}
    		 , {name : 'priceCnv'}
    		 , {name : 'fiscalYear'}
    		 , {name : 'total'}
    		 , {name : 'action'}
    ]
});