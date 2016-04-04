package pb.build.alfresco;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class MenuGenerator {
	
	public void genMenu(String propFile, String destPath) {
		try {
			
			Properties props = new Properties();
			InputStream ins = new FileInputStream(propFile);
			if (ins != null) {
				props.load(ins);
				
				String[] menus = props.getProperty("menu.order").split(",");
				
				Path outputPath = Paths.get(destPath + File.separator + "share-header.get.js");
				try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
				
					prefixModule(writer);
					
					for(String menu : menus) {
						
						String key = "module."+menu;
						String value = props.getProperty(key);
						
						if (value != null && Boolean.parseBoolean(value)) {
							if (key.equals("module.admin")) {
								adminModule(writer);
							}
							else
							if (key.equals("module.pcm")) {
								pcmModule(writer);
							}
							else
							if (key.equals("module.exp")) {
								expModule(writer);
							}
						} // if (value)
					} // for

					suffixModule(writer);
					
					writer.flush();
					writer.close();
				
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			else {
				throw new FileNotFoundException("propertyfile : '" + propFile + "' not found!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void prefixModule(Writer writer) throws Exception {
	    writer.write("var headerBar = widgetUtils.findObject(model.jsonModel, \"id\", \"HEADER_APP_MENU_BAR\");");
	    writer.write("if (headerBar != null)");
	    writer.write("{");
	    writer.write("var widgets = [];");
	}

	private void suffixModule(Writer writer) throws Exception {
		writer.write("headerBar.config.widgets.splice(1, 0, {");
		writer.write(	"id: \"HEADER_PB\",");
		writer.write(	"name: \"alfresco/header/AlfMenuBarPopup\",");
		writer.write(	"config: {");
		writer.write(		"label: \"header.menu.pb.label\",");
		writer.write(		"widgets: [{");   
		writer.write(			"name: \"alfresco/menus/AlfMenuGroup\",");
		writer.write(			"config: {");
		writer.write(				"widgets: widgets");
		writer.write(			"}");
		writer.write(		"}]");
		writer.write(	"}");
		writer.write(	"});");
	    writer.write("}");
	}
	
	private void adminModule(Writer writer) throws Exception {
		System.out.println("+ admin");
	    writer.write("if (user.isAdmin) {");
	    writer.write(	"widgets.push({");
	    writer.write(		"name: \"alfresco/header/AlfMenuItem\",");
	    writer.write(		"config:{");
		writer.write(			"id: \"HEADER_PB_ADMIN\",");
		writer.write(			"label: \"header.menu.pb-admin.label\",");
		writer.write(			"iconClass: \"alf-pb-admin-icon\",");
		writer.write(			"targetUrl: \"admin\"");
	    writer.write(		"}");
		writer.write(	"});");
		writer.write("}");
	}

	private void pcmModule(Writer writer) throws Exception {
		System.out.println("+ pcm");
		writer.write("widgets.push({");
		
	    
		writer.write(	"id: \"HEADER_PB_PCM\",");
		writer.write(	"name: \"alfresco/header/AlfCascadingMenu\",");
		writer.write(	"config: {");
		writer.write(		"label: \"header.menu.pb-pcm.label\",");
		writer.write(		"iconClass: \"alf-pb-pcm-icon\",");
		writer.write(		"widgets: [{");   
		writer.write(			"name: \"alfresco/menus/AlfMenuGroup\",");
		writer.write(			"config: {");
		writer.write(				"widgets: [{");
		
		writer.write(	"name: \"alfresco/menus/AlfMenuItem\",");
		writer.write(	"config:{");
		writer.write(		"id: \"HEADER_PB_PCM_REQ\",");
		writer.write(		"label: \"header.menu.pb-pcm-req.label\",");
		writer.write(		"iconClass: \"alf-pb-pcm-icon\",");
		writer.write(		"targetUrl: \"pcm-req\"");
		writer.write(	"}");
		
		writer.write(				"},{");
		
		writer.write(	"name: \"alfresco/menus/AlfMenuItem\",");
		writer.write(	"config:{");
		writer.write(		"id: \"HEADER_PB_PCM_ORD\",");
		writer.write(		"label: \"header.menu.pb-pcm-ord.label\",");
		writer.write(		"iconClass: \"alf-pb-pcm-icon\",");
		writer.write(		"targetUrl: \"pcm-ord\"");
		writer.write(	"}");
		
		writer.write(				"},{");
		
		writer.write(	"name: \"alfresco/menus/AlfMenuItem\",");
		writer.write(	"config:{");
		writer.write(		"id: \"HEADER_PB_PCM_USE\",");
		writer.write(		"label: \"header.menu.pb-pcm-use.label\",");
		writer.write(		"iconClass: \"alf-pb-pcm-icon\",");
		writer.write(		"targetUrl: \"pcm-use\"");
		writer.write(	"}");
		
		
		writer.write(				"}]");
		writer.write(			"}");
		writer.write(		"}]");
		writer.write(	"}");
		
		writer.write("});");
	}

	private void expModule(Writer writer) throws Exception {
		System.out.println("+ exp");
		writer.write("widgets.push({");
		
		writer.write(	"id: \"HEADER_PB_EXP\",");
		writer.write(	"name: \"alfresco/header/AlfCascadingMenu\",");
		writer.write(	"config: {");
		writer.write(		"label: \"header.menu.pb-exp.label\",");
		writer.write(		"iconClass: \"alf-pb-exp-icon\",");
		writer.write(		"widgets: [{");   
		writer.write(			"name: \"alfresco/menus/AlfMenuGroup\",");
		writer.write(			"config: {");
		writer.write(				"widgets: [{");
		
		writer.write(	"name: \"alfresco/menus/AlfMenuItem\",");
		writer.write(	"config:{");
		writer.write(		"id: \"HEADER_PB_EXP_BRW\",");
		writer.write(		"label: \"header.menu.pb-exp-brw.label\",");
		writer.write(		"iconClass: \"alf-pb-exp-icon\",");
		writer.write(		"targetUrl: \"exp-brw\"");
		writer.write(	"}");
		
		writer.write(				"},{");
		
		writer.write(	"name: \"alfresco/menus/AlfMenuItem\",");
		writer.write(	"config:{");
		writer.write(		"id: \"HEADER_PB_EXP_USE\",");
		writer.write(		"label: \"header.menu.pb-exp-use.label\",");
		writer.write(		"iconClass: \"alf-pb-exp-icon\",");
		writer.write(		"targetUrl: \"exp-use\"");
		writer.write(	"}");
		
		
		writer.write(				"}]");
		writer.write(			"}");
		writer.write(		"}]");
		writer.write(	"}");
		
		writer.write("});");
	}

	public static void main(String[] args) {
//		
//		URL location = MenuGenerator.class.getProtectionDomain().getCodeSource().getLocation();
//        System.out.println(location.getFile());
        
		MenuGenerator mg = new MenuGenerator();

		String propFile = args[0];
		String destPath = args[1];
		
		mg.genMenu(propFile, destPath);
	}

}