package cn.shaviation.mymaven.db.install.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.shaviation.mymaven.db.install.ScriptFilter;
import cn.shaviation.mymaven.util.VersionUtil;

public class ScriptFilterImpl implements ScriptFilter{

	@Override
	public boolean isScriptAvailable(String applyScope, String currentDbVersion, String scriptScope,
			String scriptVersion, String branch) {
		int internalVersionIndex = currentDbVersion.lastIndexOf(".");
		String dbBigVersion = currentDbVersion.substring(0, internalVersionIndex);//去除内部版本号后的系统版本
		List<String> scriptScopeList = new ArrayList<>();
		List<String> branchVersionList = new ArrayList<>();
		Map<String, String> branchVersionMap = new HashMap<>();
		if (!StringUtils.isEmpty(scriptScope)) {
			scriptScopeList = Arrays.asList(StringUtils.split(scriptScope, ","));
		}
		if (!StringUtils.isEmpty(branch)) {
			for (String tempVersion : StringUtils.split(branch, ",")) {
				branchVersionList.add(tempVersion);
				internalVersionIndex = tempVersion.lastIndexOf(".");
				branchVersionMap.put(tempVersion.substring(0, internalVersionIndex), tempVersion);
			}
		}
		if (VersionUtil.compareVersion(currentDbVersion, scriptVersion) < 0) {	//	当前系统版本小于脚本的版本
			if (scriptScopeList.isEmpty() //脚本不限定范围
					|| scriptScopeList.contains(applyScope)) {//包含指定的范围
				if (branchVersionList.isEmpty()//没有分支版本
						|| !branchVersionMap.containsKey(dbBigVersion)//当前系统非分支版本
						|| VersionUtil.compareVersion(currentDbVersion, scriptVersion) < 0) {//当前系统虽然是分支版本但还未执行过该脚本
					return true;
				}
			}
		}
		
		return false;
	}
}
