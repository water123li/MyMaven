package cn.shaviation.mymaven.menu.model;

import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@MappedSuperclass
public class Menu<T> {

	protected Long Id;
	protected String name;
	protected String url;
	protected T parentMenu;
	protected Set<T> subMenus;
	protected Integer position;

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

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setParentMenu(T parentMenu) {
		this.parentMenu = parentMenu;
	}

	@ManyToOne
	public T getParentMenu(){
		return parentMenu;
	}

	@OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	@OrderBy(" position asc")
	public Set<T> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Set<T> subMenus) {
		this.subMenus = subMenus;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public static void main(String[] args) {
		// Menu menu = new ProjectMenu();
	}

}
