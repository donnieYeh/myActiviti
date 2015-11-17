package com.yejf.ativiti.entity;

import java.io.File;

public class WorkFlowBean {
	private String fileName;
	private File processFile;
	//文件名称  
    private String processFileFileName;  
    //文件类型  
    private String processFileContentType;  
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public File getProcessFile() {
		return processFile;
	}
	public void setProcessFile(File processFile) {
		this.processFile = processFile;
	}
	public String getProcessFileFileName() {
		return processFileFileName;
	}
	public void setProcessFileFileName(String processFileFileName) {
		this.processFileFileName = processFileFileName;
	}
	public String getProcessFileContentType() {
		return processFileContentType;
	}
	public void setProcessFileContentType(String processFileContentType) {
		this.processFileContentType = processFileContentType;
	}
	
}
