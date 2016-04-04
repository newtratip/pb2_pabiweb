Ext.define('PB.Util', {
	statics: {
	    getSrcUrl:function(srcUrl) {
			if (srcUrl == "mainMaster") {
				return "memo/master";
			}
			else
			if (srcUrl == "mainMasterField") {
				return "memo/masterField";
			}
			else
			if (srcUrl == "format") {
				return "memo/format";
			}
			else
			if (srcUrl == "approvalMatrix") {
				return "main/approvalMatrix";
			}
			else
			if (srcUrl == "userProfile") {
				return "main/userProfile";
			}
			
			return srcUrl;
	    }
	}
});
