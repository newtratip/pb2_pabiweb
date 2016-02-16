package nstda.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DocUtil {
	
	private static Logger log = Logger.getLogger(DocUtil.class);
	
	public void convertHtmlToPdf(Map<String, Object> params) throws Exception {
		
//		Map<String, String> env = System.getenv();
//		for (String envName : env.keySet()) {
//		     System.out.format("%s=%s%n", envName, env.get(envName));
//		}
		
		String fullName = (String)params.get("fullName");
		String top = StringUtils.defaultIfBlank((String)params.get("top"), "50mm");
		String bottom = StringUtils.defaultIfBlank((String)params.get("bottom"), "50mm");
		String left = StringUtils.defaultIfBlank((String)params.get("left"), "50mm");
		String right = StringUtils.defaultIfBlank((String)params.get("right"), "50mm");
		
		String hcenter = StringUtils.defaultIfBlank((String)params.get("h_center"), null);
		hcenter = hcenter!=null ? " --header-center "+hcenter.replace(' ', '\u00a0') : "";
		String hleft = StringUtils.defaultIfBlank((String)params.get("h_left"), null);
		hleft = hleft!=null ? " --header-left "+hleft.replace(' ', '\u00a0') : "";
		String hright = StringUtils.defaultIfBlank((String)params.get("h_right"), null);
		hright = hright!=null ? " --header-right "+hright.replace(' ', '\u00a0') : "";
		String hspacing = StringUtils.defaultIfBlank((String)params.get("h_spacing"), null);
		hspacing = hspacing!=null ? " --header-spacing "+hspacing : "";
		String hfontname = StringUtils.defaultIfBlank((String)params.get("h_font_name"), null);
		hfontname = hfontname!=null ? " --header-font-name "+hfontname : "";
		String hfontsize = StringUtils.defaultIfBlank((String)params.get("h_font_size"), null);
		hfontsize = hfontsize!=null ? " --header-font-size "+hfontsize : "";

		
		String fcenter = StringUtils.defaultIfBlank((String)params.get("f_center"), null);
		fcenter = fcenter!=null ? " --footer-center "+fcenter.replace(' ', '\u00a0') : "";
		String fleft = StringUtils.defaultIfBlank((String)params.get("f_left"), null);
		fleft = fleft!=null ? " --footer-left "+fleft.replace(' ', '\u00a0') : "";
		String fright = StringUtils.defaultIfBlank((String)params.get("f_right"), null);
		fright = fright!=null ? " --footer-right "+fright.replace(' ', '\u00a0') : "";
		String fspacing = StringUtils.defaultIfBlank((String)params.get("f_spacing"), null);
		fspacing = fspacing!=null ? " --footer-spacing "+fspacing : "";
		String ffontname = StringUtils.defaultIfBlank((String)params.get("f_font_name"), null);
		ffontname = ffontname!=null ? " --footer-font-name "+ffontname : "";
		String ffontsize = StringUtils.defaultIfBlank((String)params.get("f_font_size"), null);
		ffontsize = ffontsize!=null ? " --footer-font-size "+ffontsize : "";
		
		String cmd = "wkhtmltopdf"
			 		+" -L "+left
			 		+" -R "+right 
				 	+" -T "+top
				 	+" -B "+bottom
				 	+hleft
				 	+hright
				 	+hcenter
				 	+hspacing
				 	+hfontname
				 	+hfontsize
				 	+fleft
				 	+fright
				 	+fcenter
				 	+fspacing
				 	+ffontname
				 	+ffontsize
					+" "+fullName+".html"
					+" "+fullName+".pdf"
					;
		
		log.info("convert cmd : "+cmd);
		
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(cmd);
		p.waitFor();
		BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String line = "";

		while ((line = b.readLine()) != null) {
		  log.info(line);
		}

		b.close();
	}
	
	public String genUniqueFileName() {
		UUID fileNameUUID = UUID.randomUUID();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = df.format(new Date()) + "_" + fileNameUUID.toString();

		return fileName;
	}
	
}
