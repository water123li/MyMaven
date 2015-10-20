package cn.shaviation.mymaven.menu.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class ProjectMenu extends Menu<ProjectMenu> {

	private ProjectMenuItem projectMenuItem;

	@ManyToOne(fetch = FetchType.LAZY)
	public ProjectMenuItem getProjectMenuItem() {
		return projectMenuItem;
	}

	public void setProjectMenuItem(ProjectMenuItem projectMenuItem) {
		this.projectMenuItem = projectMenuItem;
	}

}
