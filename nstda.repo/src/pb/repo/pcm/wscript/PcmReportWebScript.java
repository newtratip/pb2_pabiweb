package pb.repo.pcm.wscript;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.apache.chemistry.opencmis.commons.impl.MimeTypes;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.pcm.service.PcmFunctionService;
import pb.repo.pcm.util.PcmReqUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class PcmReportWebScript {

	private static Logger log = Logger.getLogger(PcmReportWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/pcm/rpt";
	
	@Autowired
	PcmFunctionService memoFunctionService;
	
	@Uri(URI_PREFIX+"/listFnType")
	  public void handleFileList(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
			
		String json = null;
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String tn = request.getParameter("tn");
		String criteria = null;
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			criteria = params.get("criteria").toString();
			List<Map<String,Object>> list = memoFunctionService.listType(params);
			
			String fileName = "IC-R01";
			byte[] file = null;
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoType", f, t, "R01", tn, criteria, "IC-R01");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
			
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}		
			
		}
		
	  }
	
	@Uri(URI_PREFIX+"/listFnLevel")
	  public void handleFileLevel(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
		
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String rt = request.getParameter("rt");
		String tn = request.getParameter("tn");
		String json = null;
		String criteria = null;
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			criteria = params.get("criteria").toString();
			List<Map<String,Object>> list = memoFunctionService.listLevel(params);
			
			
			String fileName = "IC-R02";
			byte[] file = null;
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoType", f, t, "R02", tn, criteria, "IC-R02");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}	
		}
		
	  }
	
	
	
	@Uri(URI_PREFIX+"/listFnTeam")
	  public void handleFileTeam(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
			
		String json = null;
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String tn = request.getParameter("tn");
		//String rt = request.getParameter("rt");
		
		String criteria = null;
		try {
	
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			//params.put("rt", rt);
			criteria = params.get("criteria").toString();
			List<Map<String,Object>> list = memoFunctionService.listTeam(params);
			
			
			String fileName = "IC-R03";
			byte[] file = null;
			
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoType", f, t, "R03", tn, criteria, "IC-R03");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}	
		}
		
	  }
	
	@Uri(URI_PREFIX+"/listFnHour")
	  public void handleFileHour(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
			
		String json = null;
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String tn = request.getParameter("tn");
		String pd = request.getParameter("pd");
		String pdn = request.getParameter("pdn");
		String sort = request.getParameter("sort");
		String sortName = request.getParameter("sortName");
		String criteria = null;
		try {
	
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			
			criteria = params.get("criteria").toString();
			if(pd!=null && !pd.equalsIgnoreCase("")) {
				params.put(pd, pd);
				criteria+="		";
				criteria += "วันที่แก้ไขถาวร : "+pdn;
			}
			if(sort!=null && !sort.equalsIgnoreCase("")) {
				log.info("sort :: " + sort);
				params.put("sort", sort);
				criteria+="		";
				criteria += "เรียงลำดับ : "+sortName;
			}
			List<Map<String,Object>> list = memoFunctionService.listHour(params);
			
			
			String fileName = "IC-R04";
			byte[] file = null;
			
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoDuration", f, t, "R04", tn, criteria, "IC-R04");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}
		}
		
	  }
	
	@Uri(URI_PREFIX+"/listTypeChange")
	  public void handleTypeChange(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
			
		String json = null;
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String tn = request.getParameter("tn");
		String criteria = null;
		try {
	
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			criteria = params.get("criteria").toString();
			List<Map<String,Object>> list = memoFunctionService.listTypeChange(params);
			
			
			
			String fileName = "CRF-R05";
			byte[] file = null;
			
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoType", f, t, "R05", tn, criteria, "CRF-R05");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}
		}
		
	  }
	
	@Uri(URI_PREFIX+"/listRequestDay")
	  public void handleReq(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
			
		String json = null;
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String rt = request.getParameter("rt");
		String rtn = request.getParameter("rtn");
		String tn = request.getParameter("tn");
		String criteria = null;
		try {
	
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			if(rt==null || rt.equalsIgnoreCase("")) {
				rt= "%";
			}
			params.put("rt", rt);
			criteria = params.get("criteria").toString();
			
			if(rt!=null && !rt.equalsIgnoreCase("%")) {
				criteria+="		";
				criteria += "ประเภทการร้องขอ : "+rtn;
			}
			
			List<Map<String,Object>> list = memoFunctionService.listRequestDay(params);
			
			
			
			String fileName = "CRF-R06";
			byte[] file = null;
			
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoType", f, t, "R06", tn, criteria, "CRF-R06");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}
		}
		
	  }
	
	@Uri(URI_PREFIX+"/listRequestEffect")
	  public void handleReqEff(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
			
		String json = null;
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String rt = request.getParameter("rt");
		String rtn = request.getParameter("rtn");
		String tn = request.getParameter("tn");
		String criteria = null;
		
		try {
	
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			if(rt==null || rt.equalsIgnoreCase("")) {
				rt= "%";
			}
			params.put("rt", rt);
			criteria = params.get("criteria").toString();
			
			if(rt!=null && !rt.equalsIgnoreCase("%")) {
				criteria+="		";
				criteria += "ประเภทการร้องขอ : "+rtn;
			}
			
			List<Map<String,Object>> list = memoFunctionService.listRequestEffect(params);
			
			
			
			String fileName = "CRF-R07";
			byte[] file = null;
			
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoType", f, t, "R07", tn, criteria, "CRF-R07");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}
		}
		
	  }
	
	@Uri(URI_PREFIX+"/listChange")
	  public void handleChange(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
			
		String json = null;
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String rt = request.getParameter("rt");
		String rtn = request.getParameter("rtn");
		String tn = request.getParameter("tn");
		String sort = request.getParameter("sort");
		String sortName = request.getParameter("sortName");
		String criteria = null;
		try {
	
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			if(rt==null || rt.equalsIgnoreCase("")) {
				rt= "%";
			}
			params.put("rt", rt);
			criteria = params.get("criteria").toString();
			
			if(rt!=null && !rt.equalsIgnoreCase("%")) {
				criteria+="		";
				criteria += "ประเภทการร้องขอ : "+rtn;
			}
			if(sort!=null && !sort.equalsIgnoreCase("")) {
				params.put("sort", sort);
				criteria +="		";
				criteria += "เรียงลำดับ : "+sortName;
			}
		
			List<Map<String,Object>> list = memoFunctionService.listChange(params);
			
			
			
			String fileName = "CRF-R08";
			byte[] file = null;
			
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoDuration", f, t, "R08", tn, criteria, "CRF-R08");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}
		}
		
	  }
	
	@Uri(URI_PREFIX+"/listTrain")
	  public void handleTrain(final WebScriptResponse response, final WebScriptRequest request)
	      throws Exception {
			
		String json = null;
		String mt = request.getParameter("mimeType");
		String f = request.getParameter("f");
		String t = request.getParameter("t");
		String tn = request.getParameter("tn");
		String y = request.getParameter("y");
		String i = request.getParameter("i");
		String country = request.getParameter("country");
		String n = request.getParameter("n");
		StringBuffer sb = new StringBuffer();
		try {
	
			Map<String, Object> params = new HashMap<String, Object>();
			params = PcmReqUtil.dateParameter(f, t, params);
			//params.put("s", f);
			//params.put("f", t);
			sb.append(params.get("criteria").toString());
			if(y!=null && !y.equalsIgnoreCase("")) {
				params.put("y", y);
				PcmReqUtil.appenCriteria(sb, y, "ปีงบประมาณ : ");
			}
			if(i!=null && !i.equalsIgnoreCase("")) {
				params.put("i", i);
				PcmReqUtil.appenCriteria(sb, country, "ใน/นอกประเทศ : ");
				
			}
			if(n!=null && !n.equalsIgnoreCase("")) {
				params.put("n", "%"+n+"%");
				PcmReqUtil.appenCriteria(sb, n, "ชื่อผู้เข้าอบรม : ");
			
			}
			
			List<Map<String,Object>> list = memoFunctionService.listTrain(params);
			
			
			String fileName = "CRF-R09";
			byte[] file = null;
			
			if(mt.equalsIgnoreCase("PDF") || mt.equalsIgnoreCase("EXCEL")) {
				JasperPrint jasperPrint = memoFunctionService.genReport(list, "memoDuration", f, t, "R09", tn, sb.toString(), "CRF-R09");
				this.genRpt(mt, file, fileName, response, jasperPrint);
		        
			}else {
				json = PcmReqUtil.jsonReportSuccess(list);
			}
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			if(mt.equalsIgnoreCase("")) {
				CommonUtil.responseWrite(response, json);
			}
		}
		
	  }
	
	public void genRpt(String mt, byte[] file, String fileName, WebScriptResponse response, JasperPrint jasperPrint) throws IOException, JRException {
		
		if(mt.equalsIgnoreCase("PDF")) {
			file = JasperExportManager.exportReportToPdf(jasperPrint);
				
			InputStream is = new ByteArrayInputStream(file);
			//String mimeType = MimeTypes.getMIMEType(FilenameUtils.getExtension(fileName+".pdf"));
			response.setContentType("application/pdf");
			//response.setHeader("Content-disposition","attachment; filename=\""+fileName+"\".pdf");
			
			OutputStream out = response.getOutputStream();
	        byte[] buffer = new byte[4096];
	        int length;
	        while ((length = is.read(buffer)) > 0){
	            out.write(buffer, 0, length);
	        }
	      
	        is.close();
	        out.flush();	
	        out.close();
	        
		}else if(mt.equalsIgnoreCase("EXCEL")){
			
			 JRXlsxExporter exporter = new JRXlsxExporter();
			 
			 
			 response.setContentType("application/vnd.ms-excel.12");
			 response.setHeader("content-disposition","attachment; filename="+fileName+".xlsx");
			 
			 OutputStream out = response.getOutputStream();
			
		     exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		     exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
		     exporter.exportReport();
		     
		     out.flush();
		     out.close();

		}
	}
}
