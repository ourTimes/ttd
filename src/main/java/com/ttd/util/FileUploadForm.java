/*
 * Class: FileUploadForm
 * Description:文件上传的模型
 * Version: 1.0
 * Author: Chenchenxing
 * Creation date: 2013-8-10
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {

	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
}
