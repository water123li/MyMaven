package cn.shaviation.mymaven.event;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件
 * 
 * @author rli
 *
 */
public class Event {
	private String type;	//事件类型
	private Map<String, Object> data;	//数据
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getData() {
		if (this.data == null) {
			data = new HashMap<>();
		}
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	

	public Event addData(String key, Object data) {
		if (this.data == null) {
			data = new HashMap<>();
		}
		this.data.put(key, data);
		return this;
	}
}
