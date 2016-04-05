package cn.shaviation.mymaven.db.install.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.StringUtils;

import cn.shaviation.mymaven.db.install.ResourceResolver;

/**
 * 根据资源路径查找资源文件
 * @author rli
 *
 */
public class ResourceResolverImpl implements ResourceResolver {
	private static String  RESOUCE_LOCATION_DELIMITERS = ",; \t\n";
	private ApplicationContext applicationContext;

	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public List<Resource> getResources(String resourceLocations) {
		List<Resource> resources = new ArrayList<>();
		String[] loacationPatterns = StringUtils.tokenizeToStringArray(resourceLocations, RESOUCE_LOCATION_DELIMITERS);
		for (String loacationPattern : loacationPatterns) {
			try {
				Resource[] res = ResourcePatternUtils.getResourcePatternResolver(this.applicationContext).getResources(loacationPattern);
				resources.addAll(Arrays.asList(res));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return resources;
	}


}
