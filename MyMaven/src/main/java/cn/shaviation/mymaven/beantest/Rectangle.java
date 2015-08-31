package cn.shaviation.mymaven.beantest;

import org.springframework.stereotype.Service;

@Service("rectangle")
public class Rectangle implements Shape{

	@Override
	public String getClassName() {
		return "正方形";
	}

	@Override
	public Class<?> getDataClass() {
		return Rectangle.class;
	}

}
