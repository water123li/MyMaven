package cn.shaviation.mymaven.custom.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cn.shaviation.mymaven.common.model.BaseEntity;

/**
 * 实体属性
 * 
 * @author rli
 *
 */
@Entity
public class EntityObject extends BaseEntity {

	private String name;
	private String title;
	private Set<BaseEntityProperty> baseEntityProperties;
	private Set<CustomEntityProperty> customEntityProperties;
	private EntityCatalog entityCatalog;
	private Boolean canCustom; // 是否允许自定义

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(mappedBy = "entityObject", cascade = CascadeType.ALL)
	public Set<BaseEntityProperty> getBaseEntityProperties() {
		return baseEntityProperties;
	}

	public void setBaseEntityProperties(Set<BaseEntityProperty> baseEntityProperties) {
		this.baseEntityProperties = baseEntityProperties;
	}

	@OneToMany(mappedBy = "entityObject", cascade = CascadeType.ALL)
	public Set<CustomEntityProperty> getCustomEntityProperties() {
		return customEntityProperties;
	}

	public void setCustomEntityProperties(Set<CustomEntityProperty> customEntityProperties) {
		this.customEntityProperties = customEntityProperties;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public EntityCatalog getEntityCatalog() {
		return entityCatalog;
	}

	public void setEntityCatalog(EntityCatalog entityCatalog) {
		this.entityCatalog = entityCatalog;
	}

	public Boolean getCanCustom() {
		return canCustom;
	}

	public void setCanCustom(Boolean canCustom) {
		this.canCustom = canCustom;
	}

}
