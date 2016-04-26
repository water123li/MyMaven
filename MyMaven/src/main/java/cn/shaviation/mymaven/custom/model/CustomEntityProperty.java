package cn.shaviation.mymaven.custom.model;

import javax.persistence.Entity;

/**
 * 实体自定义属性
 * 
 * @author rli
 *
 */
@Entity
public class CustomEntityProperty extends EntityProperty {

	private String columnName;	//数据库字段名
	private Boolean nullable;	//可否为空
	private Integer columnLength;	//字段长度
	private String defaultValue;	//默认值
	private String editOptions;	//设置格式
	private Integer showType;	
	private Boolean isUser;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Boolean getNullable() {
		return nullable;
	}
	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}
	public Integer getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(Integer columnLength) {
		this.columnLength = columnLength;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getEditOptions() {
		return editOptions;
	}
	public void setEditOptions(String editOptions) {
		this.editOptions = editOptions;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}
	public Boolean getIsUser() {
		return isUser;
	}
	public void setIsUser(Boolean isUser) {
		this.isUser = isUser;
	}	
	
}
