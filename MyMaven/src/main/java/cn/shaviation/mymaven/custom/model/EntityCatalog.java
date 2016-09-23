package cn.shaviation.mymaven.custom.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 实体目录
 * 
 * @author rli
 *
 */
@Entity
public class EntityCatalog extends BaseEntity {

	private String name;
	private List<EntityCatalog> subCatalog;
	private EntityCatalog parentCatalog;
	private Set<EntityObject> entityObjects;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "parentCatalog", cascade = CascadeType.ALL)
	public List<EntityCatalog> getSubCatalog() {
		return subCatalog;
	}

	public void setSubCatalog(List<EntityCatalog> subCatalog) {
		this.subCatalog = subCatalog;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public EntityCatalog getParentCatalog() {
		return parentCatalog;
	}

	public void setParentCatalog(EntityCatalog parentCatalog) {
		this.parentCatalog = parentCatalog;
	}

	@OneToMany(mappedBy = "entityCatalog", cascade = CascadeType.ALL)
	public Set<EntityObject> getEntityObjects() {
		return entityObjects;
	}

	public void setEntityObjects(Set<EntityObject> entityObjects) {
		this.entityObjects = entityObjects;
	}

}
