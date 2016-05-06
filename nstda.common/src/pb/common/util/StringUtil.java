package pb.common.util;

import java.text.DecimalFormat;

public class StringUtil {
	public static String convertEnterToBrTag(String s) {
		return s.replace("\n", "<br/>");
	}
	
	public static String forShow(Double d, String format) {
		DecimalFormat df = new DecimalFormat(format);
		
		return df.format(d); 
	}
}
