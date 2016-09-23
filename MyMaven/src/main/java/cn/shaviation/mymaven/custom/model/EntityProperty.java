package cn.shaviation.mymaven.custom.model;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * 实体属性
 * 
 * @author rli
 *
 */
@MappedSuperclass
public class EntityProperty extends BaseEntity {

	private String name;
	private String title;
	private String columnType;
	private EntityObject entityObject;

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

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	@ManyToOne
	public EntityObject getEntityObject() {
		return entityObject;
	}

	public void setEntityObject(EntityObject entityObject) {
		this.entityObject = entityObject;
	}

}
