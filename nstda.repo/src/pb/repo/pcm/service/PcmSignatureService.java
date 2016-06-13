package pb.repo.pcm.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.activiti.engine.delegate.DelegateTask;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.version.VersionModel;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNode;
import org.alfresco.service.cmr.coci.CheckOutCheckInService;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.version.VersionType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.util.CommonDateTimeUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.pcm.model.PcmReqModel;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Service
public class PcmSignatureService {
	
	private static Logger log = Logger.getLogger(PcmSignatureService.class);

	@Autowired
	FileFolderService fileFolderService;
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	CheckOutCheckInService checkOutCheckInService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	PcmReqService memoService;
	
	@Autowired
	AdminMasterService adminMasterService;
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	SearchService searchService;
	
	public NodeRef addSignature(final DelegateTask task, final HashMap<String, Object> signConfMap ,final String prefix, final Integer level) {

		
		return AuthenticationUtil.runAs(new RunAsWork<NodeRef>()
		{
			public NodeRef doWork() throws Exception
			{
				String refId = task.getVariable(prefix+"memoId").toString();
				
				PcmReqModel memoModel = memoService.get(refId);
							
				String docRef = memoModel.getDocRef();
//				String folderRef = memoModel.getFolderRef();
				NodeRef pdfNodeRef = new NodeRef(docRef);
//				NodeRef parentNodeRef = new NodeRef(folderRef);
				log.info("pdfNodeRef :: " + pdfNodeRef);
				
				//List<MainMasterModel> listMaster = new ArrayList<MainMasterModel>();
				
				//listMaster = adminMasterService.listByType(MainMasterConstant.CONFIG_SIGNTURE_TYPE, null, null, null, null);
				
				//Map<String, String> sigCondMap = null;
			    //sigCondMap = new HashMap<String, String>();
			    //for(MainMasterModel m : listMaster) {
			    //	sigCondMap.put(m.getCode(), m.getFlag1());
			    //}
				
			  //  sigCondMap.put(MainMasterConstant.SIGNTURE_PIXEL_CODE, "40,40");
				
				CommonDateTimeUtil util = new CommonDateTimeUtil();
				
				ContentReader contentReader = contentService.getReader(pdfNodeRef, ContentModel.PROP_CONTENT);
			
			    InputStream is = contentReader.getContentInputStream();

//	        	MainApprovalMatrixModel approvalMatrixModel = adminApprovalMatrixService.get(memoModel.getApprovalMatrixId());
			    
			    try {
		        	Object reviewer = task.getVariable(prefix+"reviewer"+level+"Assignee");
		        	if(reviewer!=null&&!reviewer.toString().equalsIgnoreCase("")) {
//				        Object person = nodeService.getProperty(personService.getPerson(userName), ContentModel.PROP_JOBTITLE);
//				        String jobTitle = "";
//				        if(person!=null){
//				        	jobTitle = person.toString();
//				        }
		        		 
				        String date = util.getDisplayDate((Date)task.getVariable(prefix+"reviewer"+level+"SignDate"));
				        
						
						String fileName = reviewer.toString().toLowerCase()+".png";
						log.info("Signed user="+fileName);
						
						NodeRef nodeRef = new NodeRef( signConfMap.get(MainMasterConstant.SCC_MAIN_SIGNATURE_PATH).toString());
						log.info("Signature Folder="+nodeRef);
						
						nodeRef = fileFolderService.searchSimple(nodeRef, fileName);
						log.info("Signature Image="+nodeRef);
			    	
			    	
				    	List<List<String>> signList = new ArrayList<List<String>>();
//				    	String signature = approvalMatrixModel.getSignature();
				    	String signature = "";
				    	log.info("signature position : "+signature);
				    	List<String> s = null;
						if(signature!=null && !signature.trim().equals("")){
							signature =  signature.replaceAll("[\\[\\]]", "");
							log.info("signature[] : "+signature);
							List<String> position = Arrays.asList(signature.split(","));
							
							if (position.size() % 2 == 0) {
								int i;
								for(i=0;i<position.size(); i+=2){
									
									s = new ArrayList<String>();
									for(int l=0; l<2; l++){
										s.add(position.get(i+l));
									}
									
									signList.add(s);
									
								}
							}
							
						}
						
						if (!signList.isEmpty()) {
				    	
				        	List<String> position = signList.get(level-1);
				        	
				        	if (Integer.valueOf(position.get(0)) != 0 || Integer.valueOf(position.get(1)) != 0) {
				        	
						    	PdfReader pdfToWatermark = new PdfReader(is);
								int numberOfPages = pdfToWatermark.getNumberOfPages();
						        ByteArrayOutputStream out = new ByteArrayOutputStream();
						        PdfStamper stamp = new PdfStamper(pdfToWatermark, out);
						        
						        for(int i=1 ; i<= numberOfPages; i++){
						        	 PdfContentByte pageContent = stamp.getOverContent(i);
						        	 
								     addOneSignature(pageContent, position, nodeRef, date, signConfMap, 0);
						        }
						        
						        stamp.close();
						        is.close();
						        out.flush();
						        out.close();
						        
						        if (contentReader.isClosed()) {
						        	try {
							   			  Thread.sleep(2000);
							   		} catch (InterruptedException ie) {
							   			  //Handle exception
							   		}
						        	
			//			            FileInfo fileInfo = fileFolderService.getFileInfo(pdfNodeRef);
			//				        
			//				        log.info("File Name="+fileInfo.getName());
			//				        log.info("parentNodeRef :: " + parentNodeRef);
							        
			//						QName nodeType = ContentModel.PROP_CONTENT;
			//						
			//						NodeRef tempNodeRef = new NodeRef(sigCondMap.get(MainMasterConstant.SCC_MAIN_TEMP_PATH).toString());
			//						
			//				        //NodeRef signNodeRef = fileFolderService.create(parentNodeRef, "SIGN-"+fileInfo.getName(), nodeType).getNodeRef();
			//						NodeRef signNodeRef = fileFolderService.create(tempNodeRef, "SIGN-"+fileInfo.getName(), nodeType).getNodeRef();
			//						
			//			            ContentWriter contentWriter = contentService.getWriter(signNodeRef, ContentModel.PROP_CONTENT, true);
			//				        contentWriter.setMimetype("application/pdf");
			//				        FileChannel fileChannel = contentWriter.getFileChannel(true);
			//				        ByteBuffer bf = ByteBuffer.wrap(out.toByteArray());
			//
			//				        fileChannel.position(contentWriter.getSize());
			//			            fileChannel.write(bf);
			//			            fileChannel.force(false);
			//			            fileChannel.close();
						            
						        	alfrescoService.cancelCheckout(pdfNodeRef);
						            NodeRef workingCopy = checkOutCheckInService.checkout(pdfNodeRef);
									ContentWriter contentWriter = contentService.getWriter(workingCopy, ContentModel.PROP_CONTENT, true);
								    contentWriter.setMimetype("application/pdf");
								    
			//					    contentReader = contentService.getReader(signNodeRef, ContentModel.PROP_CONTENT);
								    
			//					    contentWriter.putContent(contentReader);
								    contentWriter.putContent(new ByteArrayInputStream(out.toByteArray()));
								    nodeService.setProperty(workingCopy, ContentModel.PROP_CONTENT, contentWriter.getContentData());
								    
								    Map<String, Serializable> versionProperties = new HashMap<String, Serializable>(1);
								    versionProperties.put(VersionModel.PROP_VERSION_TYPE, VersionType.MINOR);
								   
								    log.info("  check in ...");
								    checkOutCheckInService.checkin(workingCopy, versionProperties);
								    log.info("  finish");
			//					    fileFolderService.delete(signNodeRef);
						        }
					        
				        	} // position
				        
						}
		        	}
			    	
			    }  catch (Exception ex) {
					log.error("", ex);
				}
			    
				return pdfNodeRef;
			}
		}, AuthenticationUtil.getAdminUserName());
						
	}
	
	private void addOneSignature(PdfContentByte pageContent, List<String> signPos, NodeRef signatureNodeRef, String date, Map<String,Object> signConfig
			,Integer increasePosition) throws Exception {
		
		log.info("Begin Sign");

		String pos0 = String.valueOf(Integer.valueOf(signPos.get(0))+increasePosition);
		String pos1 = String.valueOf(Integer.valueOf(signPos.get(1))+0);
		
//		String fontSize = signConfig.get(MainMasterConstant.SCC_MEMO_SIGNATURE_FONT_SIZE).toString();
//		String[] datePosition = signConfig.get(MainMasterConstant.SCC_MEMO_SIGNATURE_DATE_POSITION).toString().split(",");
//		String[] pixel = signConfig.get(MainMasterConstant.SCC_MEMO_SIGNATURE_PIXEL).toString().split(",");
//
//		String basePath = System.getProperty("catalina.base");
//		String fontPath = basePath+"/webapps/alfresco/WEB-INF/classes";
//	
//		BaseFont baseFont = BaseFont.createFont(fontPath+"/angsa.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//		
//		if(Float.parseFloat(pos0) != 0 && Float.parseFloat(pos1) != 0){
//			
//	        ContentReader contentReader = alfrescoService.getContentByNodeRef(signatureNodeRef);
//	        Image signatureImage = Image.getInstance(ImageIO.read(contentReader.getContentInputStream()), null);
//	        
//	        log.info("x::"+Float.parseFloat(pos0));
//	        log.info("y::"+Float.parseFloat(pos1));
//	        
//	        signatureImage.scaleAbsolute(Float.parseFloat(pixel[0]), Float.parseFloat(pixel[1])); 
//	        signatureImage.setAbsolutePosition(Float.parseFloat(pos0), Float.parseFloat(pos1));
//			
//	     	pageContent.addImage(signatureImage);
//	     	
//	     	Integer datePaddingLeft = Integer.valueOf(pos0)+Integer.valueOf(datePosition[0]);
//	     	Integer dateNewLine = Integer.valueOf(pos1)-Integer.valueOf(datePosition[1]);
//	     	
//	     	pageContent.beginText();
//		    pageContent.setFontAndSize(baseFont, Float.parseFloat(fontSize));
//		    pageContent.setTextMatrix(Float.parseFloat(datePaddingLeft.toString()), Float.parseFloat(dateNewLine.toString()));
//		    pageContent.showText(date);
//		    pageContent.endText(); 
//		    
//
//		}
	
	}
	
	
	public List<NodeRef> addDocSignatureAllApprover(final DelegateTask task, final HashMap<String, Object> signConfMap ,final String prefix) {
		
		return AuthenticationUtil.runAs(new RunAsWork<List<NodeRef>>()
				{
					public List<NodeRef> doWork() throws Exception
					{
//						List<Map<String, Object>> docSignPatternList = adminMasterService.listByType(MainMasterConstant.TYPE_SYSTEM_CONFIG, MainMasterConstant.SCC_MEMO_DOC_SIGN_PATTERN+"_", null, null, null);
						List<Map<String, Object>> docSignPatternList = null;
						log.info("docSignPatternList :: " + docSignPatternList+ ", "+docSignPatternList.size());
						
						List<ActivitiScriptNode> attachDocList = (List<ActivitiScriptNode>)task.getVariable(prefix+"attachDocument");
						log.info("attachDocList :: " + attachDocList + ", " + attachDocList.size());
						
						List<NodeRef> attDocList = new ArrayList<NodeRef>();
						
						CommonDateTimeUtil util = new CommonDateTimeUtil();
						
					    try {
					    	List<NodeRef> signatureList = new ArrayList<NodeRef>();
					    	List<String> signDateList = new ArrayList<String>();
					    	
					    	int level = 1;
					    	
							NodeRef folderNodeRef = new NodeRef( signConfMap.get(MainMasterConstant.SCC_MAIN_SIGNATURE_PATH).toString());
							log.info("Signature Folder="+folderNodeRef);
							
				        	Object reviewer = task.getVariable(prefix+"reviewer"+level+"Assignee");
				        	while(reviewer!=null&&!reviewer.toString().equalsIgnoreCase("")) {
				        		 
								String reviewerImgName = reviewer.toString().toLowerCase()+".png";
								log.info("Reviewer="+reviewerImgName);
								
								NodeRef signatureNodeRef = fileFolderService.searchSimple(folderNodeRef, reviewerImgName);
								log.info("Signature Image="+signatureNodeRef);
								
								signatureList.add(signatureNodeRef);
								signDateList.add(util.getDisplayDate((Date)task.getVariable(prefix+"reviewer"+level+"SignDate")));
								
								level++;
								
								reviewer = task.getVariable(prefix+"reviewer"+level+"Assignee");
				        	}
						        
						        
							for (ActivitiScriptNode scriptNodeRef : attachDocList) {
								NodeRef attachNodeRef = scriptNodeRef.getNodeRef();
								log.info("attachNodeRef :: " + attachNodeRef);
								
								attDocList.add(attachNodeRef);
								
								FileInfo fileInfo = fileFolderService.getFileInfo(attachNodeRef);
								log.info("name:"+fileInfo.getName());
								
								for(Map<String, Object> docSignPatternModel : docSignPatternList) {
									log.info("  regex:"+docSignPatternModel.get(MainMasterConstant.TFN_FLAG1));
									
									if (fileInfo.getName().matches((String)docSignPatternModel.get(MainMasterConstant.TFN_FLAG1))) {
										log.info("   matched");
							
										ContentReader contentReader = contentService.getReader(attachNodeRef, ContentModel.PROP_CONTENT);
										
									    InputStream is = contentReader.getContentInputStream();
				
								    	List<List<String>> signPosList = new ArrayList<List<String>>();
								    	String signature = (String)docSignPatternModel.get(MainMasterConstant.TFN_FLAG2);
								    	log.info("signature position : "+signature);
								    	List<String> s = null;
										if(signature!=null){
											signature =  signature.replaceAll("[\\[\\]]", "");
											log.info("signature[] : "+signature);
											List<String> position = Arrays.asList(signature.split(","));
											
											int i;
											for(i=0;i<position.size(); i+=2){
												
												s = new ArrayList<String>();
												for(int l=0; l<2; l++){
													s.add(position.get(i+l));
												}
												
												signPosList.add(s);
												
											}
											
										}
								    	
								    	PdfReader pdfToWatermark = new PdfReader(is);
										int numberOfPages = pdfToWatermark.getNumberOfPages();
								        ByteArrayOutputStream out = new ByteArrayOutputStream();
								        PdfStamper stamp = new PdfStamper(pdfToWatermark, out);
								        
								        for(int i=1 ; i<= numberOfPages; i++){
								        	 PdfContentByte pageContent = stamp.getOverContent(i);
							        	 
								        	 int j = 0;
								        	 for(j=0; j<signatureList.size(); j++) {
								        		 if (Integer.valueOf(signPosList.get(j).get(0)) != 0 || Integer.valueOf(signPosList.get(j).get(1)) != 0 ) {
								        			 addOneSignature(pageContent, signPosList.get(j), signatureList.get(j), signDateList.get(j), signConfMap, 0);
								        		 }
								        	 }
							        	}
								        
								        stamp.close();
								        is.close();
								        out.flush();
								        out.close();
								        
								        if (contentReader.isClosed()) {
								        	try {
									   			  Thread.sleep(2000);
									   		} catch (InterruptedException ie) {
									   			  //Handle exception
									   		}
								            
								        	alfrescoService.cancelCheckout(attachNodeRef);
								            NodeRef workingCopy = checkOutCheckInService.checkout(attachNodeRef);
											ContentWriter contentWriter = contentService.getWriter(workingCopy, ContentModel.PROP_CONTENT, true);
										    contentWriter.setMimetype("application/pdf");
										    
										    contentWriter.putContent(new ByteArrayInputStream(out.toByteArray()));
										    nodeService.setProperty(workingCopy, ContentModel.PROP_CONTENT, contentWriter.getContentData());
										    
										    Map<String, Serializable> versionProperties = new HashMap<String, Serializable>(1);
										    versionProperties.put(VersionModel.PROP_VERSION_TYPE, VersionType.MINOR);
										   
										    log.info("  check in ...");
										    checkOutCheckInService.checkin(workingCopy, versionProperties);
										    log.info("  finish");
								        }
								        break;
							        
									} // if
								} // for doc sign pattern
							} // for attach doc
					    
					    }  catch (Exception ex) {
							log.error("", ex);
						}
						
						return attDocList;
					}
				}, AuthenticationUtil.getAdminUserName());	
	}
		
	public List<NodeRef> addDocSignature(final DelegateTask task, final HashMap<String, Object> signConfMap ,final String prefix, final Integer level) {
		
		return AuthenticationUtil.runAs(new RunAsWork<List<NodeRef>>()
				{
					public List<NodeRef> doWork() throws Exception
					{
//						List<Map<String, Object>> docSignPatternList = adminMasterService.listByType(MainMasterConstant.TYPE_SYSTEM_CONFIG, MainMasterConstant.SCC_MEMO_DOC_SIGN_PATTERN+"_", null, null, null);
						List<Map<String, Object>> docSignPatternList = null;
						log.info("docSignPatternList :: " + docSignPatternList+ ", "+docSignPatternList.size());
						
						List<ActivitiScriptNode> attachDocList = (List<ActivitiScriptNode>)task.getVariable(prefix+"attachDocument");
						log.info("attachDocList :: " + attachDocList + ", " + attachDocList.size());
						
						List<NodeRef> attDocList = new ArrayList<NodeRef>();
						
						CommonDateTimeUtil util = new CommonDateTimeUtil();
						
					    try {
					    	
					    	
				        	Object reviewer = task.getVariable(prefix+"reviewer"+level+"Assignee");
				        	if(reviewer!=null&&!reviewer.toString().equalsIgnoreCase("")) {
						        String date = util.getDisplayDate((Date)task.getVariable(prefix+"reviewer"+level+"SignDate"));
						        
								String fileName = reviewer.toString().toLowerCase()+".png";
								log.info("Signed user="+fileName);
								
								NodeRef nodeRef = new NodeRef( signConfMap.get(MainMasterConstant.SCC_MAIN_SIGNATURE_PATH).toString());
								log.info("Signature Folder="+nodeRef);
								
								nodeRef = fileFolderService.searchSimple(nodeRef, fileName);
								log.info("Signature Image="+nodeRef);
						        
						        
								for (ActivitiScriptNode scriptNodeRef : attachDocList) {
									NodeRef attachNodeRef = scriptNodeRef.getNodeRef();
									log.info("attachNodeRef :: " + attachNodeRef);
									
									attDocList.add(attachNodeRef);
									
									FileInfo fileInfo = fileFolderService.getFileInfo(attachNodeRef);
									log.info("name:"+fileInfo.getName());
									
									for(Map<String, Object> docSignPatternModel : docSignPatternList) {
										log.info("  regex:"+docSignPatternModel.get(MainMasterConstant.TFN_FLAG1));
										
										if (fileInfo.getName().matches((String)docSignPatternModel.get(MainMasterConstant.TFN_FLAG1))) {
											log.info("   matched");
								
									    	List<List<String>> signList = new ArrayList<List<String>>();
									    	String signature = (String)docSignPatternModel.get(MainMasterConstant.TFN_FLAG2);
									    	log.info("signature position : "+signature);
									    	List<String> s = null;
											if(signature!=null){
												signature =  signature.replaceAll("[\\[\\]]", "");
												log.info("signature[] : "+signature);
												List<String> position = Arrays.asList(signature.split(","));
												
												int i;
												for(i=0;i<position.size(); i+=2){
													
													s = new ArrayList<String>();
													for(int l=0; l<2; l++){
														s.add(position.get(i+l));
													}
													
													signList.add(s);
													
												}
												
											}
									    	
									        List<String> position = signList.get(level-1);
									        
									        if (Integer.valueOf(position.get(0)) != 0 || Integer.valueOf(position.get(1)) != 0) {
									        	
												ContentReader contentReader = contentService.getReader(attachNodeRef, ContentModel.PROP_CONTENT);
												
											    InputStream is = contentReader.getContentInputStream();
											    
										    	PdfReader pdfToWatermark = new PdfReader(is);
												int numberOfPages = pdfToWatermark.getNumberOfPages();
										        ByteArrayOutputStream out = new ByteArrayOutputStream();
										        PdfStamper stamp = new PdfStamper(pdfToWatermark, out);
										        
										        for(int i=1 ; i<= numberOfPages; i++){
										        	 PdfContentByte pageContent = stamp.getOverContent(i);
									        	 
											         addOneSignature(pageContent, position, nodeRef, date, signConfMap, 0);
									        	}
										        
										        stamp.close();
										        is.close();
										        out.flush();
										        out.close();
										        
										        if (contentReader.isClosed()) {
										        	try {
											   			  Thread.sleep(2000);
											   		} catch (InterruptedException ie) {
											   			  //Handle exception
											   		}
										            
										        	alfrescoService.cancelCheckout(attachNodeRef);
										            NodeRef workingCopy = checkOutCheckInService.checkout(attachNodeRef);
													ContentWriter contentWriter = contentService.getWriter(workingCopy, ContentModel.PROP_CONTENT, true);
												    contentWriter.setMimetype("application/pdf");
												    
					//							    contentReader = contentService.getReader(signNodeRef, ContentModel.PROP_CONTENT);
												    
					//							    contentWriter.putContent(contentReader);
												    contentWriter.putContent(new ByteArrayInputStream(out.toByteArray()));
												    nodeService.setProperty(workingCopy, ContentModel.PROP_CONTENT, contentWriter.getContentData());
												    
												    Map<String, Serializable> versionProperties = new HashMap<String, Serializable>(1);
												    versionProperties.put(VersionModel.PROP_VERSION_TYPE, VersionType.MINOR);
												   
												    log.info("  check in ...");
												    checkOutCheckInService.checkin(workingCopy, versionProperties);
												    log.info("  finish");
										        }
									        
									        } // position
									        break;
								        
										} // if
									} // for doc sign pattern
								} // for attach doc
					        } // if
					    
					    }  catch (Exception ex) {
							log.error("", ex);
						}
						
						return attDocList;
					}
				}, AuthenticationUtil.getAdminUserName());	
	}
}
