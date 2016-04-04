package pb.common.util;

public class StringUtil {
	public static String convertEnterToBrTag(String s) {
		return s.replace("\n", "<br/>");
	}
}
