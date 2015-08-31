package cn.shaviation.mymaven.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CmdUtil {

	private static final Logger logger = LoggerFactory.getLogger(CmdUtil.class);
		
	/**
	 * 执行脚本
	 * @param cmd	脚本命令
	 * @param dir	脚本的执行目录
	 * @param outputFile	脚本的输出文件
	 * @param fileEncode	输出文件编码
	 */
	public static void excuteCmd(String[] cmd, File dir, File outputFile, String fileEncode) throws Exception{
		Process process = null;
		PrintWriter writer = null;
		try {
			logger.debug("执行开始");
			process = Runtime.getRuntime().exec(cmd, cmd, null);
			if (outputFile != null) {
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outputFile), fileEncode);
				writer = new PrintWriter(outputStreamWriter);
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String text = null;
				while ((text = reader.readLine()) != null) {
					writer.println(text);
				}
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String text = null;
			while ((text = reader.readLine()) != null) {
				throw new Exception(text);
			}
			process.waitFor();
			if (process.exitValue() < 0) {
				throw new Exception("执行失败");
			}
			
		} catch (IOException e) {
			logger.error(null, e);
			throw new Exception("执行失败");
		}finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (process != null) {
					process.getInputStream().close();
					process.getErrorStream().close();
				}
			} catch (Exception e2) {
				logger.error(null, e2);
			}
		}
		
	}
	
	/**
	 * 
	 * @param cmd
	 * @param dir
	 * @param outputFile
	 */
	public static void excuteCmd(String[] cmd, File dir, File outputFile) throws Exception{
		excuteCmd(cmd, dir, outputFile, null);
	}
}
