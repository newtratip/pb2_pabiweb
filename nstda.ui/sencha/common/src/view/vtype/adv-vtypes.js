Ext.define("PB.vtype.Validation", {
	
	singleton : true,

    // Add the additional 'advanced' VTypes
    daterangeValidation:function() {
    	Ext.form.field.VTypes.daterange = function(val, field) {
    		
    		var pos = val.indexOf(" ");
    		if (pos >= 0) {
    			var suffix = val.substring(pos+1);
    			suffix = suffix.replace(/1|2|3|4|5|6|7|8|9/g, '0');
    			val = val.substring(0, pos)+" "+suffix;
    		}
    		
            var date = field.parseDate(val);
            if (!date) {
                return;
            }
            
            if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
                this.dateRangeMax = date;
                var start = field.up('form').down('#' + field.startDateField);
                start.setMaxValue(date);
                start.validate();
            }
            else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
                this.dateRangeMin = date;
                var end = field.up('form').down('#' + field.endDateField);
                end.setMinValue(date);
                end.validate();
            }
            /*
             * Always return true since we're only using this vtype to set the
             * min/max allowed values (these are tested for after the vtype test)
             */
            return true;
        }

        Ext.form.field.VTypes.daterangeText = 'Start date must be less than end date';

    }
    
    },
    
	function() {
		console.log("registered VTypes");
		this.daterangeValidation();
	}

);
    