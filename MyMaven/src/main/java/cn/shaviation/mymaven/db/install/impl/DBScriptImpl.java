package cn.shaviation.mymaven.db.install.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.shaviation.mymaven.db.install.CodeScriptTarget;
import cn.shaviation.mymaven.db.install.DBScript;
import cn.shaviation.mymaven.db.install.ResourceResolver;
import cn.shaviation.mymaven.db.install.ScriptFilter;
import cn.shaviation.mymaven.db.sql.SqlExecutor;
import cn.shaviation.mymaven.util.SpringContextUtil;
import cn.shaviation.mymaven.util.VersionUtil;

public class DBScriptImpl implements DBScript {

	private ResourceResolver resourceResolver;	//文件资源查找器
	private String resourceLocations;
	private ScriptFilter scriptFilter;
	
	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public void setResourceResolver(ResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
	}

	public String getResourceLocations() {
		return resourceLocations;
	}

	public void setResourceLocations(String resourceLocations) {
		this.resourceLocations = resourceLocations;
	}

	public ScriptFilter getScriptFilter() {
		return scriptFilter;
	}

	public void setScriptFilter(ScriptFilter scriptFilter) {
		this.scriptFilter = scriptFilter;
	}

	@Override
	public void execute(JdbcTemplate jdbcTemplate, String applyScope, String currentDbVersion) throws Exception{
		List<Resource> resources = resourceResolver.getResources(resourceLocations);
		this.sortXmlResource(resources);
		for (Resource resource : resources) {
			File scriptFile = resource.getFile();
			if (scriptFile.isFile()) {
				String fileName = scriptFile.getName();
				if (fileName.endsWith("sql")) {
					SqlExecutor sqlExecutor = (SqlExecutor) SpringContextUtil.getBean(SqlExecutor.class);
					sqlExecutor.executeSql(scriptFile, jdbcTemplate, false);
				} else {
					this.executeXmlSql(scriptFile, jdbcTemplate, applyScope, currentDbVersion);
				}
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void executeXmlSql(File xmlSqlFile, JdbcTemplate jdbcTemplate, String applyScope, String currentDbVersion) throws Exception{
		SqlExecutor sqlExecutor = (SqlExecutor) SpringContextUtil.getBean(SqlExecutor.class);
		String fileName = xmlSqlFile.getName();
		String scriptVersion = fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("."));
		List<Element> sqlElements = new SAXReader().read(xmlSqlFile).getRootElement().elements("sql");
		for (Element element : sqlElements) {
			if (scriptFilter.isScriptAvailable(applyScope, currentDbVersion, element.attributeValue("scriptScope"), scriptVersion, element.attributeValue("branch"))) {
				String scriptTargetBeanName = element.attributeValue("target"), methodName = element.attributeValue("methodName");
				if (scriptTargetBeanName != null && methodName != null) {
					CodeScriptTarget codeScriptTarget = (CodeScriptTarget) SpringContextUtil.getBean(scriptTargetBeanName, CodeScriptTarget.class);
					codeScriptTarget.setJdbcTemplate(jdbcTemplate);
					MethodUtils.invokeMethod(codeScriptTarget, methodName, null);
				} else {
					String sqlText = element.getText();
					if (!StringUtils.isEmpty(sqlText.trim())) {
						sqlExecutor.executeSql(sqlText, jdbcTemplate);
					}
				}
			}
		}
	}

	private void sortXmlResource(List<Resource> resources) throws IOException{
		for (Resource resource : resources) {
			String fileName = resource.getFile().getName();
			if (!fileName.endsWith(".xml")) {
				return;
			}
		}
		Collections.sort(resources, new Comparator<Resource>() {

			@Override
			public int compare(Resource o1, Resource o2) {
				try {
					String fileName1 = o1.getFile().getName();
					String fileName2 = o2.getFile().getName();
					String scriptVersion1 = fileName1.substring(fileName1.indexOf("_") + 1, fileName1.lastIndexOf("."));
					String scriptVersion2 = fileName2.substring(fileName2.indexOf("_") + 1, fileName2.lastIndexOf("."));
					return VersionUtil.compareVersion(scriptVersion1, scriptVersion2);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
	
}
