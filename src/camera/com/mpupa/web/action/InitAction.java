package com.mpupa.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

@Controller
@RequestMapping("/")
public class InitAction implements ServletContextAware{

	
	private ServletContext servletContext;

	private Logger logger = Logger.getLogger(AlbumAction.class);
	
	@RequestMapping("/{folderName}")
	public String home(@PathVariable String folderName,HttpServletRequest request) {
		
		String path = this.servletContext.getRealPath("/uploadfile/"+folderName);
		
		File folderFile = new File(path);
		if(folderFile.exists()) {
			File[] files = folderFile.listFiles();
			List<String> fileNameStrings = new ArrayList<String>();
			for (File file2 : files) {
				
				String pathString = file2.getAbsolutePath();
				pathString = pathString.substring(pathString.indexOf("uploadfile"));
				
				logger.error("each filePath:"+pathString);
				fileNameStrings.add(pathString);
			}
			
			if(fileNameStrings.size() == 0) {
				return "index";
			} else {
				
				request.getSession().setAttribute("filePaths", fileNameStrings);
			}
			return "image";
		} else {
			return "index";
		}
		
	}
	@RequestMapping("/")
	public String home(HttpServletRequest request) {
		return "/index";
	}
	
	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		servletContext = arg0;
	}
}
