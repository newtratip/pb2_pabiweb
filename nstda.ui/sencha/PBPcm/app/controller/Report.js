Ext.define('PBPcm.controller.Report', {
    extend: 'Ext.app.Controller',

    refs:[{
        ref: 'main',
        selector: 'pcmReqMain'
    },{
        ref: 'reportForm',
        selector: 'reportForm'
    },{
        ref: 'reportType',
        selector: '#reportType'
    },{
        ref: 'reportGrid',
        selector: '#reportGrid'
    },{
        ref: 'fromDate',
        selector: '#fromDate'
    },{
        ref: 'toDate',
        selector: '#toDate'
    },{
        ref: 'cmbSort',
        selector: '#cmbSort'
    },{
        ref: 'cmbRequestType',
        selector: '#cmbRequestType'
    },{
        ref: 'cmbPermanentDate',
        selector: '#cmbPermanentDate'
    },{
        ref: 'requestType',
        selector: '#requestType'
    },{
        ref: 'permanentDate',
        selector: '#permanentDate'
    },{
        ref: 'pr08',
        selector: '#p_R08'
    },{
        ref: 'pr09',
        selector: '#p_R09'
    },{
        ref: 'northPanel',
        selector: '#northPanel'
    },{
        ref: 'yearBudget',
        selector: '#yearBudget'
    },{
        ref: 'inout',
        selector: '#inout'
    },{
        ref: 'nameTrain',
        selector: '#nameTrain'
    }],
    
    init:function() {
		var me = this;
		
		me.control({
			'reportForm [action=reportSearch]': {
				click : me.reportSearch
			},
			'reportForm [action=pdf]': {
				click : me.reportSearch
			},
			'reportForm [action=excel]': {
				click : me.reportSearch
			},
			'#reportType':{
   				'select': me.selectType
//   			},
//   			'#fromDate':{
//   				'change': me.changeDate
//   			},
//   			'#toDate':{
//   				'change': me.changeDate
   			}
		});

	},
	

    URL : ALF_CONTEXT+'/pcm',
    MSG_URL : ALF_CONTEXT+'/pcm/message',
    changeDate : function(){
	
		var from = this.getFromDate();
		var to = this.getToDate();
		
		this.compareDate(from,to);

	},
	compareDate : function(from,to){
		
		var dateFrom = new Date(from.getValue());
		var dateTo = new Date(to.getValue());
		
		if(to.getValue() == null){
			
			to.setMinValue(dateFrom);
			to.validate();
			
		}else{
			
			to.setMinValue(dateFrom);
			to.validate();
			
		}
	
	},
    reportSearch : function(btn){
	
		var store = Ext.create('PBPcm.store.ReportStore',{autoLoad:false});
		var type = this.getReportType().getValue();
		var typeName = this.getReportType().getRawValue();
		
		
		var grid = this.getReportGrid();
		
		var from = this.getFromDate().getRawValue();
		var to = this.getToDate().getRawValue();
		//var sort = this.getCmbSort().getValue();
		
		if(!this.getFromDate().validate()||!this.getToDate().validate()){
			
			return false;
		}
		
		this.checkData(type);
		if(btn.text=='ค้นหา'){
			
		var C_R01 = [ { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E40\u0E2B\u0E15\u0E38\u0E01\u0E32\u0E23\u0E13\u0E4C',align:'center', dataIndex:'type_name', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
        { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'type_amount' ,align:'center', flex:1,renderer: function (v, m, r) {return '<div align="right">'+v+'</div>';}}  ];
		var C_R02 = [ { text: '\u0E17\u0E35\u0E21', dataIndex:'team_name',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		              { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'team_amount' ,align:'center', flex:1,renderer: function (v, m, r) {return '<div align="right">'+v+'</div>';}}  ];
		var C_R03 = [ { text: '\u0E23\u0E30\u0E14\u0E31\u0E1A\u0E1C\u0E25\u0E01\u0E23\u0E30\u0E17\u0E1A', dataIndex:'level_name',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'level_amount',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="right">'+v+'</div>';}}  ];
		var C_R04 = [ { text: 'No', dataIndex:'id',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E40\u0E2B\u0E15\u0E38\u0E01\u0E32\u0E23\u0E13\u0E4C', dataIndex:'type_name',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E23\u0E30\u0E14\u0E31\u0E1A\u0E1C\u0E25\u0E01\u0E23\u0E30\u0E17\u0E1A', dataIndex:'level',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E0A\u0E37\u0E48\u0E2D\u0E1C\u0E39\u0E49\u0E41\u0E08\u0E49\u0E07\u0E40\u0E2B\u0E15\u0E38',align:'center', dataIndex:'informer', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E0A\u0E37\u0E48\u0E2D\u0E1C\u0E39\u0E49\u0E23\u0E31\u0E1A\u0E41\u0E08\u0E49\u0E07\u0E40\u0E2B\u0E15\u0E38',align:'center', dataIndex:'receiver', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E0A\u0E37\u0E48\u0E2D\u0E1C\u0E39\u0E49\u0E23\u0E31\u0E1A\u0E1C\u0E34\u0E14\u0E0A\u0E2D\u0E1A',align:'center', dataIndex:'responsible', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E01\u0E33\u0E2B\u0E19\u0E14\u0E40\u0E2A\u0E23\u0E47\u0E08', dataIndex:'estimate_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E2D\u0E2D\u0E01 Incident', dataIndex:'issue_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E16\u0E32\u0E27\u0E23', dataIndex:'rc_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		 { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E16\u0E32\u0E27\u0E23(\u0E0A\u0E21.)',align:'center', dataIndex:'rc_hour', flex:1,renderer: function (v, m, r) {return '<div align="right">'+v+'</div>';}},
		 { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E0A\u0E31\u0E48\u0E27\u0E04\u0E23\u0E32\u0E27',align:'center', dataIndex:'wa_date', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}
		//, { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E40\u0E2A\u0E23\u0E47\u0E08\u0E0A\u0E31\u0E48\u0E27\u0E04\u0E23\u0E32\u0E27', dataIndex:'wa_hour', flex:1}
		, { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E0A\u0E31\u0E48\u0E27\u0E04\u0E23\u0E32\u0E27(\u0E0A\u0E21.)',align:'center', dataIndex:'wa_hour', flex:1,renderer: function (v, m, r) {return '<div align="right">'+v+'</div>';}}
		];	
		var C_R05 = [ { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E40\u0E2B\u0E15\u0E38\u0E01\u0E32\u0E23\u0E13\u0E4C',align:'center', dataIndex:'field_name', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		, { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'count',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="right">'+v+'</div>';}}  ];
		var C_R06 = [ { text: '\u0E17\u0E35\u0E21', dataIndex:'field_name',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'team_amount',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="right">'+v+'</div>';}} ];
		var C_R07 = [ { text: '\u0E23\u0E30\u0E14\u0E31\u0E1A\u0E1C\u0E25\u0E01\u0E23\u0E30\u0E17\u0E1A',align:'center', dataIndex:'field_name', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'level_amount',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="right">'+v+'</div>';}}  ];
		
		var C_R08 = [ { text: 'Change Request Id', dataIndex:'id',align:'center', flex:1}, 
		        { text: 'Change Request Type', dataIndex:'req_type',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48', dataIndex:'created_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E1C\u0E39\u0E49\u0E23\u0E49\u0E2D\u0E07\u0E02\u0E2D', dataIndex:'requester',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E40\u0E23\u0E37\u0E48\u0E2D\u0E07', dataIndex:'title',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E23\u0E30\u0E14\u0E31\u0E1A\u0E1C\u0E25\u0E01\u0E23\u0E30\u0E17\u0E1A', dataIndex:'level',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E40\u0E08\u0E49\u0E32\u0E2B\u0E19\u0E49\u0E32\u0E17\u0E35\u0E48\u0E44\u0E14\u0E49\u0E23\u0E31\u0E1A',align:'center', dataIndex:'rp_by', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E08\u0E32\u0E01\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E40\u0E1B\u0E25\u0E35\u0E48\u0E22\u0E19\u0E41\u0E1B\u0E25\u0E07', dataIndex:'from_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E16\u0E36\u0E07\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E40\u0E1B\u0E25\u0E35\u0E48\u0E22\u0E19\u0E41\u0E1B\u0E25\u0E07', dataIndex:'to_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E2D\u0E19\u0E38\u0E21\u0E31\u0E15\u0E34', dataIndex:'approve_time',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E1C\u0E39\u0E49\u0E2D\u0E19\u0E38\u0E21\u0E31\u0E15\u0E34', dataIndex:'approve_by',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: 'Change Request Report ID', dataIndex:'crpt_id',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: 'วันที่ดำเนินการเปลี่ยนแปลง', dataIndex:'rp_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}];
		
		var C_R09 = [ { xtype: 'rownumberer', width: 40, text: '\u0E25\u0E33\u0E14\u0E31\u0E1A\u0E17\u0E35\u0E48',align:'center'},
		        { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E01\u0E32\u0E23\u0E2D\u0E1A\u0E23\u0E21/\u0E2A\u0E31\u0E21\u0E19\u0E32/\u0E14\u0E39\u0E07\u0E32\u0E19',align:'center', dataIndex:'training_type', flex:1,
		        renderer: function (v, m, r) {
					
					return '<div align="left">'+v+'</div>';
				}},
		        { text: '\u0E0A\u0E37\u0E48\u0E2D\u0E2B\u0E25\u0E31\u0E01\u0E2A\u0E39\u0E15\u0E23/\u0E42\u0E04\u0E23\u0E07\u0E01\u0E32\u0E23', dataIndex:'course_name',align:'center', flex:1,
		        renderer: function (v, m, r) {
					
		        	return '<div align="left">'+v+'</div>';
				}}, 
		        { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E2B\u0E25\u0E31\u0E01\u0E2A\u0E39\u0E15\u0E23', dataIndex:'course_type',align:'center', flex:1,
		        renderer: function (v, m, r) {
					
					return '<div align="left">'+v+'</div>';
				}}, 
		        { text: '\u0E1B\u0E23\u0E30\u0E40\u0E17\u0E28', dataIndex:'country',align: 'right', flex:1,
		        renderer: function (v, m, r) {
					
					return '<div align="left">'+v+'</div>';
				}}, 
		        { text: '\u0E1B\u0E35\u0E07\u0E1A\u0E1B\u0E23\u0E30\u0E21\u0E32\u0E13', dataIndex:'budget_year',align:'center', flex:1,
		        renderer: function (v, m, r) {
					
		        	return '<div align="left">'+v+'</div>';
				}}, 
		        { text: '\u0E08\u0E32\u0E01 \u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E2D\u0E1A\u0E23\u0E21',align:'center', dataIndex:'from_date', flex:1,
		        renderer: function (v, m, r) {
					
		        	return '<div align="left">'+v+'</div>';
				}},  
		        { text: '\u0E16\u0E36\u0E07 \u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E2D\u0E1A\u0E23\u0E21',align:'center', dataIndex:'to_date', flex:1,
		        renderer: function (v, m, r) {
					
		        	return '<div align="left">'+v+'</div>';
				}},   
		
		        { text: '\u0E07\u0E1A\u0E1B\u0E23\u0E30\u0E21\u0E32\u0E13(\u0E1A\u0E32\u0E17)', dataIndex:'budget',align:'center', flex:1,
		        renderer: function (v, m, r) {
					
				    return '<div align="right">'+v+'</div>';
				}},   
		        { text: '\u0E23\u0E32\u0E22\u0E0A\u0E37\u0E48\u0E2D\u0E1C\u0E39\u0E49\u0E40\u0E02\u0E49\u0E32\u0E2D\u0E1A\u0E23\u0E21',align:'center', dataIndex:'trainee', flex:1,
		        renderer: function (v, m, r) {
							var trainee = r.get("trainee");
						    return '<div align="left">'+trainee.replace(/(?:\r\n|\r|\n)/g, '<br />')+'</div>';
						}}  ];   
			
			if(type == 'R01'){
		    	
		    	var params = {
		    			t : to,
		    			f : from,
		    			tn : typeName,
		    			mimeType: ''
		    			
			 		};
		    	store.getProxy().api.read =  ALF_CONTEXT+'/memo/listFnType';
				store.getProxy().extraParams = params;
				store.load();
				
		    	grid.reconfigure(store,C_R01);
		    }else if(type == 'R02'){
		    	var params = {
				    	t : to,
		    			f : from,
		    			tn : typeName,
				    	mimeType: ''		
					 };
				store.getProxy().api.read =  ALF_CONTEXT+'/memo/listFnTeam';
				store.getProxy().extraParams = params;
				store.load();
		    	grid.reconfigure(store,C_R02);
		    }else if(type == 'R03'){
		    	var params = {
						   t : to,
						   f : from,
						   tn : typeName,
						   mimeType: ''			
						};
				store.getProxy().api.read =  ALF_CONTEXT+'/memo/listFnLevel';
				store.getProxy().extraParams = params;
				store.load();
		    	grid.reconfigure(store,C_R03);
		    }else if(type == 'R04'){
		    	var sort = this.getCmbSort();
		    	var pmDate = this.getCmbPermanentDate();
		    	var params = {
							t : to,
							f : from,
							tn : typeName,
							pd : pmDate.getValue(),
							pdn : pmDate.getRawValue(),
							mimeType: '',
							sort : sort.getValue(),
							sortName : sort.getRawValue()
						};
				store.getProxy().api.read =  ALF_CONTEXT+'/memo/listFnHour';
				store.getProxy().extraParams = params;
				store.load();
		    	grid.reconfigure(store,C_R04);
		    }else if(type == 'R05'){
		    	grid.reconfigure(store,C_R05);
		    	var params = {
							t : to,
							f : from,
							tn : typeName,
							mimeType: ''			
						};
				store.getProxy().api.read =  ALF_CONTEXT+'/memo/listTypeChange';
				store.getProxy().extraParams = params;
				store.load();
		    }else if(type == 'R06'){
		    	var reqType = this.getCmbRequestType().getValue();
		    	var params = {
							t : to,
							f : from,
							rt : reqType,
							tn : typeName,
							mimeType: ''			
						};
				store.getProxy().api.read =  ALF_CONTEXT+'/memo/listRequestDay';
				store.getProxy().extraParams = params;
				store.load();
		    	grid.reconfigure(store,C_R06);
		    }else if(type == 'R07'){
		    	var reqType = this.getCmbRequestType().getValue();
		    	var params = {
							t : to,
							f : from,
							rt : reqType,
							tn : typeName,
							mimeType: ''			
						};
				store.getProxy().api.read =  ALF_CONTEXT+'/memo/listRequestEffect';
				store.getProxy().extraParams = params;
				store.load();
		    	grid.reconfigure(store,C_R07);
		    }else if(type == 'R08'){
		    	var reqType = this.getCmbRequestType().getValue();
		    	var sort = this.getCmbSort();
		    	var params = {
							t : to,
							f : from,
							rt : reqType,
							tn : typeName,
							mimeType: '',
							sort : sort.getValue(),
							sortName : sort.getRawValue()
						};
				store.getProxy().api.read =  ALF_CONTEXT+'/memo/listChange';
				store.getProxy().extraParams = params;
				store.load();
		    	grid.reconfigure(store,C_R08);
		    }else if(type == 'R09'){
		    	var yearBudget = this.getYearBudget().getValue();
		    	var inout = this.getInout();
		    	var nameTrain = this.getNameTrain().getValue();
		    	console.log("inout:"+inout.getValue());
		    	var params = {
							t : to,
							f : from,
							tn : typeName,
							y : yearBudget,
							i : inout.getValue(),
							country : inout.getRawValue(),
							n : nameTrain,
							mimeType: ''			
						};
		    	
				store.getProxy().api.read =  ALF_CONTEXT+'/memo/listTrain';
				store.getProxy().extraParams = params;
		    	store.load();
		    	grid.reconfigure(store,C_R09);
		    }
		    

		}else{
			
			if(type == 'R01'){
				window.open(ALF_CONTEXT+'/memo/listFnType?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName,"_blank");
			}else if(type == 'R02'){
				window.open(ALF_CONTEXT+'/memo/listFnTeam?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName,"_blank");
			}else if(type == 'R03'){
				window.open(ALF_CONTEXT+'/memo/listFnLevel?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName,"_blank");
			}else if(type == 'R04'){
				var pmDate = this.getCmbPermanentDate().getValue();
				var pmDateName = this.getCmbPermanentDate().getRawValue();
				var sort = this.getCmbSort().getValue();
				var sortName = this.getCmbSort().getRawValue();
				if(sort == null){
					sort = '';
					sortName = '';
				}
				if(pmDate == null){
					pmDate = '';
					pmDateName = '';
				}
				window.open(ALF_CONTEXT+'/memo/listFnHour?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName+'&sort='+sort+'&sortName='+sortName+'&pd='+pmDate+'&pdn='+pmDateName,"_blank");
			}else if(type == 'R05'){
				window.open(ALF_CONTEXT+'/memo/listTypeChange?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName,"_blank");
			}else if(type == 'R06'){
				var reqType = this.getCmbRequestType().getValue();
				var reqTypeName = this.getCmbRequestType().getRawValue();
				window.open(ALF_CONTEXT+'/memo/listRequestDay?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName+'&rt='+reqType+'&rtn='+reqTypeName,"_blank");
			}else if(type == 'R07'){
				var reqType = this.getCmbRequestType().getValue();
				var reqTypeName = this.getCmbRequestType().getRawValue();
				window.open(ALF_CONTEXT+'/memo/listRequestEffect?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName+'&rt='+reqType+'&rtn='+reqTypeName,"_blank");
			}else if(type == 'R08'){
				var reqType = this.getCmbRequestType().getValue();
				var reqTypeName = this.getCmbRequestType().getRawValue();
				var sort = this.getCmbSort().getValue();
				var sortName = this.getCmbSort().getRawValue();
				if(sort == null){
					sort = '';
					sortName = '';
				}
				
				window.open(ALF_CONTEXT+'/memo/listChange?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName+'&sort='+sort+'&sortName='+sortName+'&rt='+reqType+'&rtn='+reqTypeName,"_blank");
			}else if(type == 'R09'){
				var yearBudget = this.getYearBudget().getValue();
		    	var inout = this.getInout();
		    	var nameTrain = this.getNameTrain().getValue();

		    	var i = inout.getValue() ? inout.getValue() : "";

				window.open(ALF_CONTEXT+'/memo/listTrain?t='+to+'&f='+from+'&mimeType='+btn.text+'&tn='+typeName+'&y='+yearBudget+'&i='+i+'&n='+nameTrain+'&country='+inout.getRawValue(),"_new");
			}
		    

		}

	},
	checkData : function(type){
		
		var from = this.getFromDate().getRawValue();
		var to = this.getToDate().getRawValue();
		var uri = null;
		var params = {
		              
		};
		
			if(type == 'R01'){
				params = this.prepareParams(from, to, type, null, null, null, null, null, params);
				uri = ALF_CONTEXT+'/memo/listFnType';
			}else if(type == 'R02'){
				params = this.prepareParams(from, to, type, null, null, null, null, null, params);
				uri = ALF_CONTEXT+'/memo/listFnTeam';
			}else if(type == 'R03'){
				params = this.prepareParams(from, to, type, null, null, null, null, null, params);
				uri = ALF_CONTEXT+'/memo/listFnLevel';
			}else if(type == 'R04'){
				var sort = this.getCmbSort().getValue();
				params = this.prepareParams(from, to, type, sort, null, null, null, null, params);
				uri = ALF_CONTEXT+'/memo/listFnHour';
			}else if(type == 'R05'){
				params = this.prepareParams(from, to, type, null, null, null, null, null, params);
				uri = ALF_CONTEXT+'/memo/listTypeChange';
			}else if(type == 'R06'){
				var reqType = this.getCmbRequestType().getValue();
				params = this.prepareParams(from, to, type, null, null, null, null, reqType, params);
				uri = ALF_CONTEXT+'/memo/listRequestDay';
			}else if(type == 'R07'){
				var reqType = this.getCmbRequestType().getValue();
				params = this.prepareParams(from, to, type, null, null, null, null, reqType, params);
				uri = ALF_CONTEXT+'/memo/listRequestEffect';
			}else if(type == 'R08'){
				var reqType = this.getCmbRequestType().getValue();
				var sort = this.getCmbSort().getValue();
				params = this.prepareParams(from, to, type, sort, null, null, null, reqType, params);
				uri = ALF_CONTEXT+'/memo/listChange';
			}else if(type == 'R09'){
				var yearBudget = this.getYearBudget().getValue();
		    	var inout = this.getInout();
		    	var nameTrain = this.getNameTrain().getValue();
		    	params = this.prepareParams(from, to, type, sort, yearBudget, inout, nameTrain, null, params);
				uri = ALF_CONTEXT+'/memo/listTrain';
			}
		//alert(params);
//		Ext.Ajax.request({
//		    url:uri,
//		    method: "GET",
//		    params: params,
//		    success: function(response){
//		  	  
//			  	var json = Ext.decode(response.responseText);
//				  
//			   	if (json.success) {
//			   		
//			   	} else {
//			   		
//			   	}
//			   	 
//		   	 	myMask.hide();
//		
//		    },
//		    failure: function(response, opts){
//		    	
//		    },
//		    headers: getAlfHeader()
//		});
	},
	prepareParams : function(from, to, type, sort, yearBudget, inout, nameTrain, reqType, params) {
		
		params.t = to;
		params.f = from;
		params.tn = null;
		if(sort!=null&&sort!=''){
			params.sort = sort;
		}
		if(yearBudget!=null&&yearBudget!=''){
			params.y = yearBudget;
		}
		if(inout!=null&&inout!=''){
			params.i = inout;
		}
		if(nameTrain!=null&&nameTrain!=''){
			params.n = nameTrain;
		}
		if(reqType!=null&&reqType!=''){
			params.rt = reqType;
		}
	    params.country = null;
	    params.mimeType = '';
		return params;
	},
	selectType : function(combo, records, eOpts){
		
		var type = combo.getValue();
		var C_R01 = [ { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E40\u0E2B\u0E15\u0E38\u0E01\u0E32\u0E23\u0E13\u0E4C',align:'center', dataIndex:'type_name', flex:1}, 
        { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'type_amount' ,align:'center', flex:1}  ];
		var C_R02 = [ { text: '\u0E17\u0E35\u0E21', dataIndex:'team_name',align:'center', flex:1}, { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'team_amount' ,align:'center', flex:1}  ];
		var C_R03 = [ { text: '\u0E23\u0E30\u0E14\u0E31\u0E1A\u0E1C\u0E25\u0E01\u0E23\u0E30\u0E17\u0E1A', dataIndex:'level_name',align:'center', flex:1}, 
		        { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'level_amount',align:'center', flex:1}  ];
		var C_R04 = [ { text: 'No', dataIndex:'id',align:'center', flex:1}
		, { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E40\u0E2B\u0E15\u0E38\u0E01\u0E32\u0E23\u0E13\u0E4C', dataIndex:'type_name',align:'center', flex:1}  
		, { text: '\u0E23\u0E30\u0E14\u0E31\u0E1A\u0E1C\u0E25\u0E01\u0E23\u0E30\u0E17\u0E1A', dataIndex:'level',align:'center', flex:1}
		, { text: '\u0E0A\u0E37\u0E48\u0E2D\u0E1C\u0E39\u0E49\u0E41\u0E08\u0E49\u0E07\u0E40\u0E2B\u0E15\u0E38',align:'center', dataIndex:'informer', flex:1}
		, { text: '\u0E0A\u0E37\u0E48\u0E2D\u0E1C\u0E39\u0E49\u0E23\u0E31\u0E1A\u0E41\u0E08\u0E49\u0E07\u0E40\u0E2B\u0E15\u0E38',align:'center', dataIndex:'receiver', flex:1}
		, { text: '\u0E0A\u0E37\u0E48\u0E2D\u0E1C\u0E39\u0E49\u0E23\u0E31\u0E1A\u0E1C\u0E34\u0E14\u0E0A\u0E2D\u0E1A',align:'center', dataIndex:'responsible', flex:1}
		, { text: '\u0E01\u0E33\u0E2B\u0E19\u0E14\u0E40\u0E2A\u0E23\u0E47\u0E08', dataIndex:'estimate_date',align:'center', flex:1}
		, { text: '\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E2D\u0E2D\u0E01 Incident', dataIndex:'issue_date',align:'center', flex:1}
		, { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E16\u0E32\u0E27\u0E23', dataIndex:'rc_date',align:'center', flex:1}
		, { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E16\u0E32\u0E27\u0E23(\u0E0A\u0E21.)',align:'center', dataIndex:'rc_hour', flex:1}
		, { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E0A\u0E31\u0E48\u0E27\u0E04\u0E23\u0E32\u0E27',align:'center', dataIndex:'wa_date', flex:1}
		//, { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E40\u0E2A\u0E23\u0E47\u0E08\u0E0A\u0E31\u0E48\u0E27\u0E04\u0E23\u0E32\u0E27', dataIndex:'wa_hour', flex:1}
		, { text: '\u0E41\u0E01\u0E49\u0E44\u0E02\u0E0A\u0E31\u0E48\u0E27\u0E04\u0E23\u0E32\u0E27(\u0E0A\u0E21.)',align:'center', dataIndex:'wa_hour', flex:1}
		];	
		var C_R05 = [ { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E40\u0E2B\u0E15\u0E38\u0E01\u0E32\u0E23\u0E13\u0E4C',align:'center', dataIndex:'field_name', flex:1}
		, { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'count',align:'center', flex:1}  ];
		var C_R06 = [ { text: '\u0E17\u0E35\u0E21', dataIndex:'field_name',align:'center', flex:1}, 
		        { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'team_amount',align:'center', flex:1}  ];
		var C_R07 = [ { text: '\u0E23\u0E30\u0E14\u0E31\u0E1A\u0E1C\u0E25\u0E01\u0E23\u0E30\u0E17\u0E1A',align:'center', dataIndex:'field_name', flex:1}, 
		        { text: '\u0E08\u0E33\u0E19\u0E27\u0E19', dataIndex:'level_amount',align:'center', flex:1}  ];
	
		var C_R08 = [ { text: 'Change Request Id', dataIndex:'id',align:'center', flex:1}, 
		        { text: 'Change Request Type', dataIndex:'req_type',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48', dataIndex:'created_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E1C\u0E39\u0E49\u0E23\u0E49\u0E2D\u0E07\u0E02\u0E2D', dataIndex:'requester',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E40\u0E23\u0E37\u0E48\u0E2D\u0E07', dataIndex:'title',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E23\u0E30\u0E14\u0E31\u0E1A\u0E1C\u0E25\u0E01\u0E23\u0E30\u0E17\u0E1A', dataIndex:'level',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E40\u0E08\u0E49\u0E32\u0E2B\u0E19\u0E49\u0E32\u0E17\u0E35\u0E48\u0E44\u0E14\u0E49\u0E23\u0E31\u0E1A',align:'center', dataIndex:'rp_by', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E08\u0E32\u0E01\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E40\u0E1B\u0E25\u0E35\u0E48\u0E22\u0E19\u0E41\u0E1B\u0E25\u0E07', dataIndex:'from_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E16\u0E36\u0E07\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E40\u0E1B\u0E25\u0E35\u0E48\u0E22\u0E19\u0E41\u0E1B\u0E25\u0E07', dataIndex:'to_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: '\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E2D\u0E19\u0E38\u0E21\u0E31\u0E15\u0E34', dataIndex:'approve_time',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: '\u0E1C\u0E39\u0E49\u0E2D\u0E19\u0E38\u0E21\u0E31\u0E15\u0E34', dataIndex:'approve_by',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}, 
		        { text: 'Change Request Report ID', dataIndex:'crpt_id',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}},
		        { text: 'วันที่ดำเนินการเปลี่ยนแปลง', dataIndex:'rp_date',align:'center', flex:1,renderer: function (v, m, r) {return '<div align="left">'+v+'</div>';}}];
		
		var C_R09 = [ { xtype: 'rownumberer', width: 40, text: '\u0E25\u0E33\u0E14\u0E31\u0E1A\u0E17\u0E35\u0E48',align:'center'},
		        { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E01\u0E32\u0E23\u0E2D\u0E1A\u0E23\u0E21/\u0E2A\u0E31\u0E21\u0E19\u0E32/\u0E14\u0E39\u0E07\u0E32\u0E19',align:'center', dataIndex:'training_type', flex:1},
		        { text: '\u0E0A\u0E37\u0E48\u0E2D\u0E2B\u0E25\u0E31\u0E01\u0E2A\u0E39\u0E15\u0E23/\u0E42\u0E04\u0E23\u0E07\u0E01\u0E32\u0E23', dataIndex:'course_name',align:'center', flex:1}, 
		        { text: '\u0E1B\u0E23\u0E30\u0E40\u0E20\u0E17\u0E2B\u0E25\u0E31\u0E01\u0E2A\u0E39\u0E15\u0E23', dataIndex:'course_type',align:'center', flex:1}, 
		        { text: '\u0E1B\u0E23\u0E30\u0E40\u0E17\u0E28', dataIndex:'country',align:'center', flex:1}, 
		        { text: '\u0E1B\u0E35\u0E07\u0E1A\u0E1B\u0E23\u0E30\u0E21\u0E32\u0E13', dataIndex:'budget_year',align:'center', flex:1}, 
		        { text: '\u0E08\u0E32\u0E01 \u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E2D\u0E1A\u0E23\u0E21',align:'center', dataIndex:'from_date', flex:1},  
		        { text: '\u0E16\u0E36\u0E07 \u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E2D\u0E1A\u0E23\u0E21',align:'center', dataIndex:'to_date', flex:1},   
		
		        { text: '\u0E07\u0E1A\u0E1B\u0E23\u0E30\u0E21\u0E32\u0E13(\u0E1A\u0E32\u0E17)', dataIndex:'budget',align:'center', flex:1},   
		        { text: '\u0E23\u0E32\u0E22\u0E0A\u0E37\u0E48\u0E2D\u0E1C\u0E39\u0E49\u0E40\u0E02\u0E49\u0E32\u0E2D\u0E1A\u0E23\u0E21',align:'center', dataIndex:'trainee', flex:1,
		        renderer: function (v, m, r) {
							var trainee = r.get("trainee");
						    return trainee.replace(/(?:\r\n|\r|\n)/g, '<br />');;
						}}  ];   

		var store = Ext.create('PBPcm.store.ReportStore',{autoLoad:false});
		var grid = this.getReportGrid();
		
		if(type == 'R01'){
			grid.reconfigure(store,C_R01);
			store.removeAll();
		}else if(type == 'R02'){
			grid.reconfigure(store,C_R02);
			store.removeAll();
		}else if(type == 'R03'){
			grid.reconfigure(store,C_R03);
			store.removeAll();
		}else if(type == 'R04'){
			grid.reconfigure(store,C_R04);
			store.removeAll();
		}else if(type == 'R05'){
			grid.reconfigure(store,C_R05);
			store.removeAll();
		}else if(type == 'R06'){
			grid.reconfigure(store,C_R06);
			store.removeAll();
		}else if(type == 'R07'){
			grid.reconfigure(store,C_R07);
			store.removeAll();
		}else if(type == 'R08'){
			grid.reconfigure(store,C_R08);
			store.removeAll();
		}else if(type == 'R09'){
			grid.reconfigure(store,C_R09);
			store.removeAll();
		}

		var northPanel = this.getNorthPanel();
		var pr08 = this.getPr08();
		var pr09 = this.getPr09();
		var type = combo.getValue();
		var sort = this.getCmbSort();
		var reqType = this.getRequestType();
		var pmDate = this.getPermanentDate();
		if(type=='R08' || type=='R04'){
			
			var sortModel = Ext.create('PB.model.common.ComboBoxModel',
			{id: null, code:null ,name: '( ไม่ระบุ )',select:null,flag2:null});

			var sortStore=sort.getStore();
			sortStore.getProxy().api.read = ALF_CONTEXT+'/admin/main/master/listMasterColumn';
			sortStore.getProxy().extraParams = {
				t : 'RO',
				f1: type
			};
			sortStore.load({callback:function(){
				sortStore.insert(0,sortModel);
				sort.setValue(null);
			}});

			pr08.setVisible(true);
			pr09.setVisible(false);
			northPanel.setHeight(pr08.getHeight()+115);
		}else if(type=='R09'){
			pr08.setVisible(false);
			pr09.setVisible(true);
			northPanel.setHeight(pr09.getHeight()+115);
		}else{
			pr08.setVisible(false);
			pr09.setVisible(false);
			northPanel.setHeight(115);
		}
		
		
		if(type=='R07' || type=='R06' || type=='R08'){
			reqType.setVisible(true);
		}else{
			reqType.setVisible(false);
		}
		

				
		if(type=='R04'){
			pmDate.setVisible(true);
		}else{
			pmDate.setVisible(false);
		}
		
	}

});
