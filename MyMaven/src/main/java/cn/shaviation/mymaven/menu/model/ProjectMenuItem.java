package cn.shaviation.mymaven.menu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import cn.shaviation.mymaven.util.Locales;

@Entity
public class ProjectMenuItem {
	private Long Id;
	private String name;
	private String url;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public String getI18nName() {
		return Locales.getMessage(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
