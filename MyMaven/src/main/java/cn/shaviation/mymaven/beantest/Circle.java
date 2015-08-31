package cn.shaviation.mymaven.beantest;

import org.springframework.stereotype.Service;

@Service("circle")
public class Circle implements Shape{

	@Override
	public String getClassName() {
		return "圆形";
	}

	@Override
	public Class<?> getDataClass() {
		return Circle.class;
	}

}
