package com.mpupa.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mpupa.utils.WebAppUtil;

@Controller
public class AlbumAction implements ServletContextAware {

	ServletContext servletContext;

	private Logger logger = Logger.getLogger(AlbumAction.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	// 将文件上传请求映射到该方法
	public String handleFormUpload(
			@RequestParam("folderName") String folderName, // 设置请求参数的名称和类型
			@RequestParam("file1") CommonsMultipartFile mFile,
			HttpServletResponse response,HttpServletRequest request) { // 请求参数一定要与form中的参数名对应
		if (!mFile.isEmpty()) {
			String path = this.servletContext.getRealPath("/uploadfile/"); // 获取本地存储路径

			logger.error("1111111111-"+path);
			File fileFolderFile = new File(path);

			if (!fileFolderFile.exists()) {
				fileFolderFile.mkdir();
			}
			
			File personalFolder = new File(path + "/"+folderName);
			
			if(!personalFolder.exists()) {
				personalFolder.mkdir();
			}

			File file = new File(path + "/"+folderName + "/" + new Date().getTime() + ".jpg"); // 新建一个文件

			logger.error("filePath:"+file.getAbsolutePath());
			try {
				mFile.getFileItem().write(file); // 将上传的文件写入新建的文件中
			} catch (Exception e) {
				e.printStackTrace();
			}
			File[] files = fileFolderFile.listFiles();
			List<String> fileNameStrings = new ArrayList<String>();
			for (File file2 : files) {
				
				String pathString = file2.getAbsolutePath();
				pathString = pathString.substring(pathString.indexOf("uploadfile"));
				fileNameStrings.add(pathString);
			}
			request.getSession().setAttribute("filePaths", fileNameStrings);
			// WebAppUtil.outputJSONResult("Success", response);
		} else {
			// WebAppUtil.outputJSONResult("Fauile", response);
		}
		return "image";
	}

	@RequestMapping("/init")
	public String initLink(HttpServletRequest request) {
		String path = this.servletContext.getRealPath("uploadfile"); // 获取本地存储路径
		
		logger.error("init--Path:"+path);
System.out.println(path+"----------");
		File fileFolderFile = new File(path);

		if (!fileFolderFile.exists()) {
			fileFolderFile.mkdir();
		}
		
		File[] files = fileFolderFile.listFiles();
		List<String> fileNameStrings = new ArrayList<String>();
		for (File file2 : files) {
			
			String pathString = file2.getAbsolutePath();
			pathString = pathString.substring(pathString.indexOf("uploadfile"));
			
			logger.error("each filePath:"+pathString);
			fileNameStrings.add(pathString);
		}
		request.getSession().setAttribute("filePaths", fileNameStrings);
		return "image";
	}
	
	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		servletContext = arg0;
	}

}
