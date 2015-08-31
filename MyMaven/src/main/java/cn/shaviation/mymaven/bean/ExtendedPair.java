package cn.shaviation.mymaven.bean;

import org.apache.commons.lang3.ObjectUtils;

public class ExtendedPair<M,N> {

	private M name;
	private N value;
	
	public ExtendedPair() {}

	public ExtendedPair(M name, N value) {
		super();
		this.name = name;
		this.value = value;
	}

	public M getName() {
		return name;
	}

	public void setName(M name) {
		this.name = name;
	}

	public N getValue() {
		return value;
	}

	public void setValue(N value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.hashCode(this.name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ExtendedPair)) {
			return false;
		}
		ExtendedPair<?, ?> other = (ExtendedPair<?, ?>) obj;
		return ObjectUtils.equals(this.name, other.getName()) && ObjectUtils.equals(this.value, other.getValue());
	}
}
