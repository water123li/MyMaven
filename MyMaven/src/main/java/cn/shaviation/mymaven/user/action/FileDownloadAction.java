package cn.shaviation.mymaven.user.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Namespace("/file")
//@Action(results = { @Result(type = "stream", params = { "contentType", "application/octet-stream;charset=ISO8859-1", "inputName", "inputStream",
//		"contentDisposition", "attachment;filename=\"test.jpg\"", "bufferSize", "4096" }) }, params = { "inputPath",
//				"\\download\\测试.jpg" }) 
public class FileDownloadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8027316914587085238L;
	private String inputPath;  
    private String contentType;  
    private String contentDisposition;  
    private String inputName;  
    private int bufferSize;  
    private InputStream inputStream; 
    private String fileName; 

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getContentDisposition() {
		return contentDisposition;
	}


	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}


	public String getInputName() {
		return inputName;
	}


	public void setInputName(String inputName) {
		this.inputName = inputName;
	}


	public int getBufferSize() {
		return bufferSize;
	}


	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	@Action(value = "download", results = { @Result(name = "success", type = "stream", params = { "contentType",
			"application/octet-stream;charset=UTF-8", "inputName", "inputStream", "contentDisposition", "attachment;filename=\"${fileName}\"",
			"bufferSize", "4096" }) })
	public String download() throws Exception{
		// ServletContext提供getResourceAsStream()方法
				// 返回指定文件对应的输入流
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
//		this.inputPath = "\\images" + "\\" + fileName;
		//this.inputStream = ServletActionContext.getServletContext().getResourceAsStream(inputPath);
		final File file = new File(realpath + "\\" + fileName); 
		this.inputStream = new FileInputStream(file){
			@Override
			public void close() throws IOException {
				super.close();
				file.delete();
			}
		};
		this.inputStream = new FileInputStream(file);
//		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		System.out.println(inputStream);
		System.out.println("hh");
		return SUCCESS;
	}
}
