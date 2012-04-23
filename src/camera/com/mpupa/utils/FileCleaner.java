package com.mpupa.utils;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class FileCleaner implements ServletContextAware{

	private ServletContext servletContext;
	
	public void cleanOldFiles() {
		//System.out.println("--------"+servletContext);
		String pathString = this.servletContext.getRealPath("/uploadfile/");
		System.out.println(pathString);
		File fileFolder = new File(pathString);
		
		if(fileFolder.exists()) {
			File[] files = fileFolder.listFiles();
			
			for (File file : files) {
				System.out.println(file.getAbsolutePath());
				deleteFile(file);
				
				
			}
		}
	}
	
	/**
	 * 递归删除一个文件或者文件夹
	 *
	 * @author shawn
	 * @date 2012 2012-4-21 下午9:25:45
	 */
	private void deleteFile(File file) {
		System.out.println(file.getName());
		if(file.isDirectory()) {
			if(!file.delete()) {
				File[] files = file.listFiles();
				for (File file1 : files) {
					deleteFile(file1);
				}
			}
			
		} else {
			file.delete();
		}
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		servletContext = arg0;
	}
}
