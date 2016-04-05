package cn.shaviation.mymaven.db.install.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.shaviation.mymaven.db.install.DBInstaller;
import cn.shaviation.mymaven.db.install.DBScript;
import cn.shaviation.mymaven.db.sql.SqlExecutor;
import cn.shaviation.mymaven.util.CompressUtil;
import cn.shaviation.mymaven.util.SpringContextUtil;
import cn.shaviation.mymaven.util.VersionUtil;

public class DBInstallerImpl implements DBInstaller, InitializingBean {
	private List<DBScript> newInstallScripts; // 数据库初建时的脚步
	private List<DBScript> upgradeInstallScripts; // 数据库升级时的脚步
	private String applyScope; // 执行的脚步范围
	private DataSource dataSource; // 数据源
	private Resource documentFolder; // 文档目录
	private Boolean autoUpgrade; // 是否自动升级

	public void setNewInstallScripts(List<DBScript> newInstallScripts) {
		this.newInstallScripts = newInstallScripts;
	}

	public void setUpgradeInstallScripts(List<DBScript> upgradeInstallScripts) {
		this.upgradeInstallScripts = upgradeInstallScripts;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setDocumentFolder(Resource documentFolder) {
		this.documentFolder = documentFolder;
	}

	public void setAutoUpgrade(Boolean autoUpgrade) {
		this.autoUpgrade = autoUpgrade;
	}

	@Override
	public void setDocumentFolder(File documentFolder) {
		this.documentFolder = new FileSystemResource(documentFolder);
	}

	@Override
	public void setApplyScope(String applyScope) {
		this.applyScope = applyScope;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.dataSource == null) {
			return;
		}
		this.install(new JdbcTemplate(this.dataSource), this.autoUpgrade == null || this.autoUpgrade);
	}

	@Override
	public void install(JdbcTemplate jdbcTemplate) throws Exception {
		this.install(jdbcTemplate, true);

	}

	public void install(JdbcTemplate jdbcTemplate, boolean forceUpgrade) throws Exception {
		File dbBackupFile = null, documentBackupFile = null;
		SqlExecutor sqlExecutor= SpringContextUtil.getApplicationContext().getBean(SqlExecutor.class);
		try {
			if (this.isNewInstall(jdbcTemplate)) {
				dbBackupFile = sqlExecutor.backupDB(jdbcTemplate);
				this.newInstall(jdbcTemplate);
			} else if (forceUpgrade) {
				String currentDbVersion = this.getCurrentDbVersion(jdbcTemplate);
				if (VersionUtil.compareVersion(currentDbVersion, this.getVersion()) < 0) {//数据库升级
					dbBackupFile = sqlExecutor.backupDB(jdbcTemplate);
					this.upgradeInstall(jdbcTemplate,currentDbVersion);
					if (this.documentFolder != null && this.documentFolder.getFile().exists() && this.documentFolder.getFile().list().length > 0) {
						documentBackupFile = new File(new StringBuilder().append("documentBackup").append(System.nanoTime()).append(".zip").toString());
						CompressUtil.compressZipFile(documentBackupFile, this.documentFolder.getFile());
					}
					
				} else {
					return;
				}
				
			} else {
				return;
			}
			//更新系统版本
			this.updateVersion(jdbcTemplate, this.getVersion());
			dbBackupFile.delete();
			if (documentBackupFile != null) {
				documentBackupFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (dbBackupFile != null) {
				sqlExecutor.executeSql(documentBackupFile, jdbcTemplate, true);
				dbBackupFile.delete();
			}
			if (documentBackupFile != null && documentBackupFile.exists()) {
				FileUtils.cleanDirectory(this.documentFolder.getFile());
				CompressUtil.unZipFile(documentBackupFile, this.documentFolder.getFile());
			}
			throw e;
		}
	}

	@Override
	public boolean needUpgrade(JdbcTemplate jdbcTemplate) throws Exception {
		if (!this.isNewInstall(jdbcTemplate)) {
			String currentDbVersion = this.getCurrentDbVersion(jdbcTemplate);
			if (VersionUtil.compareVersion(currentDbVersion, this.getVersion()) < 0) {
				return true;
			}
		}
		return false;
	}

	public String getVersion() {
		return "";
	}

	private void newInstall(JdbcTemplate jdbcTemplate) throws Exception {
		if (newInstallScripts == null) {
			return;
		}
		for (int i = 0; i < newInstallScripts.size(); i++) {
			newInstallScripts.get(i).execute(jdbcTemplate, applyScope, null);
		}
	}

	private void upgradeInstall(JdbcTemplate jdbcTemplate, String currentDbVersion) throws Exception {
		if (upgradeInstallScripts == null) {
			return;
		}
		for (int i = 0; i < upgradeInstallScripts.size(); i++) {
			upgradeInstallScripts.get(i).execute(jdbcTemplate, applyScope, currentDbVersion);
		}
	}

	public void updateVersion(JdbcTemplate jdbcTemplate, String version) {
		List<?> res = jdbcTemplate.queryForList("select id from db_version");
		if (res != null && res.size() > 0) {
			StringBuilder sql = new StringBuilder();
			sql.append("update db_version set version = '").append(getVersion()).append("'");
			jdbcTemplate.execute(sql.toString());
		} else {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into db_version(verion) values ('").append(getVersion()).append("');");
			jdbcTemplate.execute(sql.toString());
		}
	}

	private boolean isNewInstall(JdbcTemplate jdbcTemplate) {
		return jdbcTemplate.queryForList("show tables like 'db_version'").size() == 0
				|| jdbcTemplate.queryForList("select id from db_version").size() == 0;
	}

	@SuppressWarnings("unchecked")
	private String getCurrentDbVersion(JdbcTemplate jdbcTemplate) {
		List<?> res = jdbcTemplate.queryForList("select version from db_version");
		if (res != null && res.size() > 0) {
			Map<String, Object> map = (Map<String, Object>) res.get(0);
			return map.get("version").toString();
		}
		return null;
	}

}
