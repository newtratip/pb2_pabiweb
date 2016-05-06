Ext.define('PBPcm.model.ItemGridModel', {
    extend: 'Ext.data.Model',
    fields : [ {name : 'id'}
    		 , {name : 'isEquipment'}
    		 , {name : 'description'}
    		 , {name : 'quantity'}
    		 , {name : 'unit'}
    		 , {name : 'price'}
    		 , {name : 'priceCnv'}
    		 , {name : 'total'}
    		 , {name : 'action'}
    ]
});