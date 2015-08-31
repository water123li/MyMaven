package cn.shaviation.mymaven.bean;

public class Pair<M,N> {

	private M name;
	private N value;
	
	public Pair() {}

	public Pair(M name, N value) {
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
	
	
}
