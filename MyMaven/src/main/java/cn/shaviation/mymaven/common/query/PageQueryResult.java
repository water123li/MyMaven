package cn.shaviation.mymaven.common.query;

import java.util.List;

public class PageQueryResult {
	private Long records; 	//数据的总条数
	private Integer page;	//当前页数
	private List<?> rows;	//数据列表
	
	
	
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getRows(Class<T> clazz) {
		return (List<T>) rows;
	}
	
}
