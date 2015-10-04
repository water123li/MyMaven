package cn.shaviation.mymaven.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.ResultSetHelper;
import com.opencsv.ResultSetHelperService;

public class CsvUtil {

	public static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);
	
	public static void exportData(String[] tables,final File dataPath, JdbcTemplate jdbcTemplate) {
		for (final String tableName : tables) {
			jdbcTemplate.query("select * from " + tableName, new ResultSetExtractor<Object>() {

				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					OutputStreamWriter outputStreamWriter = null;
					CSVWriter csvWriter = null;
					ResultSetHelper helper = new ResultSetHelperService();
					try {
						outputStreamWriter = new OutputStreamWriter(new FileOutputStream(new File(dataPath, tableName + ".csv")), "gbk");
						csvWriter = new CSVWriter(outputStreamWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, "\r\n");
						csvWriter.writeNext(helper.getColumnNames(rs),false);
						while (rs.next()) {
							csvWriter.writeNext(helper.getColumnValues(rs, false),false);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (outputStreamWriter != null) {
							try {
								outputStreamWriter.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					
					
					
					return null;
				}
			});
		}
		
	}

	public static void importData(String filePath, JdbcTemplate jdbcTemplate) {
		importData(new File(filePath), jdbcTemplate);
	}
	public static void importData(final File file, JdbcTemplate jdbcTemplate) {
		jdbcTemplate.execute(new ConnectionCallback<Object>() {

			@Override
			public Object doInConnection(Connection con) throws SQLException, DataAccessException {
				boolean autoCommit = con.getAutoCommit();
				DatabaseMetaData dmd = con.getMetaData();
				con.setAutoCommit(false);
				if (file.isDirectory()) {
					File[] listFiles = file.listFiles();
					for (File tempFile : listFiles) {
						importFile(tempFile, dmd, con);
					}
				}else {
					importFile(file, dmd, con);
				}
				con.commit();
				con.setAutoCommit(autoCommit);
				return null;
			}

			
		});
		
	}
	public static void importFile(File file, DatabaseMetaData dmd, Connection con) throws SQLException {
		if (!file.getName().endsWith(".csv")) {
			return;
		}
		Statement tempStatement = con.createStatement();
		tempStatement.executeQuery("set foreign_key_checks=0");
		String tableName = file.getName();
		tableName = tableName.substring(0, tableName.lastIndexOf("."));
		ResultSet rs = dmd.getTables(null, null, tableName, new String[]{"TABLE"});
		if (!rs.next()) {
			return;
		}
		
		List<List<String>> rows = readCsv(file);
		if (rows.size() == 0) {
			return;
		}
		//构造insert语句
		StringBuffer sql = new StringBuffer("insert into ");
		logger.debug(tableName);
		sql.append(tableName + "(");
		Map<String, String> columnTypes = new HashMap<>();
		ResultSet columns = dmd.getColumns(null, null, tableName, null);
		while (columns.next()) {
			columnTypes.put(columns.getString("COLUMN_NAME").toLowerCase(), columns.getString("TYPE_NAME").toLowerCase());
		}
		columns.close();
		tempStatement.execute("delete from " + tableName);
		List<String> columnTypeList = new ArrayList<>();
		int batchCount = 0, colCount = rows.get(0).size();
		for (int k = 0; k < colCount; k++) {
			String colName = rows.get(0).get(k);
			sql.append(colName);
			columnTypeList.add(columnTypes.get(colName.toLowerCase()));
			if (k != colCount - 1) {
				sql.append(",");
			}
		}
		sql.append(") value (");
		for (int k = 0; k < colCount; k++) {
			sql.append("?");
			if (k != colCount - 1) {
				sql.append(",");
			}
		}
		sql.append(");");
		PreparedStatement statement = con.prepareStatement(sql.toString());
		for (int i = 1; i <rows.size(); i++) {
			for (int k = 0; k < colCount; k++) {
				String columnType = columnTypeList.get(k);
				String fieldValue = rows.get(i).get(k);
				if (fieldValue.equals("NULL") || fieldValue.isEmpty()) {
					statement.setString(k + 1, null);
				}else if(columnType.equals("varchar") || columnType.equals("longtext")){
					statement.setString(k + 1, fieldValue);
				}else {
					if (columnType.equals("bit")) {
						if (fieldValue.equalsIgnoreCase("true")) {
							fieldValue = "1";
						} else {
							fieldValue = "0";
						}
					}
					try {
						statement.setLong(k + 1, Long.parseLong(fieldValue));
					} catch (Exception e) {
						statement.setString(k + 1, fieldValue);
					}
				}
			}
			statement.addBatch();
			batchCount++;
			if (batchCount == 300) {
				statement.executeBatch();
				batchCount = 0;
				
			}
		}
		statement.executeBatch();
		statement.cancel();		
	}
	public static List<List<String>> readCsv(File file) {
		List<List<String>> result = new ArrayList<>();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "gbk"), ',');
			String[] content = null;
			while ((content = reader.readNext()) != null) {
				result.add(Arrays.asList(content));
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	
	
	public static void main(String[] args) throws Exception{
		String[] tables = {"user"};
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:mysql://localhost:3306/mymaven?createDatabaseIfNotExist=true&amp;useUnicode=true&amp;characterEncoding=UTF-8");
		dataSource.setMaxActive(1000);
		dataSource.setMaxWait(1000);
		dataSource.setDefaultAutoCommit(true);
		dataSource.setDefaultReadOnly(false);
		dataSource.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		
		CsvUtil.importData("D:\\data", new JdbcTemplate(dataSource));
//		CsvUtil.exportData(tables, new File("D:\\data"), new JdbcTemplate(dataSource));
		System.out.println("操作成功！");
	}
}
