package cn.shaviation.mymaven.user.action;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("default")
@Namespace("/file")
public class FileUploadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6578870323987260797L;
	private File image;
	private String imageFileName;
	private String fileName; 
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}
	
	@Action(value = "upload", results = {@Result(name = "success", location = "/WEB-INF/pages/user/success.jsp")})
	public String upload() throws Exception{
		
		String realpath = ServletActionContext.getServletContext().getRealPath("/images");
		System.out.println(realpath);
		if(image!=null){
			File savefile = new File(new File(realpath), imageFileName);
			if(!savefile.getParentFile().exists()) savefile.getParentFile().mkdirs();
			FileUtils.copyFile(image, savefile);
			ActionContext.getContext().put("message", "上传成功");
		}
		return SUCCESS;
	}

}
